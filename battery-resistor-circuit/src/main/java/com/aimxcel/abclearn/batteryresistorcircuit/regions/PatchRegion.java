// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.batteryresistorcircuit.regions;

import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;
import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WirePatch;
import com.aimxcel.abclearn.batteryresistorcircuit.volt.WireRegion;

public class PatchRegion implements WireRegion {
    double min;
    double max;
    WirePatch patch;

    public PatchRegion( double min, double max, WirePatch patch ) {
        this.min = min;
        this.max = max;
        this.patch = patch;
    }

    public boolean contains( WireParticle wp ) {
        return wp.getWirePatch() == patch && max >= wp.getPosition() && min <= wp.getPosition();
    }
}
