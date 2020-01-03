

package edu.colorado.phet.eatingandexercise;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnResources;

import edu.umd.cs.piccolo.nodes.PImage;

/**
 * TemplateResources is a wrapper around the PhET resource loader.
 * If we decide to use a different technique to load resources in the
 * future, all changes will be encapsulated here.
 */
public class EatingAndExerciseResources {

    private static final AbcLearnResources RESOURCES = new AbcLearnResources( EatingAndExerciseConstants.PROJECT_NAME );

    /* not intended for instantiation */
    private EatingAndExerciseResources() {
    }

    public static final AbcLearnResources getResourceLoader() {
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
        return AbcLearnCommonResources.getInstance().getLocalizedString( name );
    }

    public static final BufferedImage getCommonImage( String name ) {
        return AbcLearnCommonResources.getInstance().getImage( name );
    }
}
