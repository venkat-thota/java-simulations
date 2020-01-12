
package com.aimxcel.abclearn.conductivity.macro.bands;

public interface BandParticleState {

    public abstract BandParticleState stepInTime( BandParticle bandparticle, double d );
}
