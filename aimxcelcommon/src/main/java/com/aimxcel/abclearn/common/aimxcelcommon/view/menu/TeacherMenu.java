
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.teacherMenu;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenu;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyCheckBoxMenuItem;

/**
 * TeacherMenu is the "Teacher" menu that appears in the menu bar.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class TeacherMenu extends SimSharingJMenu {

    public TeacherMenu() {
        super( teacherMenu, AimxcelCommonResources.getString( "Common.TeacherMenu" ) );
        setMnemonic( AimxcelCommonResources.getChar( "Common.TeacherMenu.mnemonic", 'T' ) );
    }

    /**
     * Adds a JCheckBoxMenu item that allows the user to select whether the sim should be shown for a projector.
     *
     * @param whiteBackground the Property<Boolean> with which to synchronize.
     */
    public void addWhiteBackgroundMenuItem( SettableProperty<Boolean> whiteBackground ) {
        add( new PropertyCheckBoxMenuItem( AimxcelCommonResources.getString( "Common.WhiteBackground" ), whiteBackground ) {{
            setMnemonic( AimxcelCommonResources.getChar( "Common.WhiteBackground.mnemonic", 'W' ) );
        }} );
    }
}