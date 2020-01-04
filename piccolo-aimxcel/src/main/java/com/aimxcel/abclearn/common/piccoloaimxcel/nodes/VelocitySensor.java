// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;

/**
 * Model component for the VelocitySensorNode, which has a position (for the hot-spot of the sensor) and a numerical readout.
 *
 * @author Sam Reid
 */
public class VelocitySensor extends PointSensor<Vector2D> {

    public VelocitySensor() {
        this( 0, 0 );
    }

    public VelocitySensor( double x, double y ) {
        super( x, y );
    }
}