
package com.aimxcel.abclearn.common.abclearncommon.view.menu;

import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponents.teacherMenu;

import com.aimxcel.abclearn.common.abclearncommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.components.SimSharingJMenu;
import com.aimxcel.abclearn.common.abclearncommon.view.controls.PropertyCheckBoxMenuItem;

/**
 * TeacherMenu is the "Teacher" menu that appears in the menu bar.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class TeacherMenu extends SimSharingJMenu {

    public TeacherMenu() {
        super( teacherMenu, AbcLearnCommonResources.getString( "Common.TeacherMenu" ) );
        setMnemonic( AbcLearnCommonResources.getChar( "Common.TeacherMenu.mnemonic", 'T' ) );
    }

    /**
     * Adds a JCheckBoxMenu item that allows the user to select whether the sim should be shown for a projector.
     *
     * @param whiteBackground the Property<Boolean> with which to synchronize.
     */
    public void addWhiteBackgroundMenuItem( SettableProperty<Boolean> whiteBackground ) {
        add( new PropertyCheckBoxMenuItem( AbcLearnCommonResources.getString( "Common.WhiteBackground" ), whiteBackground ) {{
            setMnemonic( AbcLearnCommonResources.getChar( "Common.WhiteBackground.mnemonic", 'W' ) );
        }} );
    }
}