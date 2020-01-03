
package com.aimxcel.abclearn.common.abclearncommon.updates;

import com.aimxcel.abclearn.common.abclearncommon.math.MathUtil;
import com.aimxcel.abclearn.common.abclearncommon.preferences.AbcLearnPreferences;

/**
 * "Ask Me Later" strategy for installer updates.
 */
public class InstallerAskMeLaterStrategy implements IAskMeLaterStrategy {

    private static final long DEFAULT_DURATION = MathUtil.daysToMilliseconds( 30 );

    private long duration; // ms

    public InstallerAskMeLaterStrategy() {
        duration = DEFAULT_DURATION;
    }

    public void setStartTime( long time ) {
        AbcLearnPreferences.getInstance().setInstallerAskMeLater( time );
    }

    public long getStartTime() {
        return AbcLearnPreferences.getInstance().getInstallerAskMeLater();
    }

    public void setDuration( long duration ) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isDurationExceeded() {
        long askMeLaterPressed = getStartTime();
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - askMeLaterPressed;
        return elapsedTime > getDuration() || askMeLaterPressed == 0;
    }

}
