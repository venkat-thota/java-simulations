// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.batteryresistorcircuit.volt;

import com.aimxcel.abclearn.batteryresistorcircuit.Electron;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.Propagator1d;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;

/**
 * Created by IntelliJ IDEA.
 * User: Sam Reid
 * Date: Jan 24, 2003
 * Time: 8:44:20 PM
 * To change this template use Options | File Templates.
 */
public class ResetElectron implements Propagator1d {
    public ResetElectron() {
    }

    public void propagate( WireParticle wireParticle, double v ) {
        Electron e = (Electron) wireParticle;
        e.forgetCollision();
    }
}
