
package edu.colorado.phet.theramp;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;



public class TheRampStrings {

    private static final AimxcelResources RESOURCES = new AimxcelResources( TheRampConstants.PROJECT_NAME );

  
    private TheRampStrings() {
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

}
