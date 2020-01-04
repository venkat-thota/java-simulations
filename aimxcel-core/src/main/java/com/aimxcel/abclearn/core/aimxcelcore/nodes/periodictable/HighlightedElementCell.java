
package com.aimxcel.abclearn.core.aimxcelcore.nodes.periodictable;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

import static com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelColorScheme.RED_COLORBLIND;


public class HighlightedElementCell extends BasicElementCell {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HighlightedElementCell( final int atomicNumber, final Color backgroundColor ) {
        super( atomicNumber, backgroundColor );
        getText().setFont( new AimxcelFont( AimxcelFont.getDefaultFontSize(), true ) );
        getBox().setStroke( new BasicStroke( 2 ) );
        getBox().setStrokePaint( RED_COLORBLIND );
        getBox().setPaint( Color.white );
    }

    @Override public void tableInitComplete() {
        super.tableInitComplete();

         SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                moveToFront();
            }
        } );
    }
}
