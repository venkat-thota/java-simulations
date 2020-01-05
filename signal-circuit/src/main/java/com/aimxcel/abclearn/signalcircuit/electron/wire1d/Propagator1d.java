// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.signalcircuit.electron.wire1d;

public interface Propagator1d {
    public void propagate( WireParticle wp, double dt );
}
