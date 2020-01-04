 
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.geom.Dimension2D;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;


public class PointSensor<T> {
    public final Property<Vector2D> position;
    public final Property<Option<T>> value = new Property<Option<T>>( new Option.None<T>() );

    public PointSensor( double x, double y ) {
        position = new Property<Vector2D>( new Vector2D( x, y ) );
    }

    public void translate( Dimension2D delta ) {
        position.set( position.get().plus( delta.getWidth(), delta.getHeight() ) );
    }

    public void reset() {
        position.reset();
        value.reset();
    }
}
