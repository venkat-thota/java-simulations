

package edu.colorado.phet.common.piccolophet.nodes;

import java.util.Vector;

import javax.swing.ComboBoxModel;

import edu.colorado.phet.common.piccolophet.swing.AbcLearnJComboBox;
import edu.umd.cs.piccolox.pswing.PComboBox;

/**
 * Workaround for problems occurring on Mac, see Unfuddle #705
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class AbcLearnPComboBox extends PComboBox {

    public AbcLearnPComboBox( ComboBoxModel model ) {
        super( model );
        AbcLearnJComboBox.applyMacWorkaround( this );
    }

    public AbcLearnPComboBox( final Object items[] ) {
        super( items );
        AbcLearnJComboBox.applyMacWorkaround( this );
    }

    public AbcLearnPComboBox( Vector items ) {
        super( items );
        AbcLearnJComboBox.applyMacWorkaround( this );
    }

    public AbcLearnPComboBox() {
        super();
        AbcLearnJComboBox.applyMacWorkaround( this );
    }
}
