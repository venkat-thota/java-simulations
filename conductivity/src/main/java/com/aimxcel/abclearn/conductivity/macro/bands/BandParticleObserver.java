// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.conductivity.macro.bands;

// Referenced classes of package edu.colorado.phet.semiconductor.macro.bands:
//            BandParticle

public interface BandParticleObserver {

    public abstract void particleRemoved( BandParticle bandparticle );

    public abstract void particleAdded( BandParticle bandparticle );
}
