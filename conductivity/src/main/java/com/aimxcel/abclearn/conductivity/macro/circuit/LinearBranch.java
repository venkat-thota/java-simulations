// Copyright 2002-2012, University of Colorado

package com.aimxcel.abclearn.conductivity.macro.circuit;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;

public class LinearBranch {

    public LinearBranch( MutableVector2D phetvector, MutableVector2D phetvector1 ) {
        start = phetvector;
        end = phetvector1;
        dv = phetvector1.minus( phetvector );
    }

    public double getLength() {
        return dv.magnitude();
    }

    public Vector2D getLocation( double d ) {
        return start.plus( dv.getInstanceOfMagnitude( d ) );
    }

    public MutableVector2D getStartPosition() {
        return start;
    }

    public MutableVector2D getEndPosition() {
        return end;
    }

    MutableVector2D start;
    MutableVector2D end;
    private Vector2D dv;
}
