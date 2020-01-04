
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;


import javax.swing.JMenu;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.view.menu.ControlPanelPropertiesMenuItem;


public class DeveloperMenu extends JMenu {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeveloperMenu( AimxcelApplication app ) {
        super( "Developer" );
        add( new ControlPanelPropertiesMenuItem( app ) );
    }
}
