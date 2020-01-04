
 package com.aimxcel.abclearn.common.aimxcelcommon.util;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;


public class FrameRateReporter {

    
    public FrameRateReporter( IClock clock ) {
        clock.addClockListener( new ClockAdapter() {
            int frameCnt = 0;
            long lastTickTime = System.currentTimeMillis();
            long averagingTime = 1000;

            public void clockTicked( ClockEvent event ) {
                frameCnt++;
                long currTime = System.currentTimeMillis();
                if ( currTime - lastTickTime > averagingTime ) {
                    double rate = frameCnt * 1000 / ( currTime - lastTickTime );
                    lastTickTime = currTime;
                    frameCnt = 0;
                    System.out.println( "rate = " + rate );
                }
            }
        } );
    }
}
