
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;


import javax.swing.JMenu;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.view.menu.ControlPanelPropertiesMenuItem;

/**
 * DeveloperMenu is the "Developer" menu that appears in the menu bar.
 * This menu contains global developer-only features for tuning and debugging.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class DeveloperMenu extends JMenu {

    public DeveloperMenu( AimxcelApplication app ) {
        super( "Developer" );
        add( new ControlPanelPropertiesMenuItem( app ) );
    }
}
