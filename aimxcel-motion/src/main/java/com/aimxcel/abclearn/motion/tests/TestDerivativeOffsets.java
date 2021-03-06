
package com.aimxcel.abclearn.motion.tests;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.motion.model.SingleBodyMotionModel;


public class TestDerivativeOffsets {
    private SingleBodyMotionModel motionModel;
    private ConstantDtClock clock;

    public TestDerivativeOffsets() {
        clock = new ConstantDtClock( 30, 1 );
        motionModel = new SingleBodyMotionModel( clock );
        motionModel.setPositionDriven();
    }

    private void step( int i ) {
        for ( int k = 0; k < i; k++ ) {
            clock.stepClockWhilePaused();
            showState( motionModel );
        }
    }

    private void showState( SingleBodyMotionModel motionModel ) {
//        System.out.println( "t=" + motionModel.getTime() + ", x=" + motionModel.getPosition() + ", v=" + motionModel.getVelocity() + ", a=" + motionModel.getAcceleration() );
        System.out.println( "x.t=" + motionModel.getTime() + ", x=" + motionModel.getMotionBody().getPosition() +
                            ", v.t=" + motionModel.getTime() + ", v=" + motionModel.getMotionBody().getVelocity() +
                            ", a.t=" + motionModel.getTime() + ", a=" + motionModel.getMotionBody().getAcceleration() );
    }

    private void start() {
        showState( motionModel );
        step( 100 );
        motionModel.getMotionBody().setPosition( 1.0 );
        step( 100 );
    }

    public static void main( String[] args ) {
        new TestDerivativeOffsets().start();
    }

}
