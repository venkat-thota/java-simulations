
package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.awt.Color;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ColorUtils;


public class ColorRange {

    private final Color min, max;

    public ColorRange( Color min, Color max ) {
        this.min = min;
        this.max = max;
    }

    public Color getMin() {
        return min;
    }

    public Color getMax() {
        return max;
    }

    /**
     * Performs a linear interpolation between min and max colors in RGBA colorspace.
     *
     * @param distance 0-1 (0=min, 1=max)
     * @return
     */
    public Color interpolateLinear( double distance ) {
        if ( distance < 0 || distance > 1 ) {
            throw new IllegalArgumentException( "distance out of range: " + distance );
        }
        return ColorUtils.interpolateRBGA( min, max, distance );
    }
}
