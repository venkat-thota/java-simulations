// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.batteryresistorcircuit.volt;

import com.aimxcel.abclearn.batteryresistorcircuit.Electron;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.Propagator1d;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;

public class Crash implements Propagator1d {
    public void propagate( WireParticle wp, double dt ) {
        Electron e = (Electron) wp;
        if ( e.isCollided() ) {
            e.setVelocity( 0 );
            e.setCollided( false );
        }
    }
}
