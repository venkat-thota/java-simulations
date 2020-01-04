
package com.aimxcel.abclearn.common.aimxcelcommon.updates;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.swing.JDialog;

import com.aimxcel.abclearn.common.aimxcelcommon.AimxcelCommonConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.DownloadProgressDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.ErrorDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.files.AimxcelInstallation;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.dialogs.UpdateErrorDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.util.AimxcelUtilities;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DeploymentScenario;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DownloadThread;
import com.aimxcel.abclearn.common.aimxcelcommon.util.FileUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.util.logging.LoggingUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;


public class SimUpdater {

    // updater basename
    private static final String UPDATER_BASENAME = "aimxcel-updater";

    private static final String UPDATER_JAR = UPDATER_BASENAME + ".jar";

    // where the updater lives on the Aimxcel site
    private static final String UPDATER_ADDRESS = AimxcelCommonConstants.AIMXCEL_HOME_URL + "/aimxcel-dist/aimxcel-updater/" + UPDATER_JAR;

    // localized strings
    private static final String ERROR_WRITE_PERMISSIONS = AimxcelCommonResources.getString( "Common.updates.errorWritePermissions" );
    private static final String ERROR_MISSING_JAR = AimxcelCommonResources.getString( "Common.updates.errorMissingJar" );
    private static final String ERROR_NOT_A_JAR = AimxcelCommonResources.getString( "Common.updates.errorNotAJar" );

    private static final Logger LOGGER = LoggingUtils.getLogger( SimUpdater.class.getCanonicalName() );

    private final File tmpDir;

    public SimUpdater() {
        tmpDir = new File( System.getProperty( "java.io.tmpdir" ) );
    }

    /**
     * Updates the sim that this is called from.
     * The updater bootstrap and new sim JAR are downloaded.
     * Then the bootstrap handles replacing the running JAR with the new JAR.
     *
     * @param simInfo
     * @param newVersion
     */
    public void updateSim( ISimInfo simInfo, AimxcelVersion newVersion ) {

        if ( !tmpDir.canWrite() ) {
            handleErrorWritePermissions( tmpDir );
        }
        else {
            try {
                File simJAR = getSimJAR( simInfo.getFlavor() );
                if ( simJAR == null ) {
                    handleErrorMissingJar( simJAR );
                }
                else if ( !simJAR.canWrite() ) {
                    handleErrorWritePermissions( simJAR );
                }
                else {
                    String jarURL = getJarURL( simInfo );
                    log( "requesting update via URL=" + jarURL );
                    File tempSimJAR = getTempSimJAR( simJAR );
                    File tempUpdaterJAR = getTempUpdaterJAR();
                    boolean success = downloadFiles( UPDATER_ADDRESS, tempUpdaterJAR, jarURL, tempSimJAR, simInfo.getName(), newVersion );
                    if ( success ) {
                        boolean validUpdaterJar = validateUpdateJar( tempUpdaterJAR );
                        boolean validSimJar = validateSimJar( tempSimJAR );
                        if ( validUpdaterJar && validSimJar ) {
                            startUpdaterBootstrap( tempUpdaterJAR, tempSimJAR, simJAR );
                            System.exit( 0 ); //presumably, jar must exit before it can be overwritten
                        }
                    }
                }
            }
            catch ( IOException e ) {
                e.printStackTrace();
                showException( e );
            }
        }
    }

    /**
     * Determines which JAR URL should be used to update this simulation,
     * depends on whether we're in a aimxcel installation (which gives <project>_all.jar)
     * or an offline simulation (which gives <sim>_<locale>.jar).
     *
     * @param simInfo
     * @return
     */
    private String getJarURL( ISimInfo simInfo ) {
        if ( DeploymentScenario.getInstance() == DeploymentScenario.AIMXCEL_INSTALLATION ) {
            return HTMLUtils.getProjectJarURL( simInfo.getProjectName(), "&" );
        }
        else {
            return HTMLUtils.getSimJarURL( simInfo.getProjectName(), simInfo.getFlavor(), simInfo.getLocale() );
        }
    }

    /**
     * Displays an update exception in a dialog.
     *
     * @param e
     */
    private void showException( Exception e ) {
        JDialog dialog = new UpdateErrorDialog( AimxcelApplication.getInstance().getAimxcelFrame(), e );
        dialog.setVisible( true );
    }

    /*
    * Downloads files and displays a progress bar.
    * The files downloaded are the updater bootstrap jar, and the sim's new jar.
    */
    private boolean downloadFiles( String updaterSrc, File updaterDst, String simSrc, File simDst, String simName, AimxcelVersion newVersion ) throws IOException {

        // download requests
        DownloadThread downloadThread = new DownloadThread();
        downloadThread.addRequest( AimxcelCommonResources.getString( "Common.updates.downloadingBootstrap" ), updaterSrc, updaterDst );
        downloadThread.addRequest( AimxcelCommonResources.getString( "Common.updates.downloadingSimJar" ), simSrc, simDst );

        // progress dialog
        String title = AimxcelCommonResources.getString( "Common.updates.progressDialogTitle" );
        Object[] args = { simName, newVersion.formatMajorMinor() };
        String message = MessageFormat.format( AimxcelCommonResources.getString( "Common.updates.progressDialogMessage" ), args );
        DownloadProgressDialog dialog = new DownloadProgressDialog( null, title, message, downloadThread );

        // start the download
        downloadThread.start();
        dialog.setVisible( true );

        return downloadThread.getSucceeded();
    }

    /*
    * Runs the updater bootstrap in a separate JVM.
    */
    private void startUpdaterBootstrap( File updaterBootstrap, File src, File dst ) throws IOException {
        String[] cmdArray = new String[] { AimxcelUtilities.getJavaPath(), "-jar", updaterBootstrap.getAbsolutePath(), src.getAbsolutePath(), dst.getAbsolutePath() };
        log( "Starting updater bootstrap with cmdArray=" + Arrays.asList( cmdArray ).toString() );
        Runtime.getRuntime().exec( cmdArray );
        // It would be nice to read output from the Process returned by exec.
        // However, the simulation JAR must exit so that it can be overwritten.
    }

    /*
     * Gets the running simulation's JAR file.
     */
    private File getSimJAR( String sim ) throws IOException {
        File location = null;
        if ( DeploymentScenario.getInstance() == DeploymentScenario.AIMXCEL_INSTALLATION ) {
            location = AimxcelInstallation.getInstance().getInstalledJarFile();
        }
        else {
            location = FileUtils.getCodeSource();
            if ( !FileUtils.hasSuffix( location, "jar" ) ) {
                // So that this works in IDEs, where we aren't running a JAR.
                // In general, we only support running JAR files.
                location = File.createTempFile( sim, ".jar" );
                log( "Not running from a JAR, you are likely running from an IDE, update will be installed at " + location );
            }
            log( "running sim JAR is " + location.getAbsolutePath() );
        }
        return location;
    }

    /*
    * Gets a temporary file for downloading the sim's jar.
    */
    private File getTempSimJAR( File simJAR ) throws IOException {
        String basename = FileUtils.getBasename( simJAR );
        File file = File.createTempFile( basename, ".jar" );
        log( "temporary JAR is " + file.getAbsolutePath() );
        return file;
    }

    /*
     * Gets a temporary file for downloading the updater bootstrap jar.
     * Tries to use updater.jar, so that we don't accumulate lots of JAR files in the temp directory.
     * If that's not possible, download to a uniquely named file.
     */
    private File getTempUpdaterJAR() throws IOException {
        File updaterJAR = new File( tmpDir.getAbsolutePath() + System.getProperty( "file.separator" ) + UPDATER_JAR );
        if ( updaterJAR.exists() && !updaterJAR.canWrite() ) {
            updaterJAR = File.createTempFile( UPDATER_BASENAME, ".jar" );
        }
        log( "Downloading updater to " + updaterJAR.getAbsolutePath() );
        return updaterJAR;
    }

    /*
    * Validates the sim jar that was downloaded.
    * If it's not a jar, attempts to display the error returned by the server.
    */
    private boolean validateSimJar( File file ) {
        boolean valid = FileUtils.isJar( file );
        if ( !valid ) {
            handleErrorSimJar( file );
        }
        return valid;
    }

    /*
    * Validates the updater jar that was downloaded.
    * Displays an error if it's not a jar.
    */
    private boolean validateUpdateJar( File file ) {
        boolean valid = FileUtils.isJar( file );
        if ( !valid ) {
            handleErrorNotAJar( file );
        }
        return valid;
    }

    private void log( String message ) {
        LOGGER.fine( message );
    }

    private static void handleErrorSimJar( File file ) {
        try {
            /* If we had a sim jar error, then the file should contain an error message.
             * This error message is NOT localized, and we are at the mercy 
             * of the server to provide something intelligible to the user
             */
            String message = "";
            BufferedReader in = new BufferedReader( new FileReader( file ) );
            String s;
            while ( ( s = in.readLine() ) != null ) {
                message += s;
            }
            in.close();
            displayError( message );
        }
        catch ( IOException e ) {
            // fallback
            handleErrorNotAJar( file );
        }
    }

    private static void handleErrorWritePermissions( File file ) {
        Object[] args = { file.getAbsolutePath() };
        String message = MessageFormat.format( ERROR_WRITE_PERMISSIONS, args );
        displayError( message );
    }

    private static void handleErrorMissingJar( File file ) {
        Object[] args = { file.getAbsolutePath() };
        String message = MessageFormat.format( ERROR_MISSING_JAR, args );
        displayError( message );
    }

    private static void handleErrorNotAJar( File file ) {
        Object[] args = { file.getAbsolutePath() };
        String message = MessageFormat.format( ERROR_NOT_A_JAR, args );
        displayError( message );
    }

    private static void displayError( String message ) {
        JDialog d = new ErrorDialog( (Frame) null, message );
        SwingUtils.centerWindowOnScreen( d );
        d.setVisible( true );
    }
}
