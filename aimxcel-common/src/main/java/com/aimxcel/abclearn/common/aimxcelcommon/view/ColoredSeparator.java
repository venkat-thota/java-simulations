

package com.aimxcel.abclearn.common.aimxcelcommon.view;

import java.awt.Color;

import javax.swing.JSeparator;

import com.aimxcel.abclearn.common.aimxcelcommon.view.ColoredSeparator;


public class ColoredSeparator extends JSeparator {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Convenience class for creating black separators.
     */
    public static class BlackSeparator extends ColoredSeparator {

        public BlackSeparator() {
            super( Color.BLACK );
        }

        public BlackSeparator( int orientation ) {
            super( Color.BLACK, orientation );
        }
    }

    public ColoredSeparator( Color color ) {
        this( color, JSeparator.HORIZONTAL );
    }

    public ColoredSeparator( Color color, int orientation ) {
        super( orientation );
        setForeground( color );
    }
}
