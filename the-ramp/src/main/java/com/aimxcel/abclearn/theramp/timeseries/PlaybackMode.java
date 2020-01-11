
package com.aimxcel.abclearn.theramp.timeseries;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;



public class PlaybackMode extends Mode {
    private double playbackSpeed;
    private AimxcelTimer timer;
    private TimeSeriesModel timeSeriesModel;

    public PlaybackMode( TimeSeriesModel timeSeriesModel ) {
        super( timeSeriesModel, "Playback", false );
        this.timeSeriesModel = timeSeriesModel;
        timer = new AimxcelTimer( "Playback Timer" );
    }

    public void setPlaybackSpeed( double playbackSpeed ) {
        this.playbackSpeed = playbackSpeed;
    }

    public void initialize() {
        timeSeriesModel.repaintBackground();
    }

    public void clockTicked( ClockEvent event ) {
        double dt = event.getSimulationTimeChange();
        if ( !timeSeriesModel.isPaused() ) {
            timeSeriesModel.getPlaybackTimer().stepInTime( dt * playbackSpeed, timeSeriesModel.getRecordTimer().getTime() );
            double playTime = timeSeriesModel.getPlaybackTimer().getTime();
            double recTime = timeSeriesModel.getRecordTimer().getTime();
            if ( playTime < recTime ) {
                timeSeriesModel.setReplayTime( playTime );
            }
            else {
                timeSeriesModel.setPaused( true );
                timeSeriesModel.firePlaybackFinished();
            }
        }
    }

    public void reset() {
        timer.reset();
    }

    public void rewind() {
        timeSeriesModel.setReplayTime( 0.0 );
        timer.setTime( 0 );
    }

    public AimxcelTimer getTimer() {
        return timer;
    }
}
