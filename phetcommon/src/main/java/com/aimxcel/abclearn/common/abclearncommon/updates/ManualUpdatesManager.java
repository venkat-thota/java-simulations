
package com.aimxcel.abclearn.common.abclearncommon.updates;

import java.awt.Frame;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.abclearncommon.AbcLearnCommonConstants;
import com.aimxcel.abclearn.common.abclearncommon.application.ISimInfo;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.VersionInfoQuery;
import com.aimxcel.abclearn.common.abclearncommon.application.VersionInfoQuery.InstallerResponse;
import com.aimxcel.abclearn.common.abclearncommon.application.VersionInfoQuery.Response;
import com.aimxcel.abclearn.common.abclearncommon.application.VersionInfoQuery.SimResponse;
import com.aimxcel.abclearn.common.abclearncommon.dialogs.ErrorDialog;
import com.aimxcel.abclearn.common.abclearncommon.files.AbcLearnInstallation;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnInstallerVersion;
import com.aimxcel.abclearn.common.abclearncommon.updates.dialogs.InstallerManualUpdateDialog;
import com.aimxcel.abclearn.common.abclearncommon.updates.dialogs.SimManualUpdateDialog;
import com.aimxcel.abclearn.common.abclearncommon.updates.dialogs.UpdateErrorDialog;
import com.aimxcel.abclearn.common.abclearncommon.updates.dialogs.NoUpdateDialog.InstallerNoUpdateDialog;
import com.aimxcel.abclearn.common.abclearncommon.updates.dialogs.NoUpdateDialog.SimNoUpdateDialog;

/**
 * Handles manual requests for update checks.
 * <p/>
 * If an update is found, an dialog is displayed that allows the user to perform the update.
 * If no update is found, a dialog notifies the user.
 */
public class ManualUpdatesManager {

    private static final String ERROR_INTERNET_CONNECTION = AbcLearnCommonResources.getString( "Common.updates.error.internetConnection" );

    private static ManualUpdatesManager instance;

    private final AbcLearnApplication app;

    private ManualUpdatesManager( AbcLearnApplication app ) {
        this.app = app;
    }

    public static ManualUpdatesManager initInstance( AbcLearnApplication app ) {
        if ( instance != null ) {
            throw new RuntimeException( "instance is already initialized" );
        }
        instance = new ManualUpdatesManager( app );
        return instance;
    }

    public static ManualUpdatesManager getInstance() {
        return instance;
    }

    public void checkForSimUpdates() {

        final ISimInfo simInfo = app.getSimInfo();
        final Frame parentFrame = app.getAbcLearnFrame();

        final VersionInfoQuery query = new VersionInfoQuery( simInfo, false /* automaticRequest */ );
        query.addListener( new VersionInfoQuery.VersionInfoQueryListener() {

            public void done( final Response response ) {
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        SimResponse simResponse = response.getSimResponse();
                        if ( simResponse != null && simResponse.isUpdateRecommended() ) {
                            new SimManualUpdateDialog( parentFrame, simInfo, simResponse.getVersion() ).setVisible( true );
                        }
                        else {
                            new SimNoUpdateDialog( parentFrame, simInfo.getName(), simInfo.getVersion() ).setVisible( true );
                        }
                    }
                } );
            }

            public void exception( Exception e ) {
                handleException( parentFrame, e );
            }
        } );
        query.send();
    }

    public void checkForInstallerUpdates() {

        final AbcLearnInstallerVersion currentInstallerVersion = AbcLearnInstallation.getInstance().getInstallerVersion();
        final Frame parentFrame = app.getAbcLearnFrame();

        final VersionInfoQuery query = new VersionInfoQuery( currentInstallerVersion, false /* automaticRequest */ );
        query.addListener( new VersionInfoQuery.VersionInfoQueryListener() {

            public void done( final Response response ) {
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        InstallerResponse installerResponse = response.getInstallerResponse();
                        if ( installerResponse != null && installerResponse.isUpdateRecommended() ) {
                            AbcLearnInstallerVersion newInstallerVersion = installerResponse.getVersion();
                            JDialog dialog = new InstallerManualUpdateDialog( parentFrame, currentInstallerVersion, newInstallerVersion );
                            dialog.setVisible( true );
                        }
                        else {
                            JDialog dialog = new InstallerNoUpdateDialog( parentFrame, currentInstallerVersion );
                            dialog.setVisible( true );
                        }
                    }
                } );
            }

            public void exception( Exception e ) {
                handleException( parentFrame, e );
            }
        } );

        // OK that this blocks, since the user initiated the request
        query.send();
    }

    private void handleException( Frame parentFrame, Exception e ) {
        if ( e instanceof UnknownHostException ) {
            // user is probably not connected to the Internet
            Object[] args = { AbcLearnCommonConstants.ABC_LEARN_HOME_URL };
            String message = MessageFormat.format( ERROR_INTERNET_CONNECTION, args );
            JDialog dialog = new ErrorDialog( parentFrame, message );
            dialog.setVisible( true );
        }
        else {
            JDialog dialog = new UpdateErrorDialog( parentFrame, e );
            dialog.setVisible( true );
        }
    }
}
