
package com.aimxcel.abclearn.conductivity.common;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.conductivity.oldphetgraphics.Graphic;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;


public class StretchedBufferedImage
        implements Graphic {

    public StretchedBufferedImage( BufferedImage bufferedimage, Rectangle rectangle1 ) {
        battIm = bufferedimage;
        rectangle = rectangle1;
    }

    public void paint( Graphics2D graphics2d ) {
        java.awt.geom.Rectangle2D.Double double1 = new java.awt.geom.Rectangle2D.Double( 0.0D, 0.0D, battIm.getWidth(), battIm.getHeight() );
        ModelViewTransform2D modelviewtransform2d = new ModelViewTransform2D( double1, rectangle );
        java.awt.geom.AffineTransform affinetransform = modelviewtransform2d.getAffineTransform();
        graphics2d.drawRenderedImage( battIm, affinetransform );
    }

    private BufferedImage battIm;
    private Rectangle rectangle;

    public void setOutputRect( Rectangle rectangle ) {
        this.rectangle = rectangle;
    }
}
