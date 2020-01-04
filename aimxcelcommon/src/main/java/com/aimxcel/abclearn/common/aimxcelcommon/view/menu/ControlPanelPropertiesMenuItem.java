
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.view.menu.ControlPanelPropertiesDialog;

/**
 * Menu item that provides access to controls for properties related to a Module's standard control panels.
 * This menu item is intended to be added to the Developer menu, and is therefore not internationalized.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class ControlPanelPropertiesMenuItem extends JCheckBoxMenuItem {

    private static final String ITEM_LABEL = "Control Panel properties..."; // developer control, i18n not required

    private JDialog dialog;

    public ControlPanelPropertiesMenuItem( final AimxcelApplication app ) {
        super( ITEM_LABEL );
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                handleAction( app );
            }
        } );
    }

    private void handleAction( AimxcelApplication app ) {
        if ( isSelected() ) {
            dialog = new ControlPanelPropertiesDialog( app );
            dialog.setVisible( true );
            dialog.addWindowListener( new WindowAdapter() {

                public void windowClosed( WindowEvent e ) {
                    cleanup();
                }

                public void windowClosing( WindowEvent e ) {
                    cleanup();
                }

                private void cleanup() {
                    setSelected( false );
                    dialog = null;
                }
            } );
        }
        else {
            dialog.dispose();
        }
    }
}
