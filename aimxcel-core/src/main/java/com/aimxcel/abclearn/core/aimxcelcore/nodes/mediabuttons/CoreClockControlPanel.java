
package com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.TimeControlListener;


public class CoreClockControlPanel extends CoreTimeControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IClock clock;
    private ClockAdapter clockListener;

    /**
     * Constructs a ClockControlPanel
     *
     * @param clock must be non-null
     */
    public CoreClockControlPanel( final IClock clock ) {
        if ( clock == null ) {
            throw new RuntimeException( "Cannot have a control panel for a null clock." );
        }
        this.clock = clock;

        // Update the clock in response to control panel events
        addTimeControlListener( new TimeControlListener() {
            public void stepPressed() {
                clock.stepClockWhilePaused();
            }

            public void playPressed() {
                clock.start();
            }

            public void pausePressed() {
                clock.pause();
            }

            public void restartPressed() {
                clock.resetSimulationTime();
            }

            public void stepBackPressed() {
                clock.stepClockBackWhilePaused();
            }
        } );

        // Update the control panel when the clock changes
        clockListener = new ClockAdapter() {

            public void clockStarted( ClockEvent clockEvent ) {
                setPaused( clock.isPaused() );
            }

            public void clockPaused( ClockEvent clockEvent ) {
                setPaused( clock.isPaused() );
            }

            public void simulationTimeChanged( ClockEvent clockEvent ) {
                setTimeDisplay( clock.getSimulationTime() );
            }

            public void simulationTimeReset( ClockEvent clockEvent ) {
                // do nothing
            }
        };
        clock.addClockListener( clockListener );

        // initial state
        setPaused( clock.isPaused() );
        setTimeDisplay( clock.getSimulationTime() );
    }

    /**
     * Call this method before releasing all references to this object.
     */
    public void cleanup() {
        clock.removeClockListener( clockListener );
        //I don't think the TimeControlListener needs to be removed in order to properly clean up this object.
    }

    /**
     * Gets the clock that's being controlled.
     *
     * @return IClock
     */
    protected IClock getClock() {
        return clock;
    }
}
