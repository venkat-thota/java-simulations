package com.aimxcel.abclearn.forcesandmotionbasics.motion;

import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.UserComponents;

import com.aimxcel.abclearn.common.aimxcelcommon.model.Resettable;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.core.aimxcelcore.SimSharingCoreModule;


public class MotionModule extends SimSharingCoreModule implements Resettable {
    private final boolean friction;
    private final boolean accelerometer;

    public MotionModule( UserComponents component, String title, boolean friction, boolean accelerometer ) {
        super( component, title, new ConstantDtClock( (int) ( 1000.0 / ConstantDtClock.DEFAULT_FRAMES_PER_SECOND ), 1.0 / ConstantDtClock.DEFAULT_FRAMES_PER_SECOND / 2.0 ) );
        setSimulationPanel( new MotionCanvas( this, getClock(), friction, accelerometer ) );
        setClockControlPanel( null );
        this.friction = friction;
        this.accelerometer = accelerometer;
    }

    @Override public void reset() {
        super.reset();
        setSimulationPanel( new MotionCanvas( this, getClock(), friction, accelerometer ) );
    }
}