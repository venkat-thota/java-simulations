
package com.aimxcel.abclearn.core.aimxcelcore;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class PiccoloAimxcelResources {

    /* singleton, not intended for instantiation */
    private PiccoloAimxcelResources() {
    }

    private static AimxcelResources INSTANCE = new AimxcelResources( "piccolo-aimxcel" );

    public static AimxcelResources getInstance() {
        return INSTANCE;
    }

    public static BufferedImage getImage( String name ) {
        return INSTANCE.getImage( name );
    }
}
