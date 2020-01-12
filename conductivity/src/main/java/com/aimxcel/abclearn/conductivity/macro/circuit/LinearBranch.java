
package com.aimxcel.abclearn.conductivity.macro.circuit;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;

public class LinearBranch {

    public LinearBranch( MutableVector2D aimxcelvector, MutableVector2D aimxcelvector1 ) {
        start = aimxcelvector;
        end = aimxcelvector1;
        dv = aimxcelvector1.minus( aimxcelvector );
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
