
package com.aimxcel.abclearn.motion.tests;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.motion.model.SingleBodyMotionModel;
import com.aimxcel.abclearn.motion.model.TimeData;
import com.aimxcel.abclearn.motion.model.UpdateStrategy;


public class TestConstantVelocity {
    public static void main( String[] args ) {
        UpdateStrategy.PositionDriven updateRule = new UpdateStrategy.PositionDriven( 4, 4, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY );
        SingleBodyMotionModel model = new SingleBodyMotionModel( 4, 4, 4, new ConstantDtClock( 30, 1 ) );
        model.setUpdateStrategy( updateRule );
        System.out.println( "init state=" + model );
        for ( int i = 0; i <= 100; i++ ) {
            model.stepInTime( 1.0 );
            System.out.println( "i = " + i + ", state=" + model );
        }

        TimeData[] timeData = model.getRecentAccelerationTimeSeries( 5 );
        for ( int i = 0; i < timeData.length; i++ ) {
            TimeData data = timeData[i];
            System.out.println( "i = " + i + ", data=" + data );
        }
    }
}
