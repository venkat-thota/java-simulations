
package com.aimxcel.abclearn.common.aimxcelcommon.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelInstallerVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelProperties;
import com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.AimxcelServiceManager;

/**
 * Encapsulates the notion of a PhET offline website installation.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class AimxcelInstallation {

    private static final String PROPERTIES_FILENAME = "phet-installation.properties";

    private static AimxcelInstallation instance;

    // properties associated with the PhET installation
    private final AimxcelProperties properties;

    /**
     * Gets the instance of this object.
     *
     * @return
     */
    public static AimxcelInstallation getInstance() {
        if ( instance == null ) {
            instance = new AimxcelInstallation();
        }
        return instance;
    }

    /* singleton */
    private AimxcelInstallation() {
        properties = readProperties();
    }

    /**
     * Gets the version information associated with the installer that was used to create the PhET installation.
     *
     * @return
     */
    public AimxcelInstallerVersion getInstallerVersion() {
        String key = "installer.creation.date.epoch.seconds";
        long seconds = properties.getLong( key, -1 );
        if ( seconds == -1 ) {
            warnMissingKey( key );
        }
        return new AimxcelInstallerVersion( seconds );
    }

    /**
     * Timestamp that indicates when the installation was performed,
     * in seconds since Epoch.
     *
     * @return
     */
    public long getInstallationTimestamp() {
        return properties.getLong( "install.date.epoch.seconds", -1 );
    }

    /**
     * Gets the JAR file in the installation that corresponds to the running simulation.
     * We cannot use File.getCodeSource, because in some versions of Java, that will
     * return the JAR file in the JWS cache (see #1320).  So we use the JNLP code base
     * to identify the JAR.
     *
     * @return
     */
    public File getInstalledJarFile() {
        File file = null;
        URL codeBase = AimxcelServiceManager.getCodeBase();
        if ( codeBase != null && codeBase.getProtocol().equals( "file" ) ) {
            String dirname = codeBase.getPath();
            String project = new File( dirname ).getName(); // last name in path is project name
            file = new File( dirname, AimxcelApplicationConfig.getProjectJarName( project ) );
        }
        return file;
    }

    /**
     * Does a PhET installation exists that is associated with this sim?
     *
     * @return
     */
    public static boolean exists() {
        boolean exists = false;
        File propertiesFile = getPropertiesFile();
        if ( propertiesFile != null ) {
            exists = propertiesFile.exists();
        }
        return exists;
    }

    /*
    * Reads the properties file, if it exists.
    */
    private static AimxcelProperties readProperties() {
        AimxcelProperties properties = new AimxcelProperties();
        File file = getPropertiesFile();
        if ( file != null && file.exists() ) {
            try {
                properties.load( new FileInputStream( file ) );
            }
            catch ( IOException e ) {
                // this will only happen if the file is corrupt
                e.printStackTrace();
            }
        }
        return properties;
    }

    /*
    * Gets the properties file associated the the PhET installation.
    * Returns null if the file isn't found.
    */
    private static File getPropertiesFile() {
        File file = null;
        URL codeBase = AimxcelServiceManager.getCodeBase();
        if ( codeBase != null && codeBase.getProtocol().equals( "file" ) ) {
            String dirname = codeBase.getPath();
            String project = new File( dirname ).getName(); // last name in path is project name
            String jarName = project + "_all.jar"; // jar file shares project name
            File jarFile = new File( dirname, jarName );
            if ( jarFile.exists() ) {
                File parent = jarFile.getParentFile();
                if ( parent != null ) {
                    File grandParent = parent.getParentFile();
                    if ( grandParent != null ) {
                        file = new File( grandParent.getAbsolutePath(), PROPERTIES_FILENAME );
                    }
                }
            }
        }
        return file;
    }

    private static void warnMissingKey( String key ) {
        System.err.println( "WARNING: " + PROPERTIES_FILENAME + " is missing required key " + key );
    }
}
