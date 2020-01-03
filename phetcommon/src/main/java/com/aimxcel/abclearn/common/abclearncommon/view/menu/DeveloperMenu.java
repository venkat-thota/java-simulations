
package com.aimxcel.abclearn.common.abclearncommon.view.menu;


import javax.swing.JMenu;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;

import com.aimxcel.abclearn.common.abclearncommon.view.menu.ControlPanelPropertiesMenuItem;

/**
 * DeveloperMenu is the "Developer" menu that appears in the menu bar.
 * This menu contains global developer-only features for tuning and debugging.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class DeveloperMenu extends JMenu {

    public DeveloperMenu( AbcLearnApplication app ) {
        super( "Developer" );
        add( new ControlPanelPropertiesMenuItem( app ) );
    }
}
