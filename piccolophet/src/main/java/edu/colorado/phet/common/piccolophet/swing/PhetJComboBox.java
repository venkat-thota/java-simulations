

package edu.colorado.phet.common.piccolophet.swing;

import java.awt.Color;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import com.aimxcel.abclearn.common.abclearncommon.util.AbcLearnUtilities;

/**
 * Workaround for problems occurring on Mac when embedded in PSwing, see Unfuddle #705
 */
public class AbcLearnJComboBox extends JComboBox {
    public AbcLearnJComboBox( ComboBoxModel aModel ) {
        super( aModel );
        applyMacWorkaround( this );
    }

    public AbcLearnJComboBox( Object items[] ) {
        super( items );
        applyMacWorkaround( this );
    }

    public AbcLearnJComboBox( Vector items ) {
        super( items );
        applyMacWorkaround( this );
    }

    public AbcLearnJComboBox() {
        super();
        applyMacWorkaround( this );
    }

    public static void applyMacWorkaround( JComboBox comboBox ) {
        if ( AbcLearnUtilities.isMacintosh() ) {
            // Mac has a transparent background, with no border
            comboBox.setBackground( Color.WHITE );
            comboBox.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
        }
    }
}
