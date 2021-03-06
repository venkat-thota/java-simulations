
package com.aimxcel.abclearn.glaciers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;
public class GlaciersResources {
    
    private static final AimxcelResources RESOURCES = new AimxcelResources( GlaciersConstants.PROJECT_NAME );
    
    /* not intended for instantiation */
    private GlaciersResources() {}
    
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
    
    public static final Icon getIcon( String name ) {
        return new ImageIcon( getImage( name ) );
    }
    
    public static final InputStream getResourceAsStream( String name ) {
        InputStream s = null;
        try {
            s = RESOURCES.getResourceAsStream( name );
        }
        catch ( IOException e ) {
            System.err.println( "failed to get InputStream for resource named " + name );
        }
        return s;
    }
    
    public static final String getCommonString( String name ) {
        return AimxcelCommonResources.getInstance().getLocalizedString( name );
    }
    
    public static final BufferedImage getCommonImage( String name ) {
        return AimxcelCommonResources.getInstance().getImage( name );
    }
}
