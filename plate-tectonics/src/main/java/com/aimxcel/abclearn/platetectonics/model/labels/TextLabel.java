package com.aimxcel.abclearn.platetectonics.model.labels;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;

public class TextLabel extends PlateTectonicsLabel {
    public final Property<Vector3F> centerPosition;
    public final String label;

    public TextLabel( Property<Vector3F> centerPosition, String label ) {
        this.centerPosition = centerPosition;
        this.label = label;
    }
}
