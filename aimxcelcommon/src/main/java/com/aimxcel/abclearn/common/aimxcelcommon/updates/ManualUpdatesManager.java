
package com.aimxcel.abclearn.common.aimxcelcommon.updates;

import java.awt.Frame;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.aimxcelcommon.AimxcelCommonConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.application.VersionInfoQuery;
import com.aimxcel.abclearn.common.aimxcelcommon.application.VersionInfoQuery.InstallerResponse;
import com.aimxcel.abclearn.common.aimxcelcommon.application.VersionInfoQuery.Response;
import com.aimxcel.abclearn.common.aimxcelcommon.application.VersionInfoQuery.SimResponse;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.ErrorDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.files.AimxcelInstallation;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelInstallerVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs.InstallerManualUpdateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs.SimManualUpdateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs.UpdateErrorDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs.NoUpdateDialog.InstallerNoUpdateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs.NoUpdateDialog.SimNoUpdateDialog;

/**
 * Handles manual requests for update checks.
 * <p/>
 * If an update is found, an dialog is displayed that allows the user to perform the update.
 * If no update is found, a dialog notifies the user.
 */
public class ManualUpdatesManager {

    private static final String ERROR_INTERNET_CONNECTION = AimxcelCommonResources.getString( "Common.updates.error.internetConnection" );

    private static ManualUpdatesManager instance;

    private final AimxcelApplication app;

    private ManualUpdatesManager( AimxcelApplication app ) {
        this.app = app;
    }

    public static ManualUpdatesManager initInstance( AimxcelApplication app ) {
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
        final Frame parentFrame = app.getAimxcelFrame();

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

        final AimxcelInstallerVersion currentInstallerVersion = AimxcelInstallation.getInstance().getInstallerVersion();
        final Frame parentFrame = app.getAimxcelFrame();

        final VersionInfoQuery query = new VersionInfoQuery( currentInstallerVersion, false /* automaticRequest */ );
        query.addListener( new VersionInfoQuery.VersionInfoQueryListener() {

            public void done( final Response response ) {
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        InstallerResponse installerResponse = response.getInstallerResponse();
                        if ( installerResponse != null && installerResponse.isUpdateRecommended() ) {
                            AimxcelInstallerVersion newInstallerVersion = installerResponse.getVersion();
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
            Object[] args = { AimxcelCommonConstants.AIMXCEL_HOME_URL };
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
