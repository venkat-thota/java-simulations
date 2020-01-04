
package com.aimxcel.abclearn.common.aimxcelcommon.model.clock;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockListener;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.util.EventChannel;
public class VariableConstantTickClock implements IClock, ClockListener {
    private IClock wrappedClock;
    private double dt;
    private EventChannel clockEventChannel = new EventChannel( ClockListener.class );
    private ClockListener clockListenerProxy = (ClockListener) clockEventChannel.getListenerProxy();

    /**
     * @param clock The IClock that is to be decorated
     * @param dt    The dt that the clock is to start with
     */
    public VariableConstantTickClock( IClock clock, double dt ) {
        this.wrappedClock = clock;
        wrappedClock.addClockListener( this );
        this.dt = dt;
    }

    /**
     * Sets the amount of simulation time that elapses for each wrappedClock tick.
     *
     * @param dt
     */
    public void setDt( double dt ) {
        this.dt = dt;
    }

    //--------------------------------------------------------------------------------------------------
    // Implementation of ClockListener
    //--------------------------------------------------------------------------------------------------

    public void clockTicked( ClockEvent clockEvent ) {
        ClockEvent proxyEvent = new ClockEvent( this );
        clockListenerProxy.clockTicked( proxyEvent );
    }

    public void clockStarted( ClockEvent clockEvent ) {
        ClockEvent proxyEvent = new ClockEvent( this );
        clockListenerProxy.clockStarted( proxyEvent );
    }

    public void clockPaused( ClockEvent clockEvent ) {
        ClockEvent proxyEvent = new ClockEvent( this );
        clockListenerProxy.clockPaused( proxyEvent );
    }

    public void simulationTimeChanged( ClockEvent clockEvent ) {
        ClockEvent proxyEvent = new ClockEvent( this );
        clockListenerProxy.simulationTimeChanged( proxyEvent );
    }

    public void simulationTimeReset( ClockEvent clockEvent ) {
        ClockEvent proxyEvent = new ClockEvent( this );
        clockListenerProxy.simulationTimeReset( proxyEvent );
    }

    //--------------------------------------------------------------------------------------------------
    // Implementation of IClock
    //--------------------------------------------------------------------------------------------------

    /**
     * Overrides
     */
    public double getSimulationTimeChange() {
        return dt;
    }

    public void addClockListener( ClockListener clockListener ) {
        clockEventChannel.addListener( clockListener );
    }

    public void removeClockListener( ClockListener clockListener ) {
        clockEventChannel.removeListener( clockListener );
    }

    /**
     * Forwards
     */
    public void start() {
        wrappedClock.start();
    }

    public void pause() {
        wrappedClock.pause();
    }

    public boolean isPaused() {
        return wrappedClock.isPaused();
    }

    public boolean isRunning() {
        return wrappedClock.isRunning();
    }

    public void resetSimulationTime() {
        wrappedClock.resetSimulationTime();
    }

    public long getWallTime() {
        return wrappedClock.getWallTime();
    }

    public long getWallTimeChange() {
        return wrappedClock.getWallTimeChange();
    }

    public double getSimulationTime() {
        return wrappedClock.getSimulationTime();
    }

    public void setSimulationTime( double simulationTime ) {
        wrappedClock.setSimulationTime( simulationTime );
    }

    public void stepClockWhilePaused() {
        wrappedClock.stepClockWhilePaused();
    }

    public void stepClockBackWhilePaused() {
        wrappedClock.stepClockBackWhilePaused();
    }

    public boolean containsClockListener( ClockListener clockListener ) {
        return wrappedClock.containsClockListener( clockListener );
    }

    public void removeAllClockListeners() {
        clockEventChannel.removeAllListeners();
    }
}
