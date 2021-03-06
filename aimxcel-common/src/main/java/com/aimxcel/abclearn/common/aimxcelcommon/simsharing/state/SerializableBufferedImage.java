
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.state;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.aimxcel.abclearn.common.aimxcelcommon.util.IProguardKeepClass;

public class SerializableBufferedImage implements IProguardKeepClass {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final byte[] byteArray;

    public SerializableBufferedImage( BufferedImage bufferedImage ) {
        this( bufferedImage, "JPG" );
    }

    public SerializableBufferedImage( BufferedImage bufferedImage, String format ) {
        this.byteArray = toByteArray( bufferedImage, format );
    }

    public BufferedImage toBufferedImage() {
        return fromByteArray( byteArray );
    }

    public static BufferedImage fromByteArray( byte[] byteArray ) {
        try {
            return ImageIO.read( new ByteArrayInputStream( byteArray ) );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    private static byte[] toByteArray( final BufferedImage bufferedImage, final String format ) {
        try {
            return new ByteArrayOutputStream() {{ ImageIO.write( bufferedImage, format, this ); }}.toByteArray();
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    @Override public String toString() {
        return "SerializableBufferedImage{" +
               "byteArray=" + Arrays.toString( byteArray ) +
               '}';
    }
}