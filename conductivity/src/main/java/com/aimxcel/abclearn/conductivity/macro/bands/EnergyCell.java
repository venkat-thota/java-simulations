
package com.aimxcel.abclearn.conductivity.macro.bands;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;

public class EnergyCell {

    public EnergyCell( EnergyLevel energylevel, double d, double d1 ) {
        level = energylevel;
        x = d;
        y = d1;
    }

    public String toString() {
        return "Band=" + level.getBand() + ", level=" + level + ", index=" + level.indexOf( this );
    }

    public boolean hasOwner() {
        return owner != null;
    }

    public BandParticle getOwner() {
        return owner;
    }

    public void setOwner( BandParticle bandparticle ) {
        if ( owner == null || owner == bandparticle ) {
            owner = bandparticle;
        }
        else {
            throw new RuntimeException( "Wrong owner." );
        }
    }

    public void detach( BandParticle bandparticle ) {
        if ( owner == bandparticle ) {
            owner = null;
        }
        else {
            throw new RuntimeException( "Only owner can detach." );
        }
    }

    public MutableVector2D getPosition() {
        return new MutableVector2D( x, y );
    }

    public EnergyLevel getEnergyLevel() {
        return level;
    }

    public boolean isOccupied() {
        return owner != null;
    }

    double x;
    double y;
    BandParticle owner;
    EnergyLevel level;
}
