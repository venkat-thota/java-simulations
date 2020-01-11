// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.platetectonics.model.labels;

import com.aimxcel.abclearn.platetectonics.model.PlateMotionPlate;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;

/**
 * Represents a labeled area that has a top and bottom.
 */
public class RangeLabel extends PlateTectonicsLabel {
    public final Property<Vector3F> top;
    public final Property<Vector3F> bottom;
    public final String label;
    public final PlateMotionPlate plate;

    private boolean limitToScreen = false;

    public RangeLabel( Property<Vector3F> top, Property<Vector3F> bottom, String label, PlateMotionPlate plate ) {
        this.top = top;
        this.bottom = bottom;
        this.label = label;
        this.plate = plate;
    }

    public boolean isLimitToScreen() {
        return limitToScreen;
    }
}
