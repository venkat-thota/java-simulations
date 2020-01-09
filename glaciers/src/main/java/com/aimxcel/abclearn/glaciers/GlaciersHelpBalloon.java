// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.glaciers;

import javax.swing.JComponent;

import com.aimxcel.abclearn.core.aimxcelcore.help.HelpBalloon;

/**
 * GlaciersHelpBalloon encapsulates the look of help ballons for this sim.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class GlaciersHelpBalloon extends HelpBalloon {

    public GlaciersHelpBalloon( JComponent helpPanel, String text, HelpBalloon.Attachment arrowTailPosition, double arrowLength ) {
        super( helpPanel, text, arrowTailPosition, arrowLength, 0 /* arrowRotation */ );
    }
}
