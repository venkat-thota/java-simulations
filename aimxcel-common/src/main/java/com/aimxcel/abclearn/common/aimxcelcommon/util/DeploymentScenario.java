

package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.net.URL;
import java.util.logging.Logger;

import com.aimxcel.abclearn.common.aimxcelcommon.AimxcelCommonConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.files.AimxcelInstallation;
import com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.AimxcelServiceManager;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DeploymentScenario;
import com.aimxcel.abclearn.common.aimxcelcommon.util.FileUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.util.logging.LoggingUtils;

public class DeploymentScenario {

    // enumeration
    public static final DeploymentScenario AIMXCEL_INSTALLATION = new DeploymentScenario( "aimxcel-installation", true, true, true );
    public static final DeploymentScenario STANDALONE_JAR = new DeploymentScenario( "standalone-jar", true, true, true );
    public static final DeploymentScenario AIMXCEL_PRODUCTION_WEBSITE = new DeploymentScenario( "aimxcel-production-website", false, false, false );
    public static final DeploymentScenario AIMXCEL_DEVELOPMENT_WEBSITE = new DeploymentScenario( "aimxcel-development-website", false, false, false );
    public static final DeploymentScenario OTHER_WEBSITE = new DeploymentScenario( "other-website", true, true, false );
    public static final DeploymentScenario DEVELOPER_IDE = new DeploymentScenario( "developer-ide", true, true, false );
    public static final DeploymentScenario UNKNOWN = new DeploymentScenario( "unknown", true, true, false );

    /*
    * There are fragments of the codebase attribute in JNLP files.
    * Codebase is a URL, whose syntax is:
    * <protocol>://<authority><path>?<query>#<fragment>
    *
    * Example:
    * http://www.colorado.edu/physics/aimxcel/dev/balloons/1.07.05
    *
    * We're using <authority> and the general part of <path> to identify the codebase
    * for the Aimxcel production and development websites.
    */
    private static final String AIMXCEL_PRODUCTION_CODEBASE_PREFIX = AimxcelCommonConstants.AIMXCEL_HOME_SERVER; // prefix!
    private static final String AIMXCEL_DEVELOPMENT_CODEBASE_SUBSTRING = "colorado.edu"; // substring!

    private static final Logger LOGGER = LoggingUtils.getLogger( DeploymentScenario.class.getCanonicalName() );

    // singleton
    private static DeploymentScenario instance = null;

    private final String name;
    private final boolean statisticsEnabled;
    private final boolean updatesEnabled;
    private final boolean updatable;

    /* singleton */
    private DeploymentScenario( String name, boolean statisticsEnabled, boolean updatesEnabled, boolean updatable ) {
        this.name = name;
        this.statisticsEnabled = statisticsEnabled;
        this.updatesEnabled = updatesEnabled;
        this.updatable = updatable;
    }

    /**
     * True if the scenario checks for updates.
     */
    public boolean isUpdatesEnabled() {
        return updatesEnabled;
    }

    /**
     * True if the scenario is capable of having it's executable file updated in place.
     */
    public boolean isUpdatable() {
        return updatable;
    }

    /**
     * True if the scenario sends statistics.
     */
    public boolean isStatisticsEnabled() {
        return statisticsEnabled;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public boolean equals( Object object ) {
        boolean equals = false;
        if ( object instanceof DeploymentScenario ) {
            equals = getName().equals( ( (DeploymentScenario) object ).getName() );
        }
        return equals;
    }

    /**
     * Gets the deployment scenario, a singleton.
     * This is determined once, on demand, since it does not change.
     *
     * @return
     */
    public static DeploymentScenario getInstance() {
        if ( instance == null ) {
            instance = determineScenario();
        }
        return instance;
    }

    /*
    * Determines which scenario was used to deploy the application that we're running.
    */
    private static DeploymentScenario determineScenario() {

        DeploymentScenario scenario = UNKNOWN;

        if ( AimxcelServiceManager.isJavaWebStart() ) {

            if ( AimxcelInstallation.exists() ) {
                scenario = DeploymentScenario.AIMXCEL_INSTALLATION;
            }
            else {
                URL codeBase = AimxcelServiceManager.getCodeBase();
                if ( codeBase != null ) {

                    // web-started sims are differentiated base on the codebase attribute specified in the JNLP file

                    LOGGER.fine( "codebase=" + codeBase );
                    String codebaseFragment = codeBase.getAuthority() + codeBase.getPath();

                    if ( codebaseFragment.startsWith( AIMXCEL_PRODUCTION_CODEBASE_PREFIX ) ) {
                        scenario = DeploymentScenario.AIMXCEL_PRODUCTION_WEBSITE;
                    }
                    else if ( codebaseFragment.indexOf( AIMXCEL_DEVELOPMENT_CODEBASE_SUBSTRING ) >= 0 ) {
                        /* 
                         * Do this after checking the production server scenario, 
                         * because deployment codebase substring may be contained in production codebase prefix.
                         */
                        scenario = DeploymentScenario.AIMXCEL_DEVELOPMENT_WEBSITE;
                    }
                    else {
                        scenario = DeploymentScenario.OTHER_WEBSITE;
                    }
                }
            }
        }
        else if ( FileUtils.isJarCodeSource() ) {
            scenario = DeploymentScenario.STANDALONE_JAR;
        }
        else {
            scenario = DeploymentScenario.DEVELOPER_IDE;
        }

        LOGGER.fine( "determineScenario " + scenario.getName() );
        return scenario;
    }

    public static void main( String[] args ) {
        System.out.println( DeploymentScenario.getInstance() );
    }
}
