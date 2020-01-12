package com.aimxcel.abclearn.batteryresistorcircuit.collisions;

import com.aimxcel.abclearn.batteryresistorcircuit.Electron;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Law;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Particle;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.System2D;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WirePatch;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireSystem;
import com.aimxcel.abclearn.batteryresistorcircuit.oscillator2d.Core;

public class Collider implements Law {
    WireSystem ws;
    CollisionEvent ce;
    WirePatch patch;

    public Collider( WireSystem ws, CollisionEvent ce, WirePatch patch ) {
        this.patch = patch;
        this.ws = ws;
        this.ce = ce;
    }

    public void iterate( double dt, System2D sys ) {
        for ( int i = 0; i < sys.numParticles(); i++ ) {
            Particle wp = sys.particleAt( i );
            if ( wp instanceof Core ) {
                Core c = (Core) wp;
                for ( int k = 0; k < ws.numParticles(); k++ ) {
                    //System.err.println("Colliding.");
                    Electron other = (Electron) ws.particleAt( k );
                    if ( other.getWirePatch() == patch ) {
                        ce.collide( c, other );
                    }
                }
            }
        }
    }
}
