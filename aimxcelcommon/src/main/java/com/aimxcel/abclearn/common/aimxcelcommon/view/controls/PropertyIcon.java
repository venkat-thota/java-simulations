
package com.aimxcel.abclearn.common.aimxcelcommon.view.controls;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingIcon;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;

/**
 * Pressing this icon sets a property value.
 * This is useful for icons that are associated with Swing controls.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class PropertyIcon<T> extends SimSharingIcon {

    public PropertyIcon( final IUserComponent userComponent, Icon icon, final Property<T> property, final T value ) {
        super( userComponent, icon,
               new VoidFunction0() {
                   public void apply() {
                       property.set( value );
                   }
               } );
    }
}
