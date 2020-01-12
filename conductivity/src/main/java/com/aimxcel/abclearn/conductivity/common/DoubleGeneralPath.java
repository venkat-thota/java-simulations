
package com.aimxcel.abclearn.conductivity.common;

import java.awt.geom.GeneralPath;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;

public class DoubleGeneralPath {

    public DoubleGeneralPath( MutableVector2D aimxcelvector ) {
        this( aimxcelvector.getX(), aimxcelvector.getY() );
    }

    public DoubleGeneralPath( double d, double d1 ) {
        path = new GeneralPath();
        path.moveTo( (float) d, (float) d1 );
    }

    public void lineTo( double d, double d1 ) {
        path.lineTo( (float) d, (float) d1 );
    }

    public GeneralPath getGeneralPath() {
        return path;
    }

    public void lineTo( MutableVector2D aimxcelvector ) {
        lineTo( aimxcelvector.getX(), aimxcelvector.getY() );
    }

    GeneralPath path;

    public void lineTo( Vector2D aimxcelvector2 ) {
        lineTo( aimxcelvector2.getX(), aimxcelvector2.getY() );
    }
}
