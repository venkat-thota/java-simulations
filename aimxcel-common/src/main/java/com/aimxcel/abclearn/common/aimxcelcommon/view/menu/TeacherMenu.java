
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.teacherMenu;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenu;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyCheckBoxMenuItem;


public class TeacherMenu extends SimSharingJMenu {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public TeacherMenu() {
        super( teacherMenu, AimxcelCommonResources.getString( "Common.TeacherMenu" ) );
        setMnemonic( AimxcelCommonResources.getChar( "Common.TeacherMenu.mnemonic", 'T' ) );
    }

  
    public void addWhiteBackgroundMenuItem( SettableProperty<Boolean> whiteBackground ) {
        add( new PropertyCheckBoxMenuItem( AimxcelCommonResources.getString( "Common.WhiteBackground" ), whiteBackground ) {{
            setMnemonic( AimxcelCommonResources.getChar( "Common.WhiteBackground.mnemonic", 'W' ) );
        }} );
    }
}