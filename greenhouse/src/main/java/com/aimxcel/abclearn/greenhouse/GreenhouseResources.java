// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.greenhouse;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class GreenhouseResources {

    private static final AimxcelResources RESOURCES = new AimxcelResources( GreenhouseConfig.PROJECT_NAME );

    /* not intended for instantiation */
    private GreenhouseResources() {
    }

    public static final AimxcelResources getResourceLoader() {
        return RESOURCES;
    }

    public static final String getString( String name ) {
        return RESOURCES.getLocalizedString( name );
    }

    public static final BufferedImage getImage( String name ) {
        return RESOURCES.getImage( name );
    }

    public static final String getCommonString( String name ) {
        return AimxcelCommonResources.getInstance().getLocalizedString( name );
    }

    public static final BufferedImage getCommonImage( String name ) {
        return AimxcelCommonResources.getInstance().getImage( name );
    }
    
    public static final int getInt( String name, int defaultValue ) {
        return RESOURCES.getLocalizedInt( name, defaultValue );
    }
}
