
package com.aimxcel.abclearn.common.abclearncommon.application;

import java.util.Locale;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnVersion;

public interface ISimInfo {

    String getName();

    AbcLearnVersion getVersion();

    /**
     * Should the updates feature be included at runtime?
     *
     * @return
     */
    boolean isUpdatesFeatureIncluded();

    /**
     * Should the statistics feature be included at runtime?
     *
     * @return
     */
    boolean isStatisticsFeatureIncluded();

    /**
     * Is automatic checking for updates enabled?
     * This is based on the users preferences and whether the feature is included at runtime.
     *
     * @return
     */
    boolean isUpdatesEnabled();

    /**
     * Is statistics enabled?
     * This is based on the users preferences and whether the feature is included at runtime.
     *
     * @return
     */
    boolean isStatisticsEnabled();

    /**
     * Should the user have access to the Preferences dialog?
     * This is true if at least one of the features in the preferences dialog is included at runtime.
     *
     * @return
     */
    boolean isPreferencesEnabled();

    String getProjectName();

    String getFlavor();

    String[] getCommandLineArgs();

    boolean hasCommandLineArg( String s );

    /**
     * Was this sim run in developer mode (-dev program arg)?
     *
     * @return
     */
    boolean isDev();

    /**
     * Gets the locale that we're using to decide which string translations to load.
     *
     * @return
     */
    Locale getLocale();

    public String getDistributionTag();
}
