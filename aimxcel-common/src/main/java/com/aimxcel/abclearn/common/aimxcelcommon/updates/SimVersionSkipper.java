
package com.aimxcel.abclearn.common.aimxcelcommon.updates;

import com.aimxcel.abclearn.common.aimxcelcommon.preferences.AimxcelPreferences;

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
        AimxcelPreferences.getInstance().setSimSkipUpdate( project, sim, skipVersion );
    }

    public int getSkippedVersion() {
        return AimxcelPreferences.getInstance().getSimSkipUpdate( project, sim );
    }

    public boolean isSkipped( int version ) {
        return version == getSkippedVersion();
    }
}
