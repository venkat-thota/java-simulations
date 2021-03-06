package com.aimxcel.abclearn.theramp.view;

import java.text.DecimalFormat;
import java.text.MessageFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class SpeedReadoutGraphic extends PNode implements ModelElement {
    private DecimalFormat format = new DecimalFormat( "0.00" );
    private PText aimxcelTextGraphic;
    private RampPhysicalModel rampPhysicalModel;

    public SpeedReadoutGraphic( RampPhysicalModel rampPhysicalModel ) {
        this.rampPhysicalModel = rampPhysicalModel;
        aimxcelTextGraphic = new PText( "" );
        aimxcelTextGraphic.setFont( RampFontSet.getFontSet().getSpeedReadoutFont() );
        addChild( aimxcelTextGraphic );
    }

    public void stepInTime( double dt ) {
        double value = rampPhysicalModel.getBlock().getVelocity();
        String text = MessageFormat.format( TheRampStrings.getString( "readout.velocity" ), new Object[] { format.format( value ) } );
        aimxcelTextGraphic.setText( text );
    }
}
