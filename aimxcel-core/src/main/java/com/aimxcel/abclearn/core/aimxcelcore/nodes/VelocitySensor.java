 
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;


public class VelocitySensor extends PointSensor<Vector2D> {

    public VelocitySensor() {
        this( 0, 0 );
    }

    public VelocitySensor( double x, double y ) {
        super( x, y );
    }
}