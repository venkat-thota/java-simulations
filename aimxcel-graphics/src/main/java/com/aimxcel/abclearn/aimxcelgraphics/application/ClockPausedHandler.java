
 
package com.aimxcel.abclearn.aimxcelgraphics.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;


class ClockPausedHandler extends ClockAdapter {

    private static int DEFAULT_DELAY = 500; // time between refreshes, in milliseconds

    private AimxcelGraphicsModule module; // the Module that we're associated with
    private Timer timer;

    /**
     * Creates a ClockPausedHandler with a default delay.
     *
     * @param module
     */
    public ClockPausedHandler( AimxcelGraphicsModule module ) {
        this( module, DEFAULT_DELAY );
    }

    /**
     * Creates a ClockPausedHandler with a specific delay.
     *
     * @param module
     * @param delay  the delay, in milliseconds
     */
    public ClockPausedHandler( AimxcelGraphicsModule module, int delay ) {
        this.module = module;
        timer = new Timer( delay, new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                refresh( event );
            }
        } );
    }

    /**
     * ActionListener implementation.
     * Anything that needs to be refreshed should be done here.
     * The module will be refreshed only while it is active.
     */
    private void refresh( ActionEvent event ) {
        if ( event.getSource() == timer && this.module.isActive() ) {
            this.module.refresh();
        }
    }

    public void clockPaused( ClockEvent clockEvent ) {
        timer.start();
    }

    public void clockStarted( ClockEvent clockEvent ) {
        timer.stop();
    }

}
