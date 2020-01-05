

package com.aimxcel.abclearn.magnetandcompass;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class MagnetAndCompassResources {
    
    private static final AimxcelResources RESOURCES = new AimxcelResources( MagnetAndCompassConstants.PROJECT_NAME );
    
    /* not intended for instantiation */
    private MagnetAndCompassResources() {}
    
    public static final AimxcelResources getResourceLoader() {
        return RESOURCES;
    }
    
    public static final String getString( String name ) {
        return RESOURCES.getLocalizedString( name  );
    }
    
    public static final char getChar( String name, char defaultValue ) {
        return RESOURCES.getLocalizedChar( name, defaultValue );
    }

    public static final int getInt( String name, int defaultValue ) {
        return RESOURCES.getLocalizedInt( name, defaultValue );
    }
    
    public static final BufferedImage getImage( String name ) {
        return RESOURCES.getImage( name );
    }
}
