

package com.aimxcel.abclearn.core.aimxcelcore.swing;

import java.awt.Color;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import com.aimxcel.abclearn.common.aimxcelcommon.util.AimxcelUtilities;


public class AimxcelJComboBox extends JComboBox {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AimxcelJComboBox( ComboBoxModel aModel ) {
        super( aModel );
        applyMacWorkaround( this );
    }

    public AimxcelJComboBox( Object items[] ) {
        super( items );
        applyMacWorkaround( this );
    }

    public AimxcelJComboBox( Vector items ) {
        super( items );
        applyMacWorkaround( this );
    }

    public AimxcelJComboBox() {
        super();
        applyMacWorkaround( this );
    }

    public static void applyMacWorkaround( JComboBox comboBox ) {
        if ( AimxcelUtilities.isMacintosh() ) {
            // Mac has a transparent background, with no border
            comboBox.setBackground( Color.WHITE );
            comboBox.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
        }
    }
}
