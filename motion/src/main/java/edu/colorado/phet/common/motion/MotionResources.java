
package edu.colorado.phet.common.motion;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnResources;

/**
 * Author: Sam Reid
 * May 23, 2007, 1:04:58 AM
 */
public class MotionResources {
    private static AbcLearnResources INSTANCE = new AbcLearnResources( "motion" );

    public static AbcLearnResources getInstance() {
        return INSTANCE;
    }

    public static BufferedImage loadBufferedImage( String url ) throws IOException {
        return INSTANCE.getImage( url );
    }
}
