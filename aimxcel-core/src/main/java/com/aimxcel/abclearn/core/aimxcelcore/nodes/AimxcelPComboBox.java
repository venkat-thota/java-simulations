

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.util.Vector;

import javax.swing.ComboBoxModel;

import com.aimxcel.abclearn.core.aimxcelcore.swing.AimxcelJComboBox;

import com.aimxcel.abclearn.aimxcel2dextra.pswing.PComboBox;


public class AimxcelPComboBox extends PComboBox {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
