package com.aimxcel.abclearn.conductivity.oldaimxcelgraphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class ImageGraphic implements Graphic {
    BufferedImage image;
    private AffineTransform transform = new AffineTransform();

    public ImageGraphic( BufferedImage image ) {
        this.image = image;
    }

    public void paint( Graphics2D g ) {
        g.drawRenderedImage( image, transform );
    }

    public void setTransform( AffineTransform at ) {
        this.transform = at;
    }
}
