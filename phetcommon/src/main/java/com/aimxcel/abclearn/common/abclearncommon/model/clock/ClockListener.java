

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package com.aimxcel.abclearn.common.abclearncommon.model.clock;

import java.util.EventListener;

import com.aimxcel.abclearn.common.abclearncommon.model.clock.ClockEvent;

/**
 * Listens to changes in both Clock state (running, paused)
 * and clock ticks  (in both wall time and simulation time).
 */

public interface ClockListener extends EventListener {
    /**
     * Invoked when the clock ticks.
     *
     * @param clockEvent
     */
    void clockTicked( ClockEvent clockEvent );

    /**
     * Invoked when the clock starts.
     *
     * @param clockEvent
     */
    void clockStarted( ClockEvent clockEvent );

    /**
     * Invoked when the clock is paused.
     *
     * @param clockEvent
     */
    void clockPaused( ClockEvent clockEvent );

    /**
     * Invoked when the running time of the simulation has changed.
     *
     * @param clockEvent
     */
    void simulationTimeChanged( ClockEvent clockEvent );

    /**
     * Invoked when the clock's simulation time is reset.
     *
     * @param clockEvent
     */
    void simulationTimeReset( ClockEvent clockEvent );
}