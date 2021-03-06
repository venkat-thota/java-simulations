
package com.aimxcel.abclearn.motion.tests;

import junit.framework.TestCase;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.motion.model.SingleBodyMotionModel;

public class TestRecordTime extends TestCase {
    public void testRecordTime() {
        ConstantDtClock swingClock = new ConstantDtClock( 30, 1.0 );
        SingleBodyMotionModel motionModel = new SingleBodyMotionModel( swingClock );
        motionModel.getTimeSeriesModel().setRecordMode();
        for ( int i = 0; i < 100; i++ ) {
            swingClock.stepClockWhilePaused();
        }
        System.out.println( "Time=" + motionModel.getTime() );
        motionModel.getTimeSeriesModel().setPlaybackMode();
        motionModel.getTimeSeriesModel().setPlaybackTime( motionModel.getTime() / 2.0 );

        motionModel.getTimeSeriesModel().setRecordMode();
        for ( int i = 0; i < 100; i++ ) {
            swingClock.stepClockWhilePaused();
        }
        System.out.println( "Time=" + motionModel.getTime() );
    }
}
