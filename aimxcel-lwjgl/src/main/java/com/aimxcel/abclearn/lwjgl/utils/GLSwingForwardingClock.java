package com.aimxcel.abclearn.lwjgl.utils;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockListener;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;

public class GLSwingForwardingClock implements IClock {
    private final IClock clock;

    private final Map<ClockListener, ClockListener> swingToGLListenerMap = new HashMap<ClockListener, ClockListener>();

    public GLSwingForwardingClock( IClock clock ) {
        this.clock = clock;
    }

    public void start() {
        clock.start();
    }

    public void pause() {
        clock.pause();
    }

    public boolean isPaused() {
        return clock.isPaused();
    }

    public boolean isRunning() {
        return clock.isRunning();
    }

    public void addClockListener( final ClockListener clockListener ) {
        ClockListener swingListener = new ClockListener() {
            public void clockTicked( final ClockEvent clockEvent ) {
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        clockListener.clockTicked( clockEvent );
                    }
                } );
            }

            public void clockStarted( final ClockEvent clockEvent ) {
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        clockListener.clockStarted( clockEvent );
                    }
                } );
            }

            public void clockPaused( final ClockEvent clockEvent ) {
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        clockListener.clockPaused( clockEvent );
                    }
                } );
            }

            public void simulationTimeChanged( final ClockEvent clockEvent ) {
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        clockListener.simulationTimeChanged( clockEvent );
                    }
                } );
            }

            public void simulationTimeReset( final ClockEvent clockEvent ) {
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        clockListener.simulationTimeReset( clockEvent );
                    }
                } );
            }
        };
        swingToGLListenerMap.put( clockListener, swingListener );
        clock.addClockListener( swingListener );
    }

    public void removeClockListener( ClockListener clockListener ) {
        clock.removeClockListener( swingToGLListenerMap.get( clockListener ) );
    }

    public void removeAllClockListeners() {
        clock.removeAllClockListeners();
    }

    public void resetSimulationTime() {
        clock.resetSimulationTime();
    }

    public long getWallTime() {
        return clock.getWallTime();
    }

    public long getWallTimeChange() {
        return clock.getWallTimeChange();
    }

    public double getSimulationTimeChange() {
        return clock.getSimulationTimeChange();
    }

    public double getSimulationTime() {
        return clock.getSimulationTime();
    }

    public void setSimulationTime( double simulationTime ) {
        clock.setSimulationTime( simulationTime );
    }

    public void stepClockWhilePaused() {
        clock.stepClockWhilePaused();
    }

    public void stepClockBackWhilePaused() {
        clock.stepClockBackWhilePaused();
    }

    public boolean containsClockListener( ClockListener clockListener ) {
        return clock.containsClockListener( swingToGLListenerMap.get( clockListener ) );
    }
}
