package com.aimxcel.abclearn.theramp.view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.MessageFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class PotentialEnergyZeroGraphic extends PNode {
    private RampPhysicalModel rampPhysicalModel;
    private RampWorld rampWorld;
    private RampPanel rampPanel;
    private PPath phetShapeGraphic;
    private PText label;

    public PotentialEnergyZeroGraphic( RampPanel component, final RampPhysicalModel rampPhysicalModel, final RampWorld rampWorld ) {
        this.rampPanel = component;
        this.rampPhysicalModel = rampPhysicalModel;
        this.rampWorld = rampWorld;
        phetShapeGraphic = new PPath( new Line2D.Double( 0, 0, 1000, 0 ), new BasicStroke( 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[] { 20, 20 }, 0 ) );
        phetShapeGraphic.setPaint( Color.black );
        addChild( phetShapeGraphic );

        addInputEventListener( new PBasicInputEventHandler() {
            public void mouseDragged( PInputEvent event ) {
                changeZeroPoint( event );
            }
        } );

        RampPhysicalModel.Listener listener = new RampPhysicalModel.Adapter() {
            public void zeroPointChanged() {
                setOffset( 0, rampWorld.getRampGraphic().getScreenTransform().modelToViewY( rampPhysicalModel.getZeroPointY() ) );
                updateLabel();
            }

        };
        rampPhysicalModel.addListener( listener );

        label = new PText( TheRampStrings.getString( "indicator.height-unknown" ) );
        addChild( label );
        label.setFont( new Font( AimxcelFont.getDefaultFontName(), Font.BOLD, 18 ) );


        addInputEventListener( new CursorHandler( Cursor.HAND_CURSOR ) );
        updateLabel();
        rampPhysicalModel.setZeroPointY( 0.0 );
    }

    private void updateLabel() {
        String str = new DecimalFormat( "0.0" ).format( rampPhysicalModel.getZeroPointY() );
        label.setText( MessageFormat.format( TheRampStrings.getString( "indicator.height" ), new Object[] { str } ) );

    }

    private void changeZeroPoint( PInputEvent pEvent ) {

        Point2D pt = pEvent.getPositionRelativeTo( rampWorld );
        double zeroPointY = rampPanel.getRampGraphic().getScreenTransform().viewToModelY( pt.getY() );
        rampPhysicalModel.setZeroPointY( zeroPointY );
    }
}
