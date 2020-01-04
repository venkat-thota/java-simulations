package com.aimxcel.abclearn.common.aimxcelcommon.math;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.math.ImmutableRectangle2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;

public class ModelBounds extends Property<Option<ImmutableRectangle2D>> {
    public ModelBounds() {
        super( new Option.None<ImmutableRectangle2D>() );
    }

    public Point2D getClosestPoint( Point2D point ) {
        if ( get().isNone() ) {
            return point;
        }
        else {
            return get().get().getClosestPoint( point );
        }
    }

    //Check whether the specified point is within the defined region of this model bounds.  If the model bound is still not yet set, then the point is contained,
    //since all points should be legal before we have the bounds definition
    public boolean contains( Vector2D value ) {
        if ( get().isNone() ) {
            return true;//any point legal if no bounds defined yet
        }
        else {
            return get().get().contains( value.toPoint2D() );
        }
    }
}
