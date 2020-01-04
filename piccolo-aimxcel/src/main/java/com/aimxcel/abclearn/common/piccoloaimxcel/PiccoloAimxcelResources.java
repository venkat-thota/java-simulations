
package com.aimxcel.abclearn.common.piccoloaimxcel;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;

/**
 * Singleton that provides convenient access to piccolo-phet's JAR resources.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class PiccoloAimxcelResources {

    /* singleton, not intended for instantiation */
    private PiccoloAimxcelResources() {
    }

    private static AimxcelResources INSTANCE = new AimxcelResources( "piccolo-phet" );

    public static AimxcelResources getInstance() {
        return INSTANCE;
    }

    public static BufferedImage getImage( String name ) {
        return INSTANCE.getImage( name );
    }
}
