package com.aimxcel.abclearn.games;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class GamesResources {

    private static final AimxcelResources RESOURCES = new AimxcelResources( "games" );

    /* not intended for instantiation */
    private GamesResources() {
    }

    /**
     * Convenience method for accessing an image file from games.
     *
     * @param name the name of the image
     * @return BufferedImage
     */
    public static BufferedImage getImage( String name ) {
        return RESOURCES.getImage( name );
    }
}
