

package com.aimxcel.abclearn.magnetsandelectromagnets.control.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JMenuItem;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenuItem;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.SystemComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.view.menu.OptionsMenu;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsStrings;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsSimSharing.Components;
import com.aimxcel.abclearn.magnetsandelectromagnets.control.dialog.BackgroundColorHandler;
import com.aimxcel.abclearn.magnetsandelectromagnets.control.dialog.GridControlsDialog;

public class MagnetsAndElectromagnetsOptionsMenu extends OptionsMenu {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     *
     * @param application
     */
    public MagnetsAndElectromagnetsOptionsMenu( final AimxcelApplication application ) {
        super();

        // Background Color menu item, disabled when dialog is open
        final JMenuItem backgroundColorMenuItem = new SimSharingJMenuItem( Components.backgroundColorMenuItem, MagnetsAndElectromagnetsStrings.MENU_ITEM_BACKGROUND_COLOR );
        backgroundColorMenuItem.setMnemonic( MagnetsAndElectromagnetsStrings.MNEMONIC_BACKGROUND_COLOR );
        backgroundColorMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                BackgroundColorHandler handler = new BackgroundColorHandler( application );
                JDialog backgroundColorDialog = handler.getDialog();
                backgroundColorDialog.addWindowListener( new WindowAdapter() {

                    @Override public void windowOpened( WindowEvent e ) {
                        SimSharingManager.sendSystemMessage( Components.backgroundColorDialog, SystemComponentTypes.dialog, SystemActions.windowOpened );
                    }

                    // called when dispose is called
                    @Override public void windowClosed( WindowEvent e ) {
                        SimSharingManager.sendSystemMessage( Components.backgroundColorDialog, SystemComponentTypes.dialog, SystemActions.windowClosed );
                        backgroundColorMenuItem.setEnabled( true );
                    }

                    // called when the close button in the dialog's window dressing is clicked
                    @Override public void windowClosing( WindowEvent e ) {
                        backgroundColorMenuItem.setEnabled( true );
                    }
                } );
                backgroundColorDialog.setVisible( true );
                backgroundColorMenuItem.setEnabled( false );
            }
        } );
        add( backgroundColorMenuItem );

        // Grid Controls dialog, disabled when dialog is open
        final JMenuItem gridControlsMenuItem = new SimSharingJMenuItem( Components.gridControlsMenuItem, MagnetsAndElectromagnetsStrings.MENU_ITEM_GRID_CONTROLS );
        gridControlsMenuItem.setMnemonic( MagnetsAndElectromagnetsStrings.MNEMONIC_GRID_CONTROLS );
        gridControlsMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                final GridControlsDialog gridControlsDialog = new GridControlsDialog( application );
                gridControlsDialog.addWindowListener( new WindowAdapter() {

                    @Override public void windowOpened( WindowEvent e ) {
                        SimSharingManager.sendSystemMessage( Components.fieldControlsDialog, SystemComponentTypes.dialog, SystemActions.windowOpened );
                    }

                    // called when dispose is called
                    @Override public void windowClosed( WindowEvent e ) {
                        SimSharingManager.sendSystemMessage( Components.fieldControlsDialog, SystemComponentTypes.dialog, SystemActions.windowClosed );
                        gridControlsMenuItem.setEnabled( true );
                    }

                    // called when the close button in the dialog's window dressing is clicked
                    @Override public void windowClosing( WindowEvent e ) {
                        gridControlsDialog.revert();
                        gridControlsMenuItem.setEnabled( true );
                    }
                } );
                gridControlsDialog.setVisible( true );
                gridControlsMenuItem.setEnabled( false );
            }
        } );
        add( gridControlsMenuItem );
    }
}
