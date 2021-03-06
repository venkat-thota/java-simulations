package com.aimxcel.abclearn.batteryresistorcircuit.oscillator2d;

import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.DoublePoint;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.PropagatingParticle;

public class Core extends PropagatingParticle {
    DoublePoint origin;
    double scalarPosition;

    public Core( DoublePoint origin, double scalarPosition ) {
        this.scalarPosition = scalarPosition;
        this.origin = origin;
    }

    public double getScalarPosition() {
        return scalarPosition;
    }

    public DoublePoint getOrigin() {
        return origin;
    }
}
