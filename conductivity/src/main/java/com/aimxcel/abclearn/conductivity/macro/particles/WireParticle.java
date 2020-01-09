// Copyright 2002-2012, University of Colorado

package com.aimxcel.abclearn.conductivity.macro.particles;


import com.aimxcel.abclearn.conductivity.macro.circuit.Circuit;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;

public class WireParticle extends SimpleObservable
        implements ModelElement {

    public WireParticle( double d, Circuit circuit1 ) {
        speed = 0.0D;
        dist = d;
        circuit = circuit1;
    }

    public void setPosition( double d ) {
        dist = d;
        notifyObservers();
    }

    public Vector2D getPosition() {
        return circuit.getPosition( dist );
    }

    public void stepInTime( double d ) {
        double d1 = dist + speed * d;
        if ( circuit.contains( d1 ) ) {
            setPosition( d1 );
        }
        else {
            double d2 = circuit.getLength();
            for (; d1 < 0.0D; d1 += d2 ) {
                ;
            }
            for (; d1 > d2; d1 -= d2 ) {
                ;
            }
            setPosition( d1 );
        }
    }

    public void setSpeed( double d ) {
        speed = d;
    }

    double dist;
    Circuit circuit;
    private double speed;
}
