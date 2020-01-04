
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.periodictable;

import java.awt.BasicStroke;
import java.awt.Color;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

import static com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelColorScheme.RED_COLORBLIND;
import static com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont.getDefaultFontSize;
import static java.awt.Color.BLACK;
import static java.awt.Color.white;

/**
 * Cell that watches the atom and highlights itself if the atomic number matches its configuration.
 *
 * @author Sam Reid
 * @author John Blanco
 */
public class HighlightingElementCell extends BasicElementCell {
    public HighlightingElementCell( final PeriodicTableAtom atom, final int atomicNumber, final Color backgroundColor ) {
        super( atomicNumber, backgroundColor );
        atom.addAtomListener( new VoidFunction0() {
            public void apply() {
                boolean match = atom.getNumProtons() == atomicNumber;
                getText().setFont( new AimxcelFont( getDefaultFontSize(), match ) );
                if ( match ) {
                    getBox().setStroke( new BasicStroke( 2 ) );
                    getBox().setStrokePaint( RED_COLORBLIND );
                    getBox().setPaint( white );
                    HighlightingElementCell.this.moveToFront();
                }
                else {
                    getText().setTextPaint( BLACK );
                    getBox().setStrokePaint( BLACK );
                    getBox().setPaint( backgroundColor );
                    getBox().setStroke( new BasicStroke( 1 ) );
                }
            }
        } );
    }
}
