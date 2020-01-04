
package edu.colorado.phet.common.phetgraphics.view.phetgraphics;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;


public class AimxcelImageGraphic extends AimxcelGraphic {
    private BufferedImage image;
    private boolean shapeDirty = true;
    private Shape shape;
    private String imageResourceName;

    public AimxcelImageGraphic( Component component ) {
        this( component, null, 0, 0 );
    }

    /**
     * @param component
     * @param imageResourceName
     * @deprecated use one of the constructors that takes a BufferedImage, sims should load the image using AimxcelResourceLoader to keep the image pathname transparent
     */
    public AimxcelImageGraphic( Component component, String imageResourceName ) {
        this( component, (BufferedImage) null );
        this.imageResourceName = imageResourceName;

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageLoader.loadBufferedImage( imageResourceName );
        }
        catch ( IOException e ) {
            throw new RuntimeException( "Image resource not found: " + imageResourceName );
        }
        setImage( bufferedImage );
    }

    public AimxcelImageGraphic( Component component, BufferedImage image ) {
        this( component, image, 0, 0 );
    }

    public AimxcelImageGraphic( Component component, BufferedImage image, int x, int y ) {
        super( component );
        this.image = image;
        setLocation( x, y );
    }

    public Shape getShape() {
        AffineTransform transform = getNetTransform();
        if ( shapeDirty ) {
            if ( image == null ) {
                return null;
            }
            Rectangle rect = new Rectangle( 0, 0, image.getWidth(), image.getHeight() );
            this.shape = transform.createTransformedShape( rect );
            shapeDirty = false;
        }
        return shape;
    }

    public boolean contains( int x, int y ) {
        return isVisible() && getShape() != null && getShape().contains( x, y );
    }

    protected Rectangle determineBounds() {
        return getShape() == null ? null : getShape().getBounds();
    }

    public void paint( Graphics2D g2 ) {
        if ( isVisible() && image != null ) {
            super.saveGraphicsState( g2 );
            super.updateGraphicsState( g2 );
            try {
                g2.drawRenderedImage( image, getNetTransform() );
            }
            catch ( RuntimeException paintException ) {
                paintException.printStackTrace();
            }
            super.restoreGraphicsState();
        }
    }

    public void setBoundsDirty() {
        super.setBoundsDirty();
        shapeDirty = true;
    }

    public void setImage( BufferedImage image ) {
        if ( this.image != image ) {
            this.image = image;
            setBoundsDirty();
            autorepaint();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    ///////////////////////////////////////////////////
    // Persistence support
    //

    public AimxcelImageGraphic() {
        // noop
    }

    public String getImageResourceName() {
        return imageResourceName;
    }

    public void setImageResourceName( String imageResourceName ) {
        this.imageResourceName = imageResourceName;
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageLoader.loadBufferedImage( imageResourceName );
        }
        catch ( IOException e ) {
            throw new RuntimeException( "Image resource not found: " + imageResourceName );
        }
        setImage( bufferedImage );
    }
}
