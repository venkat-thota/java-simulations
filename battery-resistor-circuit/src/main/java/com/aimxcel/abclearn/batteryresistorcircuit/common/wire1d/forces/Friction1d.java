package com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.forces;

import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.Force1d;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;

/**
 * Applies a force equals to -bv
 */
public class Friction1d implements Force1d {
    double value;

    public Friction1d( double value ) {
        this.value = value;
    }

    public double getForce( WireParticle wp ) {
        double v = wp.getVelocity();
        double f = -v * value;
        return f;
    }
}
