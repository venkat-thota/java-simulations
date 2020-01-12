package com.aimxcel.abclearn.platetectonics.view.materials;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;

public class CombinedMaterial implements EarthMaterial {
    private static final Color min = new Color( 64, 64, 64 );
    private static final Color max = new Color( 255, 64, 64 );

    public Color getColor( float density, float temperature, Vector2F position, float alpha ) {
        float tempValue = TemperatureMaterial.temperatureMap( temperature ).x;
        float densityValue = DensityMaterial.densityMap( density ).x;

        // HSV
        float sat = tempValue;
        float value = densityValue / 2 + 0.5f;

        float c = value * sat;

        float m = value - c;

        return new Color( c + m, m, m, alpha );
    }

    public Color getMinColor() {
        return min;
    }

    public Color getMaxColor() {
        return max;
    }
}
