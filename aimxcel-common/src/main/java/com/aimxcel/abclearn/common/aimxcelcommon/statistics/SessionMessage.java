
package com.aimxcel.abclearn.common.aimxcelcommon.statistics;

import java.util.Date;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.application.SessionCounter;
import com.aimxcel.abclearn.common.aimxcelcommon.files.AimxcelInstallation;
import com.aimxcel.abclearn.common.aimxcelcommon.preferences.AimxcelPreferences;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DeploymentScenario;
import com.aimxcel.abclearn.common.aimxcelcommon.util.JavaVersion.JREVersion;


public class SessionMessage extends StatisticsMessage {

    // Versioning the messages allows us to manage data after changing message content.
    // If the content of this message is changed, you'll need to increment the version number.
    public static final String MESSAGE_VERSION = "0";

    private static SessionMessage instance;

    public static SessionMessage initInstance( ISimInfo simInfo ) {
        if ( instance != null ) {
            throw new RuntimeException( "initInstance was called more than once" );
        }
        else {
            instance = new SessionMessage( simInfo );
        }
        return instance;
    }

    public static SessionMessage getInstance() {
        return instance;
    }

    /* singleton */
    private SessionMessage( ISimInfo simInfo ) {
        super( "session", MESSAGE_VERSION );

        initTimeZone();
        JREVersion jre = new JREVersion();

        String userInstallationTimestamp = null;
        if ( DeploymentScenario.getInstance() == DeploymentScenario.AIMXCEL_INSTALLATION ) {
            AimxcelInstallation p = AimxcelInstallation.getInstance();
            userInstallationTimestamp = String.valueOf( p.getInstallationTimestamp() );
        }

        StatisticsMessageField[] fields = new StatisticsMessageField[] {

                // Sim data
                new StatisticsMessageField( "Common.statistics.sim_project", "sim_project", simInfo.getProjectName() ),
                new StatisticsMessageField( "Common.statistics.sim_name", "sim_name", simInfo.getFlavor() ),
                new StatisticsMessageField( "Common.statistics.sim_major_version", "sim_major_version", simInfo.getVersion().getMajor() ),
                new StatisticsMessageField( "Common.statistics.sim_minor_version", "sim_minor_version", simInfo.getVersion().getMinor() ),
                new StatisticsMessageField( "Common.statistics.sim_dev_version", "sim_dev_version", simInfo.getVersion().getDev() ),
                new StatisticsMessageField( "Common.statistics.sim_revision", "sim_revision", simInfo.getVersion().getRevision() ),
                new StatisticsMessageField( "Common.statistics.sim_version_timestamp", "sim_version_timestamp", simInfo.getVersion().getTimestampSeconds() ),
                new StatisticsMessageField( "Common.statistics.sim_distribution_tag", "sim_distribution_tag", simInfo.getDistributionTag() ),
                new StatisticsMessageField( "Common.statistics.sim_locale_language", "sim_locale_language", AimxcelResources.readLocale().getLanguage() ),
                new StatisticsMessageField( "Common.statistics.sim_locale_country", "sim_locale_country", AimxcelResources.readLocale().getCountry() ),
                new StatisticsMessageField( "Common.statistics.sim_deployment", "sim_deployment", DeploymentScenario.getInstance().toString() ),
                new StatisticsMessageField( "Common.statistics.sim_dev", "sim_dev", simInfo.isDev() + "" ),
                new StatisticsMessageField( "Common.statistics.sim_total_sessions", "sim_total_sessions", SessionCounter.getInstance().getCount() ),
                new StatisticsMessageField( "Common.statistics.sim_sessions_since", "sim_sessions_since", SessionCounter.getInstance().getCountSince() ),

                // Host data
                new StatisticsMessageField.SystemProperty( "Common.statistics.host_os_name", "host_os_name", "os.name" ),
                new StatisticsMessageField.SystemProperty( "Common.statistics.host_os_version", "host_os_version", "os.version" ),
                new StatisticsMessageField.SystemProperty( "Common.statistics.host_os_arch", "host_os_arch", "os.arch" ),
                new StatisticsMessageField.SystemProperty( "Common.statistics.host_java_vendor", "host_java_vendor", "java.vendor" ),
                new StatisticsMessageField( "Common.statistics.host_java_version_major", "host_java_version_major", jre.getMajorNumber() ),
                new StatisticsMessageField( "Common.statistics.host_java_version_minor", "host_java_version_minor", jre.getMinorNumber() ),
                new StatisticsMessageField( "Common.statistics.host_java_version_maintenance", "host_java_version_maintenance", jre.getMaintenanceNumber() ),
                new StatisticsMessageField.SystemProperty( "Common.statistics.host_java_webstart_version", "host_java_webstart_version", "javawebstart.version" ),
                new StatisticsMessageField.SystemProperty( "Common.statistics.host_locale_language", "host_locale_language", "user.language" ),
                new StatisticsMessageField.SystemProperty( "Common.statistics.host_locale_country", "host_locale_country", "user.country" ),
                new StatisticsMessageField.SystemProperty( "Common.statistics.host_java_timezone", "host_java_timezone", "user.timezone" ),

                // User data
                new StatisticsMessageField( "Common.statistics.user_preference_file_creation_time", "user_preference_file_creation_time", AimxcelPreferences.getInstance().getPreferencesFileCreationTime() ),
                new StatisticsMessageField( "Common.statistics.user_total_sessions", "user_total_sessions", SessionCounter.getInstance().getTotal() ),
                new StatisticsMessageField( "Common.statistics.user_installation_timestamp", "user_installation_timestamp", userInstallationTimestamp )
        };
        super.addFields( fields );
    }

    private void initTimeZone() {
        //for some reason, user.timezone only appears if the next line is used (otherwise user.timezone is empty or null)
        new Date().toString();
    }
}
