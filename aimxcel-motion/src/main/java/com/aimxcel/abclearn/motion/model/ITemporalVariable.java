
package com.aimxcel.abclearn.motion.model;


public interface ITemporalVariable extends IVariable {
    TimeData[] getData( double startTime, double endTime );

    TimeData[] getRecentSeries( int numPts );

    TimeData getData( int index );

    TimeData[] getData( int index, int requestedPoints );

    TimeData getRecentData( int index );

    int getSampleCount();

    void clear();

    TimeData getMin();

    TimeData getMax();

    void addValue( double magnitude, double time );

    void addValue( TimeData timeData );

    void setPlaybackTime( double time );

    void addListener( Listener listener );

    void removeListener( Listener listener );

    //computes an average using min(s,numSamples) data points
    double estimateAverage( int s );

    int getIndexForTime( double time );

    void setTimeData( int index, double time, double value );

    int[] getIndicesForTimeInterval( double t0, double t1 );

    void removeAll( int[] indices );

    ITemporalVariable getDerivative();

    ITemporalVariable getIntegral();

    void stepInTime( double dt );

    public static interface Listener extends IVariable.Listener {
        void dataAdded( TimeData data );

        void dataCleared();
    }

    public static class ListenerAdapter implements Listener {
        public void dataAdded( TimeData data ) {
        }

        public void dataCleared() {
        }

        public void valueChanged() {
        }
    }
}
