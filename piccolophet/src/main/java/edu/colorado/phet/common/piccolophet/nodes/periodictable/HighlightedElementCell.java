
package edu.colorado.phet.common.piccolophet.nodes.periodictable;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;

import static com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnColorScheme.RED_COLORBLIND;

/**
 * Cell that watches the atom and highlights itself if the atomic number
 * matches its configuration.
 *
 * @author Sam Reid
 * @author John Blanco
 */
public class HighlightedElementCell extends BasicElementCell {

    public HighlightedElementCell( final int atomicNumber, final Color backgroundColor ) {
        super( atomicNumber, backgroundColor );
        getText().setFont( new AbcLearnFont( AbcLearnFont.getDefaultFontSize(), true ) );
        getBox().setStroke( new BasicStroke( 2 ) );
        getBox().setStrokePaint( RED_COLORBLIND );
        getBox().setPaint( Color.white );
    }

    //Wait until others are added so that moving to front will actually work, otherwise 2 sides would be clipped by nodes added after this
    @Override public void tableInitComplete() {
        super.tableInitComplete();

        //For unknown reasons, some nodes (Oxygen in sodium nitrate in sugar-and-salt solutions) get clipped if you don't schedule this for later
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                moveToFront();
            }
        } );
    }
}
