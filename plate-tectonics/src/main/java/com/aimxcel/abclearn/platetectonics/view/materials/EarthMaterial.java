// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.platetectonics.view.materials;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;

/**
 * Base for a material (texture) for the cross-section of the Earth.
 */
public interface EarthMaterial {
    // main color based on density, temperature, etc.
    public Color getColor( float density, float temperature, Vector2F position, float alpha );

    // min/max colors used for the legend (either minimum or maximum displayed values for temperature or density)
    public Color getMinColor();

    public Color getMaxColor();
}
