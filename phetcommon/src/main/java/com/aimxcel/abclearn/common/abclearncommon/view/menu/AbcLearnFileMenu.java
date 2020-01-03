

package com.aimxcel.abclearn.common.abclearncommon.view.menu;

import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentChain.chain;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponents.exitMenuItem;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponents.fileMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.aimxcel.abclearn.common.abclearncommon.application.ISimInfo;
import com.aimxcel.abclearn.common.abclearncommon.preferences.AbcLearnPreferences;
import com.aimxcel.abclearn.common.abclearncommon.preferences.PreferencesDialog;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.components.SimSharingJMenu;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.components.SimSharingJMenuItem;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponents;
import com.aimxcel.abclearn.common.abclearncommon.statistics.SessionMessage;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnFrame;

/**
 * AbcLearnFileMenu
 *
 * @author ?
 */
public class AbcLearnFileMenu extends SimSharingJMenu {

    public AbcLearnFileMenu( final AbcLearnFrame phetFrame, final ISimInfo simInfo ) {
        super( fileMenu, AbcLearnCommonResources.getInstance().getLocalizedString( "Common.FileMenu.Title" ) );
        setMnemonic( AbcLearnCommonResources.getInstance().getLocalizedString( "Common.FileMenu.TitleMnemonic" ).charAt( 0 ) );

        if ( simInfo.isPreferencesEnabled() ) {
           // addPreferencesMenuItem( phetFrame, simInfo );
            addSeparator();
        }

        JMenuItem exitMI = new SimSharingJMenuItem( chain( fileMenu, exitMenuItem ), AbcLearnCommonResources.getInstance().getLocalizedString( "Common.FileMenu.Exit" ) );
        exitMI.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                phetFrame.getApplication().exit();
            }
        } );
        exitMI.setMnemonic( AbcLearnCommonResources.getInstance().getLocalizedString( "Common.FileMenu.ExitMnemonic" ).charAt( 0 ) );
        this.add( exitMI );
    }

    private void addPreferencesMenuItem( final AbcLearnFrame phetFrame, final ISimInfo simInfo ) {
        JMenuItem preferencesMenuItem = new SimSharingJMenuItem( chain( fileMenu, UserComponents.preferencesMenuItem ), AbcLearnCommonResources.getInstance().getLocalizedString( "Common.FileMenu.Preferences" ) );
        preferencesMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                new PreferencesDialog( phetFrame,
                                       SessionMessage.getInstance(),
                                       AbcLearnPreferences.getInstance(),
                                       simInfo.isStatisticsFeatureIncluded(),
                                       simInfo.isUpdatesFeatureIncluded(),
                                       simInfo.isDev() ).setVisible( true );
            }
        } );
        preferencesMenuItem.setMnemonic( AbcLearnCommonResources.getInstance().getLocalizedString( "Common.FileMenu.PreferencesMnemonic" ).charAt( 0 ) );
        add( preferencesMenuItem );
    }
}
