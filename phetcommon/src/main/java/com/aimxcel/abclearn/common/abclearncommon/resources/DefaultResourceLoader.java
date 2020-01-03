
package com.aimxcel.abclearn.common.abclearncommon.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.aimxcel.abclearn.common.abclearncommon.audio.AbcLearnAudioClip;
import com.aimxcel.abclearn.common.abclearncommon.view.util.ImageLoader;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbstractResourceLoader;

/**
 * DefaultResourceLoader is the default loader for JAR resources.
 */
/* package private */
public class DefaultResourceLoader extends AbstractResourceLoader {

    private static final BufferedImage NULL_IMAGE = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_RGB );

    /**
     * Gets the image having the specified resource name.
     *
     * @param resourceName
     * @return BufferedImage
     */
    public BufferedImage getImage( String resource ) {
        if ( resource == null || resource.length() == 0 ) {
            throw new IllegalArgumentException( "null or zero-length resource name" );
        }
        BufferedImage image = null;
        try {
            image = ImageLoader.loadBufferedImage( resource );
        }
        catch ( IOException e ) {
            e.printStackTrace();
            image = NULL_IMAGE;
        }
        return image;
    }

    /**
     * Gets the audio clip having the specified resource name.
     *
     * @param resourceName
     * @return AbcLearnAudioClip
     */
    public AbcLearnAudioClip getAudioClip( String resource ) {
        if ( resource == null || resource.length() == 0 ) {
            throw new IllegalArgumentException( "null or zero-length resource name" );
        }
        return new AbcLearnAudioClip( resource );
    }
}
