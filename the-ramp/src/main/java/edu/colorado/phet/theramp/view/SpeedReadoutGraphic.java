// Copyright 2002-2011, University of Colorado

/*  */
package edu.colorado.phet.theramp.view;

import java.text.DecimalFormat;
import java.text.MessageFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import edu.colorado.phet.theramp.TheRampStrings;
import edu.colorado.phet.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

/**
 * User: Sam Reid
 * Date: May 30, 2005
 * Time: 6:45:21 PM
 */

public class SpeedReadoutGraphic extends PNode implements ModelElement {
    private DecimalFormat format = new DecimalFormat( "0.00" );
    private PText phetTextGraphic;
    private RampPhysicalModel rampPhysicalModel;

    public SpeedReadoutGraphic( RampPhysicalModel rampPhysicalModel ) {
        this.rampPhysicalModel = rampPhysicalModel;
//        Font font = new Font( AimxcelDefaultFont.LUCIDA_SANS, Font.BOLD, 22 );
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
