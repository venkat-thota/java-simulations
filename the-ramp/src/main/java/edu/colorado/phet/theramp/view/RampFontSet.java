// Copyright 2002-2011, University of Colorado

/*  */
package edu.colorado.phet.theramp.view;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

/**
 * User: Sam Reid
 * Date: Aug 19, 2005
 * Time: 9:43:58 AM
 */

public class RampFontSet {
    private Font normalButtonFont;
    private Font barGraphTitleFont;
    private Font timeReadoutFont;
    private Font speedReadoutFont;
    private Font forceArrowLabelFont;
    private Font barFont;

    public static RampFontSet getFontSet() {
        //use toolkit.getDimension to return an appropriate font set.
        return new RampFontSet();
    }

    public RampFontSet() {
        normalButtonFont = new AimxcelFont( 10 );
        barGraphTitleFont = new AimxcelFont( 12, true );
        timeReadoutFont = new AimxcelFont( 14, true );
        speedReadoutFont = timeReadoutFont;
        forceArrowLabelFont = new AimxcelFont( 12, true );
        barFont = new AimxcelFont( 12, true );
    }

    public Font getNormalButtonFont() {
        return normalButtonFont;
    }

    public Font getBarGraphTitleFont() {
        return barGraphTitleFont;
    }

    public Font getTimeReadoutFont() {
        return timeReadoutFont;
    }

    public Font getSpeedReadoutFont() {
        return speedReadoutFont;
    }

    public Font getForceArrowLabelFont() {
        return forceArrowLabelFont;
    }

    public Font getBarFont() {
        return barFont;
    }
}
