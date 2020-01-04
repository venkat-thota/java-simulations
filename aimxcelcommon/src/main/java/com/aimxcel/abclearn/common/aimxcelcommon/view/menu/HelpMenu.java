

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author:samreid $
 * Revision : $Revision:14677 $
 * Date modified : $Date:2007-04-17 03:40:29 -0500 (Tue, 17 Apr 2007) $
 */
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ModuleEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ModuleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJCheckBoxMenuItem;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenu;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenuItem;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingLogMenuItem;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.ManualUpdatesManager;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;

/**
 * HelpMenu
 *
 * @author ?
 * @version $Revision:14677 $
 */
public class HelpMenu extends SimSharingJMenu implements ModuleObserver {
    private final JMenuItem onscreenHelp;

    public HelpMenu( final AimxcelApplication phetApplication, AimxcelFrame phetFrame ) {
        super( helpMenu, AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpMenu.Title" ) );
        this.setMnemonic( AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpMenu.TitleMnemonic" ).charAt( 0 ) );
        phetApplication.addModuleObserver( this );

        //----------------------------------------------------------------------
        // "Help" menu item
        onscreenHelp = new SimSharingJCheckBoxMenuItem( helpMenuItem, AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpMenu.Help" ) );
        onscreenHelp.setMnemonic( AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpMenu.HelpMnemonic" ).charAt( 0 ) );
        onscreenHelp.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                phetApplication.getActiveModule().setHelpEnabled( onscreenHelp.isSelected() );
            }
        } );
        onscreenHelp.setEnabled( phetApplication.getActiveModule() != null && phetApplication.getActiveModule().hasHelp() );
        add( onscreenHelp );

        //----------------------------------------------------------------------
        // "MegaHelp" menu item
        final JMenuItem megaHelpItem = new SimSharingJMenuItem( megaHelpMenuItem, AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpMenu.MegaHelp" ) );
        megaHelpItem.setMnemonic( AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpMenu.MegaHelpMnemonic" ).charAt( 0 ) );
        megaHelpItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if ( phetApplication.getActiveModule().hasMegaHelp() ) {
                    phetApplication.getActiveModule().showMegaHelp();
                }
                else {
                    AimxcelOptionPane.showMessageDialog( AimxcelApplication.getInstance().getAimxcelFrame(),
                                                      "No MegaHelp available for this module." );
                }
            }
        } );
        phetApplication.addModuleObserver( new ModuleObserver() {
            public void moduleAdded( ModuleEvent event ) {
            }

            public void activeModuleChanged( ModuleEvent event ) {
                megaHelpItem.setVisible( event.getModule().hasMegaHelp() );
            }

            public void moduleRemoved( ModuleEvent event ) {
            }
        } );
        megaHelpItem.setVisible( phetApplication.getActiveModule() != null && phetApplication.getActiveModule().hasMegaHelp() );
        add( megaHelpItem );

        //If in sim sharing, show a menu item that allows the user to access the event log in case they need to send it manually
        if ( SimSharingManager.getInstance().isEnabled() ) {
            addSeparator();
            add( new SimSharingLogMenuItem( phetFrame ) );
        }

        //----------------------------------------------------------------------
        // Separator
       /* addSeparator();
        if ( phetApplication.getSimInfo().isUpdatesFeatureIncluded() ) {
            add( new CheckForSimUpdateMenuItem() );
        }*/

        //----------------------------------------------------------------------
        // "About" menu item
        final JMenuItem about = new SimSharingJMenuItem( aboutMenuItem, AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpMenu.About" ) );
        about.setMnemonic( AimxcelCommonResources.getInstance().getLocalizedString( "Common.HelpMenu.AboutMnemonic" ).charAt( 0 ) );
        about.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                phetApplication.showAboutDialog();
            }
        } );
        add( about );
    }

    /**
     * Sets the state of the Help menu item.
     * This is used to keep the menubar's Help menu item
     * in sync with the control panel's Help button.
     *
     * @param selected
     */
    public void setHelpSelected( boolean selected ) {
        onscreenHelp.setSelected( selected );
    }

    //----------------------------------------------------------------
    // ModuleObserver implementation
    //----------------------------------------------------------------
    public void moduleAdded( ModuleEvent event ) {
        //noop
    }

    public void activeModuleChanged( ModuleEvent event ) {
        Module module = event.getModule();
        if ( module != null ) {
            onscreenHelp.setEnabled( module.hasHelp() );
            onscreenHelp.setSelected( module.isHelpEnabled() );
        }
    }

    public void moduleRemoved( ModuleEvent event ) {
        //noop
    }

    private class CheckForSimUpdateMenuItem extends SimSharingJMenuItem {
        private CheckForSimUpdateMenuItem() {
            super( checkForSimulationUpdateMenuItem, AimxcelCommonResources.getInstance().getLocalizedString( "Common.updates.checkForSimUpdate" ) );
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    ManualUpdatesManager.getInstance().checkForSimUpdates();
                }
            } );
        }
    }
}