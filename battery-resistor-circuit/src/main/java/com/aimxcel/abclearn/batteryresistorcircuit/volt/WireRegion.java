// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.batteryresistorcircuit.volt;

import com.aimxcel.abclearn.batteryresistorcircuit.common.wire1d.WireParticle;

public interface WireRegion {
    public boolean contains( WireParticle wp );
}
