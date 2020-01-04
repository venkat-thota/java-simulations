

package com.aimxcel.abclearn.common.piccoloaimxcel.nodes;

import java.util.Vector;

import javax.swing.ComboBoxModel;

import com.aimxcel.abclearn.common.piccoloaimxcel.swing.AimxcelJComboBox;

import edu.umd.cs.piccolox.pswing.PComboBox;

/**
 * Workaround for problems occurring on Mac, see Unfuddle #705
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class AimxcelPComboBox extends PComboBox {

    public AimxcelPComboBox( ComboBoxModel model ) {
        super( model );
        AimxcelJComboBox.applyMacWorkaround( this );
    }

    public AimxcelPComboBox( final Object items[] ) {
        super( items );
        AimxcelJComboBox.applyMacWorkaround( this );
    }

    public AimxcelPComboBox( Vector items ) {
        super( items );
        AimxcelJComboBox.applyMacWorkaround( this );
    }

    public AimxcelPComboBox() {
        super();
        AimxcelJComboBox.applyMacWorkaround( this );
    }
}
