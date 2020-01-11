


package edu.colorado.phet.theramp.timeseries;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockListener;


public abstract class Mode implements ClockListener {
    private String name;
    private TimeSeriesModel module;

    public Mode( TimeSeriesModel module, String name, boolean takingData ) {
        this.module = module;
        this.name = name;
    }

    public abstract void initialize();

    public String getName() {
        return name;
    }

    public void clockStarted( ClockEvent clockEvent ) {
    }

    public void clockPaused( ClockEvent clockEvent ) {
    }

    public void simulationTimeChanged( ClockEvent clockEvent ) {
    }

    public void simulationTimeReset( ClockEvent clockEvent ) {
    }

    public TimeSeriesModel getTimeSeriesModel() {
        return module;
    }
}