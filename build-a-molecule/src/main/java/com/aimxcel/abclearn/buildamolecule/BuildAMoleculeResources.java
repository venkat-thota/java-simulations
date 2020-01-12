
package com.aimxcel.abclearn.buildamolecule;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class BuildAMoleculeResources {

    private static final AimxcelResources RESOURCES = new AimxcelResources( BuildAMoleculeConstants.PROJECT_NAME );

    /* not intended for instantiation */
    private BuildAMoleculeResources() {
    }

    public static AimxcelResources getResourceLoader() {
        return RESOURCES;
    }

    public static String getString( String name ) {
        return RESOURCES.getLocalizedString( name );
    }

    public static char getChar( String name, char defaultValue ) {
        return RESOURCES.getLocalizedChar( name, defaultValue );
    }

    public static int getInt( String name, int defaultValue ) {
        return RESOURCES.getLocalizedInt( name, defaultValue );
    }

    public static BufferedImage getImage( String name ) {
        return RESOURCES.getImage( name );
    }

    public static PImage getImageNode( String name ) {
        return new PImage( RESOURCES.getImage( name ) );
    }

    public static String getCommonString( String name ) {
        return AimxcelCommonResources.getInstance().getLocalizedString( name );
    }

    public static BufferedImage getCommonImage( String name ) {
        return AimxcelCommonResources.getInstance().getImage( name );
    }
}
