
 package com.aimxcel.abclearn.common.aimxcelcommon.view.util;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.CachingImageLoader;

public class ImageLoader {

    LoadStrategy loadStrategy;
    ConversionStrategy conversionStrategy;
    /**
     * A convenience instance, public for customization.
     */
    public static final ImageLoader instance = new CachingImageLoader();

    /**
     * Convenience method, uses the static instance to load a buffered image.
     */
    public static BufferedImage loadBufferedImage( String str ) throws IOException {
        return instance.loadImage( str );
    }

    public static BufferedImage loadBufferedImage( URL url ) throws IOException {
        return instance.loadImage( url );
    }

    public ImageLoader() {
        setAimxcelLoader();
        conversionStrategy = new ConversionStrategy() {
            public BufferedImage toBufferedImage( Image image ) {
                return BufferedImageUtils.toBufferedImage( image );
            }
        };
    }


    /**
     * Loads an image using the current load strategy and converts it to a bufferedimage
     * using the current conversion strategy.
     *
     * @param name
     * @return the loaded image.
     */
    public BufferedImage loadImage( String name ) throws IOException {
        // TODO: Sometimes there are problems with getClass().getClassLoader()
        // TODO  that are solved with Thread.currentThread().getContextClassLoader()
        ClassLoader cl = this.getClass().getClassLoader();
        URL imageUrl = cl.getResource( name );
        if ( imageUrl == null ) {
            throw new IOException( "Null image URL for resource name=" + name );
        }
        return loadImage( imageUrl );
    }

    public BufferedImage loadImage( URL imageURL ) throws IOException {
        if ( imageURL == null ) {
            throw new IOException( "Null image URL." );
        }
        Image image = loadStrategy.loadImage( imageURL );
        BufferedImage buffy = conversionStrategy.toBufferedImage( image );
        return buffy;
    }

    public void setImageIOLoader() {
        this.loadStrategy = new ImageIOLoader();
    }

    public void setAimxcelLoader() {
        this.loadStrategy = new AimxcelResourceLoader();
    }

    public void setToolkitLoader() {
        this.loadStrategy = new ToolkitLoader();
    }

    /**
     * So you can add your own strategy.
     */
    public void setLoadStrategy( LoadStrategy loadStrategy ) {
        this.loadStrategy = loadStrategy;
    }

    /**
     * So you can add your own strategy.
     */
    public void setConversionStrategy( ConversionStrategy conversionStrategy ) {
        this.conversionStrategy = conversionStrategy;
    }

    public void setIconLoader() {
        this.loadStrategy = new IconLoader();
    }

    static interface LoadStrategy {
        Image loadImage( URL location ) throws IOException;
    }

    static interface ConversionStrategy {
        BufferedImage toBufferedImage( Image image );
    }

    static class ImageIOLoader implements LoadStrategy {
        public Image loadImage( URL location ) throws IOException {
            return ImageIO.read( location );
        }
    }

    static class IconLoader implements LoadStrategy {
        public Image loadImage( URL location ) throws IOException {
            ImageIcon ic = new ImageIcon( location );
            return ic.getImage();
        }

    }

    static class ToolkitLoader implements LoadStrategy {
        public Image loadImage( URL location ) throws IOException {
            return Toolkit.getDefaultToolkit().getImage( location );
        }
    }

    static class AimxcelResourceLoader implements LoadStrategy {
        private static class ResourceLoader extends Container {
            public Image fetchImage( URL imageLocation ) throws IOException {
                Image image = null;
                try {
                    if ( imageLocation == null ) {
                        throw new IOException( "Image resource not found: Null imagelocation URL" );
                    }
                    else {
                        Toolkit toolkit = Toolkit.getDefaultToolkit();
                        image = toolkit.createImage( imageLocation );
                        MediaTracker tracker = new MediaTracker( this );
                        tracker.addImage( image, 0 );
                        tracker.waitForAll();
                    }
                }
                catch ( InterruptedException e ) {
                }
                return image;
            }
        }

        public Image loadImage( URL location ) throws IOException {
            if ( location == null ) {
                throw new IOException( "Null URL Location" );
            }
            Image im = null;
            try {
                ResourceLoader r = new ResourceLoader();
                im = r.fetchImage( location );
            }
            catch ( Throwable e ) {
                e.printStackTrace();
            }
            return im;

        }
    }
}