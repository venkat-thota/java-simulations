 
package com.aimxcel.abclearn.common.aimxcelcommon.view.controls;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingIcon;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentChain;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

public class PropertyCheckBoxWithIcon extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JComponent checkBox, iconLabel;

    public PropertyCheckBoxWithIcon( IUserComponent userComponent, final String text, final AimxcelFont font, Image image, final SettableProperty<Boolean> property ) {
        this(userComponent, text, font, new ImageIcon( image ), property );
    }

    public PropertyCheckBoxWithIcon( IUserComponent userComponent, final String text, final AimxcelFont font, Icon icon, final SettableProperty<Boolean> property ) {
        checkBox = new PropertyCheckBox( userComponent, text, property ) {{
            setFont( font );
        }};
        iconLabel = new SimSharingIcon( UserComponentChain.chain( userComponent, "icon" ), icon, new VoidFunction0() {
            public void apply() {
                if ( isEnabled() ) {
                    property.set( !property.get() );
                }
            }
        } );
        add( checkBox );
        add( iconLabel );
    }

    @Override public void setEnabled( boolean enabled ) {
        super.setEnabled( enabled );
        checkBox.setEnabled( enabled );
        iconLabel.setEnabled( enabled );
    }
}
