
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.optionsMenu;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenu;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyCheckBoxMenuItem;


public class OptionsMenu extends SimSharingJMenu {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OptionsMenu() {
        super( optionsMenu, AimxcelCommonResources.getString( "Common.OptionsMenu" ) );
        setMnemonic( AimxcelCommonResources.getChar( "Common.OptionsMenu.mnemonic", 'O' ) );
    }

       public void addWhiteBackgroundCheckBoxMenuItem( SettableProperty<Boolean> whiteBackgroundProperty ) {
        add( new PropertyCheckBoxMenuItem( AimxcelCommonResources.getString( "Common.WhiteBackground" ), whiteBackgroundProperty ) );
    }
}