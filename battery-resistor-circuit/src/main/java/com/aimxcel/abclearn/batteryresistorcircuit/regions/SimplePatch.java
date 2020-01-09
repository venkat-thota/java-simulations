// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.batteryresistorcircuit.regions;

import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WirePatch;
import com.aimxcel.abclearn.batteryresistorcircuit.volt.WireRegion;

public class SimplePatch implements WireRegion {
    WirePatch patch;

    public SimplePatch( WirePatch patch ) {
        this.patch = patch;
    }

    public boolean contains( WireParticle wp ) {
        return wp.getWirePatch() == patch;
    }
}
