
package com.aimxcel.abclearn.glaciers;

import javax.swing.JComponent;

import com.aimxcel.abclearn.core.aimxcelcore.help.HelpBalloon;
public class GlaciersHelpBalloon extends HelpBalloon {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GlaciersHelpBalloon( JComponent helpPanel, String text, HelpBalloon.Attachment arrowTailPosition, double arrowLength ) {
        super( helpPanel, text, arrowTailPosition, arrowLength, 0 /* arrowRotation */ );
    }
}
