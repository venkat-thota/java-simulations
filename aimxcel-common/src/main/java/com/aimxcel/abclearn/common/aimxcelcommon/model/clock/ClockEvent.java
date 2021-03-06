package com.aimxcel.abclearn.common.aimxcelcommon.model.clock;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;


public class ClockEvent {
    private IClock clock;

    /**
     * Construct a ClockEvent with the specified source clock.
     *
     * @param clock
     */
    public ClockEvent( IClock clock ) {
        this.clock = clock;
    }

    /**
     * Gets the source clock for this ClockEvent.
     *
     * @return the source clock for this ClockEvent.
     */
    public IClock getClock() {
        return clock;
    }

    /**
     * Determine how many milliseconds passed since the previous tick.
     *
     * @return how many milliseconds of wall-time passed since the previous tick.
     */
    public long getWallTimeChange() {
        return clock.getWallTimeChange();
    }

    /**
     * Get the time change in simulation time units.
     *
     * @return the time change in simulation time units.
     */
    public double getSimulationTimeChange() {
        return clock.getSimulationTimeChange();
    }

    /**
     * Get the current running time of the simulation.
     *
     * @return the current running time of the simulation.
     */
    public double getSimulationTime() {
        return clock.getSimulationTime();
    }

    /**
     * Determine the last read wall-clock time.
     *
     * @return the last read wall-clock time.
     */
    public long getWallTime() {
        return clock.getWallTime();
    }

}