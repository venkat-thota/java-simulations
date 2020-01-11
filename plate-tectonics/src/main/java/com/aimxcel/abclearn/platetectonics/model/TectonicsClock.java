// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.platetectonics.model;

import java.util.ArrayList;
import java.util.List;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing.ModelActions;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing.ModelComponents;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockListener;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ChangeObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ModelComponentTypes;

/**
 * Our clock is pushed forward by LWJGL by almost arbitrary amounts of time (depending on system performance).
 * Additionally, we need time limits for the tab animations and that is easiest to integrate into the actual clock.
 * There is also a time multiplier to allow a separate "time rate" control.
 * <p/>
 * For this simulation, the clock's reported time changes are in millions of years.
 * <p/>
 * Some aspects of the normal ConstantDtClock and superclasses are copied over for correct general clock behavior
 * TODO: investigate modifying common clock code to remove code duplication
 */
public class TectonicsClock implements IClock {
    private double lastSimulationTime = 0.0;
    private double simulationTime = 0.0;
    private long lastWallTime = 0;
    private long wallTime = 0;

    private double timeMultiplier;
    private final List<ClockListener> listeners = new ArrayList<ClockListener>();
    private final Property<Boolean> running = new Property<Boolean>( false );

    private double timeLimit = Double.MAX_VALUE;

    public TectonicsClock( double timeMultiplier ) {
        this.timeMultiplier = timeMultiplier;

        running.addObserver( new ChangeObserver<Boolean>() {
            public void update( Boolean runningNow, Boolean wasRunning ) {
                ClockEvent event = new ClockEvent( TectonicsClock.this );
                if ( runningNow ) {
                    for ( ClockListener listener : listeners ) {
                        listener.clockStarted( event );
                    }
                }
                else {
                    for ( ClockListener listener : listeners ) {
                        listener.clockPaused( event );
                    }
                }
            }
        } );
    }

    public synchronized void stepByWallSeconds( float timeElapsed ) {
        if ( running.get() ) {
            stepByWallSecondsForced( timeElapsed );
        }
    }

    public synchronized void stepByWallSecondsForced( float timeElapsed ) {
        assert !Float.isNaN( timeElapsed );
        tick( timeElapsed );
    }

    protected synchronized void tick( float timeElapsed ) {
        lastWallTime = wallTime;
        wallTime = System.currentTimeMillis();

        // bail if we are already at the maximum time limit
        if ( simulationTime >= timeLimit ) {
            return;
        }

        // don't go past the time limit
        double proposedTime = simulationTime + timeElapsed * timeMultiplier;
        if ( proposedTime > timeLimit ) {
            proposedTime = timeLimit;
            SimSharingManager.sendModelMessage( ModelComponents.time, ModelComponentTypes.feature, ModelActions.maximumTimeReached );
        }

        setSimulationTime( proposedTime );

        ClockEvent event = new ClockEvent( this );
        for ( ClockListener listener : listeners ) {
            listener.clockTicked( event );
        }
    }

    public synchronized void start() {
        running.set( true );
    }

    public synchronized void pause() {
        running.set( false );
    }

    public synchronized boolean isPaused() {
        return !running.get();
    }

    public synchronized boolean isRunning() {
        return running.get();
    }

    public synchronized void addClockListener( ClockListener clockListener ) {
        listeners.add( clockListener );
    }

    public synchronized void removeClockListener( ClockListener clockListener ) {
        listeners.remove( clockListener );
    }

    public synchronized void resetSimulationTime() {
        setSimulationTime( 0 );

        ClockEvent event = new ClockEvent( this );
        for ( ClockListener listener : listeners ) {
            listener.simulationTimeReset( event );
        }
    }

    public synchronized long getWallTime() {
        return wallTime;
    }

    public synchronized long getWallTimeChange() {
        return wallTime - lastWallTime;
    }

    public synchronized double getSimulationTimeChange() {
        return simulationTime - lastSimulationTime;
    }

    public synchronized double getSimulationTime() {
        return simulationTime;
    }

    public synchronized void setSimulationTime( double simulationTime ) {
        lastSimulationTime = this.simulationTime;
        this.simulationTime = simulationTime;

        ClockEvent event = new ClockEvent( this );
        for ( ClockListener listener : listeners ) {
            listener.simulationTimeChanged( event );
        }
    }

    public synchronized void stepClockWhilePaused() {
        // TODO: updating here isn't fully working (nicely). investigate
        tick( 5 );
    }

    public synchronized void stepClockBackWhilePaused() {
        tick( 5 );
    }

    public synchronized boolean containsClockListener( ClockListener clockListener ) {
        return listeners.contains( clockListener );
    }

    public void removeAllClockListeners() {
        listeners.clear();
    }

    public synchronized double getTimeMultiplier() {
        return timeMultiplier;
    }

    public synchronized void setTimeMultiplier( double timeMultiplier ) {
        this.timeMultiplier = timeMultiplier;
    }

    public double getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit( double timeLimit ) {
        this.timeLimit = timeLimit;
    }

    public void resetTimeLimit() {
        timeLimit = Double.MAX_VALUE;
    }
}
