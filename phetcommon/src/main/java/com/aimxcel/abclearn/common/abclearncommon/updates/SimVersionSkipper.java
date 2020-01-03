
package com.aimxcel.abclearn.common.abclearncommon.updates;

import com.aimxcel.abclearn.common.abclearncommon.preferences.AbcLearnPreferences;

/**
 * Skips a sim version by recording the skipped version number in the user's preferences.
 */
public class SimVersionSkipper implements IVersionSkipper {

    private final String project, sim;

    public SimVersionSkipper( String project, String sim ) {
        this.project = project;
        this.sim = sim;
    }

    public void setSkippedVersion( int skipVersion ) {
        AbcLearnPreferences.getInstance().setSimSkipUpdate( project, sim, skipVersion );
    }

    public int getSkippedVersion() {
        return AbcLearnPreferences.getInstance().getSimSkipUpdate( project, sim );
    }

    public boolean isSkipped( int version ) {
        return version == getSkippedVersion();
    }
}
