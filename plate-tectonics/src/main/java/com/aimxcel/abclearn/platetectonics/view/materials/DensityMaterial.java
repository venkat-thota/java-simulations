package com.aimxcel.abclearn.platetectonics.view.materials;

import java.awt.*;

import com.aimxcel.abclearn.platetectonics.model.CrustModel;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;

public class DensityMaterial implements EarthMaterial {
    private static final Color min = new Color( 255, 255, 255 );
    private static final Color max = new Color( 0, 0, 0 );

    public static Vector2F densityMap( float density ) {
        float minDensityToShow = 2500;
        float maxDensityToShow = 3500;
        float maxMaxDensityToShow = CrustModel.CENTER_DENSITY;

        float densityRatio = ( density - minDensityToShow ) / ( maxDensityToShow - minDensityToShow );
        float x;
        if ( density <= 3300 ) {
            x = 100f + ( 1f - densityRatio ) * 155f;
        }
        else {
            float start = 100f + ( 1f - ( 3300 - minDensityToShow ) / ( maxDensityToShow - minDensityToShow ) ) * 155f;
            float end = 50f;
            float ratio = ( density - 3300 ) / ( maxMaxDensityToShow - 3300 );
            x = start + ( end - start ) * ratio;
        }
        x = (float) MathUtil.clamp( 0.0, x / 220, 1.0 ); // clamp it in the normal range
        return new Vector2F( x, 0.5f );
    }

    public Color getColor( float density, float temperature, Vector2F position, float alpha ) {
        float value = densityMap( density ).x;
        return new Color( value, value, value, alpha );
    }

    public Color getMinColor() {
        return min;
    }

    public Color getMaxColor() {
        return max;
    }
}
