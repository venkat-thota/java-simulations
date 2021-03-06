
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.state;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;


public class ImageFactory {
    private BufferedImage image;
    private int count;
    private int numTimes = 10;//sim runs at 30fps, but we only generate an image every numTimes frames

    public BufferedImage getThumbnail( AimxcelFrame frame, int width ) {
        return BufferedImageUtils.multiScaleToWidth( toImage( frame ), width );
    }

    public BufferedImage toImage( JFrame frame ) {
        count++;
        if ( image == null || image.getWidth() != frame.getWidth() || image.getHeight() != frame.getHeight() ) {
            image = new BufferedImage( frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB );
            System.out.println( "new image created" );
        }
        //TODO: could be done in a different thread?
        if ( count % numTimes == 0 ) {
            Graphics2D g2 = image.createGraphics();
            frame.getContentPane().paint( g2 );
            g2.dispose();
//            image = sendFromRobot( frame );
        }
        return image;
    }

    //Generates screenshots from a Robot, sending the entire desktop.  We may or may not use this in production
    private BufferedImage sendFromRobot( JFrame frame ) {
        try {
            return new Robot().createScreenCapture( new Rectangle( frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight() ) );
        }
        catch ( AWTException e ) {
            e.printStackTrace();
            throw new RuntimeException( e );
        }
    }
}
