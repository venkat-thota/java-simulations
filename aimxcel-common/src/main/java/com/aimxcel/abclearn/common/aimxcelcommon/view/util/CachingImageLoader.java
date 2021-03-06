
 package com.aimxcel.abclearn.common.aimxcelcommon.view.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;


public class CachingImageLoader extends ImageLoader {
    Hashtable buffer = new Hashtable();

    public BufferedImage loadImage( String image ) throws IOException {
        if ( buffer.containsKey( image ) ) {
            return (BufferedImage) buffer.get( image );
        }
        else {
            BufferedImage imageLoad = super.loadImage( image );
            buffer.put( image, imageLoad );
            return imageLoad;
        }
    }
}
