// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.common.abclearncommon.view.controls;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.abclearncommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.components.SimSharingIcon;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentChain;
import com.aimxcel.abclearn.common.abclearncommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;

/**
 * Property check box with associated icon.
 * Clicking either the check box or icon toggles the property.
 * Data-collection message when clicking the icon identifies the component as userComponent.icon.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class PropertyCheckBoxWithIcon extends JPanel {

    private final JComponent checkBox, iconLabel;

    public PropertyCheckBoxWithIcon( IUserComponent userComponent, final String text, final AbcLearnFont font, Image image, final SettableProperty<Boolean> property ) {
        this(userComponent, text, font, new ImageIcon( image ), property );
    }

    public PropertyCheckBoxWithIcon( IUserComponent userComponent, final String text, final AbcLearnFont font, Icon icon, final SettableProperty<Boolean> property ) {
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
