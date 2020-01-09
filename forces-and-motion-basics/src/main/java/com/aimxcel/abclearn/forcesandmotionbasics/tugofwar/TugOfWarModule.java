package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Strings;
import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.UserComponents;

import com.aimxcel.abclearn.common.aimxcelcommon.model.Resettable;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.core.aimxcelcore.SimSharingCoreModule;

public class TugOfWarModule extends SimSharingCoreModule implements Resettable {
    public TugOfWarModule() {
        super( UserComponents.tugOfWarTab, Strings.TUG_OF_WAR, new ConstantDtClock() );
        setSimulationPanel( new TugOfWarCanvas( this, getClock() ) );
        setClockControlPanel( null );
    }

    @Override public void reset() {
        super.reset();
        setSimulationPanel( new TugOfWarCanvas( this, getClock() ) );
    }
}