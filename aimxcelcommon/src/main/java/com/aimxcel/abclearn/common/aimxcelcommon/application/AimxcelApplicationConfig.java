

package com.aimxcel.abclearn.common.aimxcelcommon.application;

import java.util.Arrays;
import java.util.Locale;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.preferences.AimxcelPreferences;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DeploymentScenario;
import com.aimxcel.abclearn.common.aimxcelcommon.util.logging.LoggingUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLookAndFeel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.FrameSetup;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.StringUtil;

public class AimxcelApplicationConfig implements ISimInfo {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    //This frame setup should be used for all abcLearn applications, to ensure a consistent window size
    public static final FrameSetup.CenteredWithSize DEFAULT_FRAME_SETUP = new FrameSetup.CenteredWithSize( 1024, 768 );

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    // immutable
    private final String[] commandLineArgs;
    private final String flavor;
    private final AimxcelResources resourceLoader;

    // mutable
    private FrameSetup frameSetup;
    private AimxcelLookAndFeel abcLearnLookAndFeel = new AimxcelLookAndFeel(); // the look and feel to be initialized in launchSim

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Constructor where project & flavor names are identical.
     *
     * @param commandLineArgs
     * @param project
     */
    public AimxcelApplicationConfig( String[] commandLineArgs, String project ) {
        this( commandLineArgs, project, project );
    }

    /**
     * Constructor where project & flavor names are different.
     *
     * @param commandLineArgs
     * @param project
     * @param flavor
     */
    public AimxcelApplicationConfig( String[] commandLineArgs, String project, String flavor ) {
        this.commandLineArgs = commandLineArgs;
        if ( hasCommandLineArg( "-log" ) ) {
            LoggingUtils.enableAllLogging( "com.aimxcel.abclearn.common.abclearncommon" );
        }
        this.flavor = flavor;
        this.resourceLoader = new AimxcelResources( project );
        this.frameSetup = DEFAULT_FRAME_SETUP;
        this.abcLearnLookAndFeel = new AimxcelLookAndFeel();
    }

    //----------------------------------------------------------------------------
    // Setters and getters
    //----------------------------------------------------------------------------

    public String[] getCommandLineArgs() {
        return commandLineArgs;
    }

    public boolean hasCommandLineArg( String arg ) {
        return StringUtil.contains( commandLineArgs, arg );
    }

    // Gets the argument that follows a command line option. Eg, for "-foo bar", return "bar".
    public String getOptionArg( String option ) {
        int s = Arrays.asList( commandLineArgs ).indexOf( option );
        if ( s >= 0 && s <= commandLineArgs.length - 2 && !commandLineArgs[s + 1].startsWith( "-" ) ) {
            return commandLineArgs[s + 1];
        }
        else {
            return null;
        }
    }

    public void setFrameSetup( FrameSetup frameSetup ) {
        this.frameSetup = frameSetup;
    }

    public FrameSetup getFrameSetup() {
        return frameSetup;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setLookAndFeel( AimxcelLookAndFeel abcLearnLookAndFeel ) {
        this.abcLearnLookAndFeel = abcLearnLookAndFeel;
    }

    public AimxcelLookAndFeel getLookAndFeel() {
        return abcLearnLookAndFeel;
    }

    public AimxcelResources getResourceLoader() {
        return resourceLoader;
    }

    public String getProjectName() {
        return resourceLoader.getProjectName();
    }

    public boolean isPreferencesEnabled() {
        return isStatisticsFeatureIncluded() || isUpdatesFeatureIncluded();
    }

    /**
     * Returns the distribution identifier associated with the sim's JAR file.
     * This is used to identify specific distributions of a sim, for example
     * as bundled with a textbook.
     *
     * @return
     */

    public String getDistributionTag() {
        return resourceLoader.getDistributionTag();
    }

    //----------------------------------------------------------------------------
    // Standard properties
    //----------------------------------------------------------------------------

    /**
     * Gets the localized simulation name.
     *
     * @return name
     */
    public String getName() {
        return resourceLoader.getName( flavor );
    }

    public String getVersionForTitleBar() {
        return getVersion().formatForTitleBar();
    }

    /**
     * Retrieves the object that encapsulates the project's version information.
     *
     * @return AbcLearnProjectVersion
     */
    public AimxcelVersion getVersion() {
        return resourceLoader.getVersion();
    }

    public boolean isDev() {
        return hasCommandLineArg( AimxcelApplication.DEVELOPER_CONTROLS_COMMAND_LINE_ARG );
    }

    public Locale getLocale() {
        return AimxcelResources.readLocale();
    }

    /**
     * Should the updates feature be included at runtime?
     *
     * @return
     */
    public boolean isUpdatesFeatureIncluded() {
        return ( !hasCommandLineArg( "-updates-off" ) ) && DeploymentScenario.getInstance().isUpdatesEnabled();
    }

    /**
     * Should the statistics feature be included at runtime?
     *
     * @return
     */
    public boolean isStatisticsFeatureIncluded() {
        return ( !hasCommandLineArg( "-statistics-off" ) ) && DeploymentScenario.getInstance().isStatisticsEnabled();
    }

    public boolean isUpdatesEnabled() {
        return isUpdatesFeatureIncluded() && AimxcelPreferences.getInstance().isUpdatesEnabled();
    }

    public boolean isStatisticsEnabled() {
        return isStatisticsFeatureIncluded() && AimxcelPreferences.getInstance().isStatisticsEnabled();
    }

    // Provided so that customer installers can disable the sponsor feature via JNLP.
    public boolean isSponsorFeatureEnabled() {
        return !hasCommandLineArg( "-sponsor-off" );
    }

    public boolean isAskFeatureEnabled() {
        return hasCommandLineArg( "-ask" );
    }

    /**
     * Project JAR file is named <project>_all.jar
     */
    public static String getProjectJarName( String project ) {
        return project + "_all.jar";
    }
}
