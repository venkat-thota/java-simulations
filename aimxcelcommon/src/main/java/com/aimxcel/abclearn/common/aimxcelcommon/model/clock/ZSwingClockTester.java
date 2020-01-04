

package com.aimxcel.abclearn.common.aimxcelcommon.model.clock;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.Clock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ZAbstractClockTester;

public class ZSwingClockTester extends ZAbstractClockTester {
    public ZSwingClockTester() {
        super( new ClockFactory() {
            public Clock createInstance( int defaultDelay, double v ) {
                SwingClock clock = new SwingClock( defaultDelay, v );

                clock.setCoalesce( false );

                return clock;
            }
        } );
    }
}
