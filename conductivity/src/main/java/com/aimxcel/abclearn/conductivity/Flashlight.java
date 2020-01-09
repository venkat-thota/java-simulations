// Copyright 2002-2012, University of Colorado

package com.aimxcel.abclearn.conductivity;


import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;

public class Flashlight extends SimpleObservable {

    public Flashlight( double d, double d1, double d2 ) {
        x = d;
        y = d1;
        angle = d2;
    }

    public double getAngle() {
        return angle;
    }

    public MutableVector2D getPosition() {
        return new MutableVector2D( x, y );
    }

    public void setAngle( double d ) {
        angle = d;
        notifyObservers();
    }

    double x;
    double y;
    double angle;
}
