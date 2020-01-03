
package com.aimxcel.abclearn.common.abclearncommon.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import com.aimxcel.abclearn.common.abclearncommon.audio.AbcLearnAudioClip;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnProperties;

/**
 * IResourceLoader is the interface implemented by all resource loaders.
 */
/* package private */
public interface IResourceLoader {

    /**
     * Determines if a named resource exists.
     *
     * @param resourceName
     * @return true or false
     */
    public boolean exists( String resourceName );

    /**
     * Gets an input stream for the specified resource.
     *
     * @param resourceName
     * @return InputStream
     */
    public InputStream getResourceAsStream( String resourceName ) throws IOException;


    /**
     * Returns the contents of a resource as a String.
     *
     * @param resourceName
     * @return String
     */
    public String getResourceAsString( String resourceName ) throws IOException;

    /**
     * Gets a byte array for the specified resource.
     *
     * @param resourceName
     * @return byte[]
     */
    public byte[] getResource( String resourceName ) throws IOException;

    /**
     * Gets the image having the specified resource name.
     *
     * @param resourceName
     * @return BufferedImage
     */
    public BufferedImage getImage( String resourceName );

    /**
     * Gets the audio clip having the specified resource name.
     *
     * @param resourceName
     * @return AbcLearnAudioClip
     */
    public AbcLearnAudioClip getAudioClip( String resourceName );

    /**
     * Gets the properties file having the specified resource name and locale.
     *
     * @param resourceName
     * @param locale
     * @return AbcLearnProperties
     */
    public AbcLearnProperties getProperties( String resourceName, Locale locale );
}
