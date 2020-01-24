
package com.aimxcel.abclearn.core.aimxcelcore;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class CoreAimxcelResources {

    /* singleton, not intended for instantiation */
    private CoreAimxcelResources() {
    }

    private static AimxcelResources INSTANCE = new AimxcelResources( "aimxcel-core" );

    public static AimxcelResources getInstance() {
        return INSTANCE;
    }

    public static BufferedImage getImage( String name ) {
        return INSTANCE.getImage( name );
    }
}
