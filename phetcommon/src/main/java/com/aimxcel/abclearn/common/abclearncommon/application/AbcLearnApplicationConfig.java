

package com.aimxcel.abclearn.common.abclearncommon.application;

import java.util.Arrays;
import java.util.Locale;

import com.aimxcel.abclearn.common.abclearncommon.preferences.AbcLearnPreferences;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnResources;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnVersion;
import com.aimxcel.abclearn.common.abclearncommon.util.DeploymentScenario;
import com.aimxcel.abclearn.common.abclearncommon.util.logging.LoggingUtils;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnLookAndFeel;
import com.aimxcel.abclearn.common.abclearncommon.view.util.FrameSetup;
import com.aimxcel.abclearn.common.abclearncommon.view.util.StringUtil;

import com.aimxcel.abclearn.common.abclearncommon.application.ISimInfo;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;

/**
 * AbcLearnApplicationConfig encapsulates the information required to configure
 * a AbcLearnApplication, including transparent access to the project's
 * properties file. It is also responsible for launching an application.
 * <p/>
 * Some terminology:
 * <ul>
 * <li>A project is a directory name in the PhET source code repository.
 * <li>More than one simulation may live under a project directory, be built
 * from the project's source code, and use the project's resources.
 * Each of these simulations is referred to as a flavor.
 * <li>If a flavor name is not specified, it defaults to the project name.
 * </ul>
 *
 * @author John De Goes / Chris Malley
 */
public class AbcLearnApplicationConfig implements ISimInfo {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    //This frame setup should be used for all phet applications, to ensure a consistent window size
    public static final FrameSetup.CenteredWithSize DEFAULT_FRAME_SETUP = new FrameSetup.CenteredWithSize( 1024, 768 );

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    // immutable
    private final String[] commandLineArgs;
    private final String flavor;
    private final AbcLearnResources resourceLoader;

    // mutable
    private FrameSetup frameSetup;
    private AbcLearnLookAndFeel phetLookAndFeel = new AbcLearnLookAndFeel(); // the look and feel to be initialized in launchSim

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Constructor where project & flavor names are identical.
     *
     * @param commandLineArgs
     * @param project
     */
    public AbcLearnApplicationConfig( String[] commandLineArgs, String project ) {
        this( commandLineArgs, project, project );
    }

    /**
     * Constructor where project & flavor names are different.
     *
     * @param commandLineArgs
     * @param project
     * @param flavor
     */
    public AbcLearnApplicationConfig( String[] commandLineArgs, String project, String flavor ) {
        this.commandLineArgs = commandLineArgs;
        if ( hasCommandLineArg( "-log" ) ) {
            LoggingUtils.enableAllLogging( "com.aimxcel.abclearn.common.abclearncommon" );
        }
        this.flavor = flavor;
        this.resourceLoader = new AbcLearnResources( project );
        this.frameSetup = DEFAULT_FRAME_SETUP;
        this.phetLookAndFeel = new AbcLearnLookAndFeel();
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

    public void setLookAndFeel( AbcLearnLookAndFeel phetLookAndFeel ) {
        this.phetLookAndFeel = phetLookAndFeel;
    }

    public AbcLearnLookAndFeel getLookAndFeel() {
        return phetLookAndFeel;
    }

    public AbcLearnResources getResourceLoader() {
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
    public AbcLearnVersion getVersion() {
        return resourceLoader.getVersion();
    }

    public boolean isDev() {
        return hasCommandLineArg( AbcLearnApplication.DEVELOPER_CONTROLS_COMMAND_LINE_ARG );
    }

    public Locale getLocale() {
        return AbcLearnResources.readLocale();
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
        return isUpdatesFeatureIncluded() && AbcLearnPreferences.getInstance().isUpdatesEnabled();
    }

    public boolean isStatisticsEnabled() {
        return isStatisticsFeatureIncluded() && AbcLearnPreferences.getInstance().isStatisticsEnabled();
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
