

package com.aimxcel.abclearn.eatingandexercise;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class EatingAndExerciseResources {

    private static final AimxcelResources RESOURCES = new AimxcelResources( EatingAndExerciseConstants.PROJECT_NAME );

    /* not intended for instantiation */
    private EatingAndExerciseResources() {
    }

    public static final AimxcelResources getResourceLoader() {
        return RESOURCES;
    }

    public static final String getString( String name ) {
        return RESOURCES.getLocalizedString( name );
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

    public static final PImage getImageNode( String name ) {
        return new PImage( RESOURCES.getImage( name ) );
    }

    public static final String getCommonString( String name ) {
        return AimxcelCommonResources.getInstance().getLocalizedString( name );
    }

    public static final BufferedImage getCommonImage( String name ) {
        return AimxcelCommonResources.getInstance().getImage( name );
    }
}
