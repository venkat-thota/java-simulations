
package edu.colorado.phet.common.piccolophet;

import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnResources;

/**
 * Singleton that provides convenient access to piccolo-phet's JAR resources.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class PiccoloAbcLearnResources {

    /* singleton, not intended for instantiation */
    private PiccoloAbcLearnResources() {
    }

    private static AbcLearnResources INSTANCE = new AbcLearnResources( "piccolo-phet" );

    public static AbcLearnResources getInstance() {
        return INSTANCE;
    }

    public static BufferedImage getImage( String name ) {
        return INSTANCE.getImage( name );
    }
}
