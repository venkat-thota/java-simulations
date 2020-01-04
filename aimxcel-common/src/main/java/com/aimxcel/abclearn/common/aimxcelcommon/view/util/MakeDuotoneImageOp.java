
 package com.aimxcel.abclearn.common.aimxcelcommon.view.util;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ColorFilter;


public class MakeDuotoneImageOp implements BufferedImageOp, ColorFilter {
    private Color baseColor;

    public MakeDuotoneImageOp( Color baseColor ) {
        assert baseColor != null;

        this.baseColor = baseColor;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

    public Rectangle2D getBounds2D( BufferedImage src ) {
        return new Rectangle2D.Double( 0, 0, src.getWidth(), src.getHeight() );
    }

    public Point2D getPoint2D( Point2D srcPt, Point2D dstPt ) {
        if ( dstPt == null ) {
            dstPt = new Point2D.Double();
        }
        dstPt.setLocation( srcPt.getX(), srcPt.getY() );
        return dstPt;
    }

    public Color filter( Color rgb ) {
        double grayRefLevel = getGrayLevel( baseColor );

        return getDuoToneRGB( rgb, grayRefLevel, baseColor );
    }

    public BufferedImage filter( BufferedImage src, BufferedImage dest ) {
        if ( dest == null ) {
            dest = createCompatibleDestImage( src, src.getColorModel() );
        }
        ColorModel cm = src.getColorModel();
        double grayRefLevel = getGrayLevel( baseColor );
        for ( int x = 0; x < src.getWidth(); x++ ) {
            for ( int y = 0; y < src.getHeight(); y++ ) {
                int rgb = src.getRGB( x, y );
                int alpha = cm.getAlpha( rgb );
                int red = cm.getRed( rgb );
                int green = cm.getGreen( rgb );
                int blue = cm.getBlue( rgb );
                int newRGB = getDuoToneRGB( red, green, blue, alpha, grayRefLevel, baseColor );
                dest.setRGB( x, y, newRGB );
            }
        }
        return dest;
    }

    /**
     * Returns an Color value that is a duotone
     */
    public static Color getDuoToneRGB( Color input, double grayRefLevel, Color baseColor ) {
        double gray = ( input.getRed() + input.getGreen() + input.getBlue() ) / 3;

        int newRed = getComponent( gray, (double) baseColor.getRed(), grayRefLevel );
        int newGreen = getComponent( gray, (double) baseColor.getGreen(), grayRefLevel );
        int newBlue = getComponent( gray, (double) baseColor.getBlue(), grayRefLevel );

        return new Color( newRed, newGreen, newBlue, input.getAlpha() );
    }

    /**
     * Returns an RGB value that is a duotone
     *
     * @param grayRefLevel
     * @return the rgb value
     */
    public static int getDuoToneRGB( int red, int green, int blue, int alpha, double grayRefLevel, Color baseColor ) {
        double gray = ( red + green + blue ) / ( 3 );
        int newRed = getComponent( gray, (double) baseColor.getRed(), grayRefLevel );
        int newGreen = getComponent( gray, (double) baseColor.getGreen(), grayRefLevel );
        int newBlue = getComponent( gray, (double) baseColor.getBlue(), grayRefLevel );
        int newRGB = alpha * 0x01000000 + newRed * 0x00010000 + newGreen * 0x000000100 + newBlue * 0x00000001;
        return newRGB;
    }

    /**
     * Returns the relative "gray" level of an RGB value
     *
     * @param color
     * @return the gray value
     */
    public static double getGrayLevel( Color color ) {
        double grayRefLevel = ( color.getRed() + color.getGreen() + color.getBlue() ) / ( 255 * 3 );
        return grayRefLevel;
    }

    /**
     * Creates a new duotone image.
     *
     * @param src
     * @param destCM
     * @return the new image.
     */
    public BufferedImage createCompatibleDestImage( BufferedImage src, ColorModel destCM ) {
        return new AffineTransformOp( new AffineTransform(), AffineTransformOp.TYPE_NEAREST_NEIGHBOR ).createCompatibleDestImage( src, destCM );
    }

    /**
     * Does a piecewise linear interpolation to compute the component value
     *
     * @param grayLevel
     * @param componentRefLevel
     * @param grayRefLevel
     * @return
     */
    private static int getComponent( double grayLevel, double componentRefLevel, double grayRefLevel ) {
        int result = 0;

        // if the grayLevel is 255, we simply return 255
        if ( grayLevel == 255 ) {
            result = 255;
        }

        // if grayLevel is greater than grayRefLevel, do linear interpolation between (grayRefLevel,colorRefLevel)
        // and (255, 255 )
        if ( grayLevel >= grayRefLevel && grayLevel < 255 ) {
            double m = ( 255 - componentRefLevel ) / ( 255 - grayRefLevel );
            double c = componentRefLevel + ( grayLevel - grayRefLevel ) * m;
            result = (int) c;
        }

        // if grayLevel is less than grayRefLevel, do linear interpolation between (grayRefLevel,colorRefLevel)
        // and (0, 0 )
        if ( grayLevel <= grayRefLevel && grayLevel < 255 ) {
            double m = ( componentRefLevel ) / ( grayRefLevel );
            double c = ( grayLevel - grayRefLevel ) * m;
            result = (int) c;
            result = (int) ( ( grayLevel / grayRefLevel ) * componentRefLevel );
        }

        return result;
    }
}

