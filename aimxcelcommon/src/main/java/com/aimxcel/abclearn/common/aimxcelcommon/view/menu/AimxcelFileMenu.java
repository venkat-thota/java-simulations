

package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentChain.chain;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.exitMenuItem;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents.fileMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.preferences.AimxcelPreferences;
import com.aimxcel.abclearn.common.aimxcelcommon.preferences.PreferencesDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenu;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJMenuItem;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponents;
import com.aimxcel.abclearn.common.aimxcelcommon.statistics.SessionMessage;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;

/**
 * AbcLearnFileMenu
 *
 * @author ?
 */
public class AimxcelFileMenu extends SimSharingJMenu {

    public AimxcelFileMenu( final AimxcelFrame phetFrame, final ISimInfo simInfo ) {
        super( fileMenu, AimxcelCommonResources.getInstance().getLocalizedString( "Common.FileMenu.Title" ) );
        setMnemonic( AimxcelCommonResources.getInstance().getLocalizedString( "Common.FileMenu.TitleMnemonic" ).charAt( 0 ) );

        if ( simInfo.isPreferencesEnabled() ) {
           // addPreferencesMenuItem( phetFrame, simInfo );
            addSeparator();
        }

        JMenuItem exitMI = new SimSharingJMenuItem( chain( fileMenu, exitMenuItem ), AimxcelCommonResources.getInstance().getLocalizedString( "Common.FileMenu.Exit" ) );
        exitMI.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                phetFrame.getApplication().exit();
            }
        } );
        exitMI.setMnemonic( AimxcelCommonResources.getInstance().getLocalizedString( "Common.FileMenu.ExitMnemonic" ).charAt( 0 ) );
        this.add( exitMI );
    }

    private void addPreferencesMenuItem( final AimxcelFrame phetFrame, final ISimInfo simInfo ) {
        JMenuItem preferencesMenuItem = new SimSharingJMenuItem( chain( fileMenu, UserComponents.preferencesMenuItem ), AimxcelCommonResources.getInstance().getLocalizedString( "Common.FileMenu.Preferences" ) );
        preferencesMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                new PreferencesDialog( phetFrame,
                                       SessionMessage.getInstance(),
                                       AimxcelPreferences.getInstance(),
                                       simInfo.isStatisticsFeatureIncluded(),
                                       simInfo.isUpdatesFeatureIncluded(),
                                       simInfo.isDev() ).setVisible( true );
            }
        } );
        preferencesMenuItem.setMnemonic( AimxcelCommonResources.getInstance().getLocalizedString( "Common.FileMenu.PreferencesMnemonic" ).charAt( 0 ) );
        add( preferencesMenuItem );
    }
}
