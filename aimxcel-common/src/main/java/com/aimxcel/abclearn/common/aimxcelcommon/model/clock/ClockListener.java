
 package com.aimxcel.abclearn.common.aimxcelcommon.model.clock;

import java.util.EventListener;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;


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