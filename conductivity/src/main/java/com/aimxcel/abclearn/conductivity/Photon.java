
package com.aimxcel.abclearn.conductivity;


import com.aimxcel.abclearn.conductivity.common.Particle;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.AbstractVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;

public class Photon extends SimpleObservable
        implements ModelElement {

    public Photon() {
        particle = new Particle( 0.0D, 0.0D );
    }

    public void setPosition( double d, double d1 ) {
        particle.setPosition( d, d1 );
        notifyObservers();
    }

    public void setVelocity( Vector2D aimxcelvector ) {
        particle.setVelocity( aimxcelvector.getX(), aimxcelvector.getY() );
        notifyObservers();
    }

    public AbstractVector2D getPosition() {
        return particle.getPosition();
    }

    public void stepInTime( double d ) {
        particle.setAcceleration( 0.0D, 0.0D );
        particle.stepInTime( d );
        notifyObservers();
    }

    public void setPosition( MutableVector2D aimxcelvector ) {
        setPosition( aimxcelvector.getX(), aimxcelvector.getY() );
    }

    public AbstractVector2D getVelocity() {
        return particle.getVelocity();
    }

    public double getSpeed() {
        return getVelocity().magnitude();
    }

    Particle particle;
}
