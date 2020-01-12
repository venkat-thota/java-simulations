package com.aimxcel.abclearn.mechanics;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;

public class DefaultBody extends Body {

    protected DefaultBody() {
        super();
    }

    protected DefaultBody( Point2D location, MutableVector2D velocity, MutableVector2D acceleration, double mass, double charge ) {
        super( location, velocity, acceleration, mass, charge );
    }

    public Point2D getCM() {
        return null;
    }

    public double getMomentOfInertia() {
        return 0;
    }
}
