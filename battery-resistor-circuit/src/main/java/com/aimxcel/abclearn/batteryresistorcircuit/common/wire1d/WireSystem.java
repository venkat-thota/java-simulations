package com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d;

import java.util.Vector;

import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Law;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.System2D;

public class WireSystem implements Law {
    Vector particles;

    public WireSystem() {
        particles = new Vector();
    }

    public void add( WireParticle wp ) {
        particles.add( wp );
    }

    public void iterate( double dt, System2D sys ) {
        for ( int i = 0; i < particles.size(); i++ ) {
            //o.O.p("i="+i);
            ( (WireParticle) particles.get( i ) ).propagate( dt );
        }
    }

    public int numParticles() {
        return particles.size();
    }

    public WireParticle particleAt( int i ) {
        return (WireParticle) particles.get( i );
    }
}
