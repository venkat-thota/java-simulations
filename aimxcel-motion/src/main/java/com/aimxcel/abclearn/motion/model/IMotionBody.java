
package com.aimxcel.abclearn.motion.model;


public interface IMotionBody {
    double getVelocity();

    double getAcceleration();

    double getPosition();

    void addAccelerationData( double acceleration, double time );

    void addVelocityData( double v, double time );

    void addPositionData( double v, double time );

    void addPositionData( TimeData data );

    void addVelocityData( TimeData data );

    void addAccelerationData( TimeData data );

    int getAccelerationSampleCount();

    TimeData[] getRecentVelocityTimeSeries( int i );

    int getPositionSampleCount();

    int getVelocitySampleCount();

    TimeData[] getRecentPositionTimeSeries( int i );

    void setAcceleration( double value );

    void setPositionDriven();
}
