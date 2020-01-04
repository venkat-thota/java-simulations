
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.optionsMenu;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenu;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyCheckBoxMenuItem;

/**
 * OptionsMenu is the "Options" menu that appears in the menu bar.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class OptionsMenu extends SimSharingJMenu {

    public OptionsMenu() {
        super( optionsMenu, AimxcelCommonResources.getString( "Common.OptionsMenu" ) );
        setMnemonic( AimxcelCommonResources.getChar( "Common.OptionsMenu.mnemonic", 'O' ) );
    }

    /**
     * Adds a JCheckBoxMenu item that allows the user to select whether the sim should be shown with a white background.
     * This is used primarily in sims with black backgrounds to make it easier to make printouts.
     *
     * @param whiteBackgroundProperty the Property<Boolean> with which to synchronize.
     */
    public void addWhiteBackgroundCheckBoxMenuItem( SettableProperty<Boolean> whiteBackgroundProperty ) {
        add( new PropertyCheckBoxMenuItem( AimxcelCommonResources.getString( "Common.WhiteBackground" ), whiteBackgroundProperty ) );
    }
}