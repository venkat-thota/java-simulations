

package com.aimxcel.abclearn.common.abclearncommon.view.util;

import java.awt.Color;

import com.aimxcel.abclearn.common.abclearncommon.view.util.ColorFilter;

public interface ColorFilter {
    public static final ColorFilter NULL = new ColorFilter() {
        public Color filter( Color in ) {
            return in;
        }
    };

    Color filter( Color in );
}
