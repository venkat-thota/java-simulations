
package com.aimxcel.abclearn.conductivity.common;


import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.AbstractVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;

public class Particle extends SimpleObservable
        implements ModelElement {

    public Particle( double d, double d1 ) {
        position = new MutableVector2D( d, d1 );
        velocity = new MutableVector2D();
        acceleration = new MutableVector2D();
    }

    public AbstractVector2D getPosition() {
        return position;
    }

    public AbstractVector2D getVelocity() {
        return velocity;
    }

    public void stepInTime( double d ) {
        Vector2D aimxcelvector = acceleration.times( d );
        velocity = velocity.plus( aimxcelvector );
        Vector2D aimxcelvector1 = velocity.times( d );
        position = position.plus( aimxcelvector1 );
        notifyObservers();
    }

    public void setAcceleration( double d, double d1 ) {
        acceleration = new MutableVector2D( d, d1 );
    }

    public void setVelocity( double d, double d1 ) {
        velocity = new MutableVector2D( d, d1 );
    }

    public void setPosition( double d, double d1 ) {
        position = new MutableVector2D( d, d1 );
    }

    AbstractVector2D position;
    AbstractVector2D velocity;
    MutableVector2D acceleration;
}
