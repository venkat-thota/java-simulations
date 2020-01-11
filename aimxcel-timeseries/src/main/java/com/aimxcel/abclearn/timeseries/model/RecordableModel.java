
package com.aimxcel.abclearn.timeseries.model;


public interface RecordableModel {
    void stepInTime( double simulationTimeChange );

    Object getState();

    void setState( Object o );

    void resetTime();

    void clear();
}
