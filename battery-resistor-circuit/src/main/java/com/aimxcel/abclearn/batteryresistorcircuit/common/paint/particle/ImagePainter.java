// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.batteryresistorcircuit.common.paint.particle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.DoublePoint;
import com.aimxcel.abclearn.batteryresistorcircuit.common.phys2d.Particle;

/*Centers the image.*/

public class ImagePainter implements ParticlePainter {
    BufferedImage im;

    public ImagePainter( BufferedImage im ) {
        this.im = im;
        if ( im == null ) {
            throw new RuntimeException( "ImagePainter created with null image." );
        }
    }

    public void paint( Particle p, Graphics2D g ) {
        DoublePoint dp = p.getPosition();
        if ( im == null ) {
            throw new RuntimeException( "Null painter." );
        }
        if ( dp == null ) {
            throw new RuntimeException( "Particle has null position." );
        }
        int x = (int) dp.getX() - im.getWidth() / 2;
        int y = (int) dp.getY() - im.getHeight() / 2;
        g.drawRenderedImage( im, AffineTransform.getTranslateInstance( x, y ) );
    }

}
