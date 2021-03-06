
package com.aimxcel.abclearn.signalcircuit;

import com.aimxcel.abclearn.signalcircuit.electron.wire1d.Propagator1d;
import com.aimxcel.abclearn.signalcircuit.electron.wire1d.WireParticle;

public class Friction implements Propagator1d {
    double scale;

    public Friction( double scale ) {
        this.scale = scale;
    }

    public void propagate( WireParticle wp, double dt ) {
        double v = wp.getVelocity();
        double slow = v * scale;
        wp.setVelocity( slow );
    }
}
