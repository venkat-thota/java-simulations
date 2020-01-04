
package com.aimxcel.abclearn.common.aimxcelcommon.updates;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.preferences.AimxcelPreferences;

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
        AimxcelPreferences.getInstance().setInstallerAskMeLater( time );
    }

    public long getStartTime() {
        return AimxcelPreferences.getInstance().getInstallerAskMeLater();
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
