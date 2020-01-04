
package com.aimxcel.abclearn.common.aimxcelcommon.view.controls;

import javax.swing.Icon;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingIcon;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;


public class PropertyIcon<T> extends SimSharingIcon {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertyIcon( final IUserComponent userComponent, Icon icon, final Property<T> property, final T value ) {
        super( userComponent, icon,
               new VoidFunction0() {
                   public void apply() {
                       property.set( value );
                   }
               } );
    }
}
