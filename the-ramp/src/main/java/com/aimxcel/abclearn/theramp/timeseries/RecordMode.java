
package com.aimxcel.abclearn.theramp.timeseries;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;


public class RecordMode extends Mode {
    private AimxcelTimer timer;
    private TimeSeriesModel timeSeriesModel;

    public RecordMode( final TimeSeriesModel timeSeriesModel ) {
        super( timeSeriesModel, "Record", true );
        timer = new AimxcelTimer( "Record Timer" );
        this.timeSeriesModel = timeSeriesModel;
    }

    public void initialize() {
        timeSeriesModel.repaintBackground();
        double recTime = timeSeriesModel.getRecordTime();
        timeSeriesModel.setReplayTime( recTime );
        timeSeriesModel.repaintBackground();
    }

    public void reset() {
        timer.reset();
    }

    public AimxcelTimer getTimer() {
        return timer;
    }

    public void clockTicked( ClockEvent event ) {
        double dt = event.getSimulationTimeChange();
        double recorderTime = timer.getTime();
        double maxTime = timeSeriesModel.getMaxAllowedTime();
        if ( !timeSeriesModel.isPaused() ) {

            double newTime = recorderTime + dt;
            if ( newTime > maxTime ) {
                dt = ( maxTime - recorderTime );
            }
            timer.stepInTime( dt, maxTime );

            timeSeriesModel.updateModel( event );

        }
    }

}
