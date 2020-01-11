package edu.colorado.phet.theramp.view;

import java.text.DecimalFormat;
import java.text.MessageFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import edu.colorado.phet.theramp.TheRampStrings;
import edu.colorado.phet.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class SpeedReadoutGraphic extends PNode implements ModelElement {
    private DecimalFormat format = new DecimalFormat( "0.00" );
    private PText phetTextGraphic;
    private RampPhysicalModel rampPhysicalModel;

    public SpeedReadoutGraphic( RampPhysicalModel rampPhysicalModel ) {
        this.rampPhysicalModel = rampPhysicalModel;
        phetTextGraphic = new PText( "" );
        phetTextGraphic.setFont( RampFontSet.getFontSet().getSpeedReadoutFont() );
        addChild( phetTextGraphic );
    }

    public void stepInTime( double dt ) {
        double value = rampPhysicalModel.getBlock().getVelocity();
        String text = MessageFormat.format( TheRampStrings.getString( "readout.velocity" ), new Object[] { format.format( value ) } );
        phetTextGraphic.setText( text );
    }
}
