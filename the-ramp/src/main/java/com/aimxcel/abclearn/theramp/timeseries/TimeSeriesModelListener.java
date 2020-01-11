


package com.aimxcel.abclearn.theramp.timeseries;


public interface TimeSeriesModelListener {
    void recordingStarted();

    void recordingPaused();

    void recordingFinished();

    void playbackStarted();

    void playbackPaused();

    void playbackFinished();

    void reset();

    void rewind();
}
