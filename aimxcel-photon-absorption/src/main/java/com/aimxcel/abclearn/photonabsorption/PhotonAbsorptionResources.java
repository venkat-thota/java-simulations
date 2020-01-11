// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.photonabsorption;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;



/**
 * @author Sam Reid
 */
public class PhotonAbsorptionResources {
    private static final AimxcelResources RESOURCES = new AimxcelResources( "photon-absorption" );

    public static BufferedImage getImage( String imageName ) {
        return RESOURCES.getImage( imageName );
    }
}
