package com.aimxcel.abclearn.batteryresistorcircuit.volt;

import com.aimxcel.abclearn.batteryresistorcircuit.Electron;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.Propagator1d;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;
public class ResetElectron implements Propagator1d {
    public ResetElectron() {
    }

    public void propagate( WireParticle wireParticle, double v ) {
        Electron e = (Electron) wireParticle;
        e.forgetCollision();
    }
}
