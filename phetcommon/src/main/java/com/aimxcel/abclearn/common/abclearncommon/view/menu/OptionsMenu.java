
package com.aimxcel.abclearn.common.abclearncommon.view.menu;

import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponents.optionsMenu;

import com.aimxcel.abclearn.common.abclearncommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.components.SimSharingJMenu;
import com.aimxcel.abclearn.common.abclearncommon.view.controls.PropertyCheckBoxMenuItem;

/**
 * OptionsMenu is the "Options" menu that appears in the menu bar.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class OptionsMenu extends SimSharingJMenu {

    public OptionsMenu() {
        super( optionsMenu, AbcLearnCommonResources.getString( "Common.OptionsMenu" ) );
        setMnemonic( AbcLearnCommonResources.getChar( "Common.OptionsMenu.mnemonic", 'O' ) );
    }

    /**
     * Adds a JCheckBoxMenu item that allows the user to select whether the sim should be shown with a white background.
     * This is used primarily in sims with black backgrounds to make it easier to make printouts.
     *
     * @param whiteBackgroundProperty the Property<Boolean> with which to synchronize.
     */
    public void addWhiteBackgroundCheckBoxMenuItem( SettableProperty<Boolean> whiteBackgroundProperty ) {
        add( new PropertyCheckBoxMenuItem( AbcLearnCommonResources.getString( "Common.WhiteBackground" ), whiteBackgroundProperty ) );
    }
}