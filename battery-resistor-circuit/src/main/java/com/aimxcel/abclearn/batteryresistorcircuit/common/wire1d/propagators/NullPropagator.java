package com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.propagators;

import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.Propagator1d;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;

public class NullPropagator implements Propagator1d {
    public void propagate( WireParticle wp, double dt ) {
    }
}
