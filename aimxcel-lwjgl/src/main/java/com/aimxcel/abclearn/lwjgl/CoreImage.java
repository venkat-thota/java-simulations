// Copyright 2002-2012, University of Colorado
package com.aimxcel.abclearn.lwjgl;

import java.awt.*;
import java.awt.geom.AffineTransform;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;

/**
 * Version of ComponentImage but optimized for Core, so that we can run things outside
 * the Swing EDT and not have to do multiple message passes to render.
 * <p/>
 * TODO: add (simple) mouse event handling, similar to ComponentImage. this will hopefully allow direct interaction with Core in the LWJGL thread
 */
public class CoreImage extends TextureImage {
    public final PNode node;

    public CoreImage( int width, int height, boolean hasAlpha, int magFilter, int minFilter, PNode node ) {
        super( width, height, hasAlpha, magFilter, minFilter );
        this.node = node;
    }

    public CoreImage( int width, int height, boolean hasAlpha, int magFilter, int minFilter, AffineTransform imageTransform, PNode node ) {
        super( width, height, hasAlpha, magFilter, minFilter, imageTransform );
        this.node = node;
    }

    @Override public void repaint() {
        refreshImage();
    }

    @Override public void paint( Graphics2D graphicsContext ) {
        // clear everything, since we may not be repainting
        graphicsContext.setBackground( new Color( 0, 0, 0, 0 ) );
        graphicsContext.clearRect( 0, 0, getWidth(), getHeight() );

        final PPaintContext paintContext = new PPaintContext( graphicsContext );
        paintContext.setRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
        node.fullPaint( paintContext );
    }
}
