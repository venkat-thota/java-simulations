
package com.aimxcel.abclearn.conductivity.macro.bands.states;

import com.aimxcel.abclearn.conductivity.macro.bands.BandParticle;
import com.aimxcel.abclearn.conductivity.macro.bands.BandParticleState;
import com.aimxcel.abclearn.conductivity.macro.bands.EnergyCell;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;

public class MoveTo
        implements BandParticleState {

    public MoveTo( EnergyCell energycell, Speed speed1 ) {
        target = energycell;
        speed = speed1;
    }

    public BandParticleState stepInTime( BandParticle bandparticle, double d ) {
        double d1 = 0.0D;
        if ( bandparticle.getX() < bandparticle.getEnergyLevel().getLine().getX1() - d1 ) {
            bandparticle.setX( bandparticle.getEnergyLevel().getLine().getX2() + d1 );
        }
        double d2 = speed.getSpeed() * d;
        MutableVector2D aimxcelvector = target.getPosition();
        MutableVector2D aimxcelvector1 = bandparticle.getPosition();
        Vector2D aimxcelvector2 = aimxcelvector.minus( aimxcelvector1 );
        double d3 = aimxcelvector2.magnitude();
        if ( d3 <= d2 ) {
            bandparticle.setPosition( aimxcelvector );
            return new Waiting();
        }
        else {
            Vector2D aimxcelvector3 = aimxcelvector2.getInstanceOfMagnitude( d2 );
            aimxcelvector3 = new Vector2D( -Math.abs( aimxcelvector3.getX() ), aimxcelvector3.getY() );
            Vector2D aimxcelvector4 = aimxcelvector1.plus( aimxcelvector3 );
            bandparticle.setPosition( new MutableVector2D( aimxcelvector4.getX(), aimxcelvector4.getY() ) );
            return this;
        }
    }

    private EnergyCell target;
    Speed speed;
}
