
package com.aimxcel.abclearn.motion;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class MotionResources {
    private static AimxcelResources INSTANCE = new AimxcelResources( "motion" );

    public static AimxcelResources getInstance() {
        return INSTANCE;
    }

    public static BufferedImage loadBufferedImage( String url ) throws IOException {
        return INSTANCE.getImage( url );
    }
}
