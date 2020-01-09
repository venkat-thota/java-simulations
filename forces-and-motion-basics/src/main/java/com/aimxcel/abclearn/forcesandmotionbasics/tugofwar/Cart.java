package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty.DoubleProperty;
class Cart {
    public final double weight = 1000 * 10;//Compare to puller weight in PullerNode
    private double velocity = 0;
    public final DoubleProperty position = new DoubleProperty( 0.0 );

    public double getPosition() { return position.get(); }

    public void stepInTime( double dt, double acceleration ) {
        dt = dt * 35;//speed up time because the masses of the characters give everything too much momentum
        velocity = velocity + acceleration * dt;
        position.set( position.get() + velocity * dt );
    }

    public void restart() {
        velocity = 0.0;
        position.reset();
    }
}