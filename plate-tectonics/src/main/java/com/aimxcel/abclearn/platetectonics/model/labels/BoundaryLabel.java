package com.aimxcel.abclearn.platetectonics.model.labels;

import com.aimxcel.abclearn.platetectonics.model.regions.Boundary;
import com.aimxcel.abclearn.platetectonics.util.Side;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;

/**
 * Represents a labeled boundary that will be displayed as a dotted line
 */
public class BoundaryLabel extends PlateTectonicsLabel {
    public final Boundary boundary;
    public final Side side;

    public final Property<Float> minX = new Property<Float>( Float.NEGATIVE_INFINITY );
    public final Property<Float> maxX = new Property<Float>( Float.POSITIVE_INFINITY );

    public BoundaryLabel( Boundary boundary, Side side ) {
        this.boundary = boundary;
        this.side = side;
    }

    public boolean isReversed() {
        return false;
    }

    public Boundary getBoundary() {
        return boundary;
    }
}
