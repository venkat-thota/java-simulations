
package com.aimxcel.abclearn.games;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
public class GameTimer {

    // properties directly accessible by clients
    public final Property<Long> time; // ms

    private final IClock clock;
    private long startTime; // System time in ms when the game started

    public GameTimer() {
        this.clock = new ConstantDtClock( 1000 / 5, 1 ); // sufficient for showing seconds
        this.time = new Property<Long>( 0L );
        clock.addClockListener( new ClockAdapter() {
            @Override
            public void clockTicked( ClockEvent clockEvent ) {
                time.set( clockEvent.getWallTime() - startTime );
            }
        } );
    }

    /**
     *  Is the timer running?
     *  @returns true or false
     */
    public boolean isRunning() {
        return clock.isRunning();
    }

    /**
     * Starts the timer. When the timer is started, it resets to zero.
     * Note that calling start while the timer is running resets the time to zero.
     */
    public void start() {
        time.set( 0L );
        startTime = System.currentTimeMillis(); // don't use clock.getWallTime, it's not valid until the clock ticks
        clock.start();
    }

    /**
     * Stops the timer.
     * If the timer is already stopped, this is a no-op.
     */
    public void stop() {
        if ( isRunning() ) {
            clock.pause();
        }
    }

    /**
     * Convenience method for starting/stopping the timer, for avoiding if-then-else logic in client.
     */
    public void setRunning( boolean running ) {
        if ( running ) {
            start();
        }
        else {
            stop();
        }
    }
}
