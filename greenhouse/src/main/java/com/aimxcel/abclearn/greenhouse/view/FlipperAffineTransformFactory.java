package com.aimxcel.abclearn.greenhouse.view;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.greenhouse.common.graphics.AffineTransformFactory;


public class FlipperAffineTransformFactory implements AffineTransformFactory {
    private Rectangle2D modelBounds;

    public FlipperAffineTransformFactory( Rectangle2D modelBounds ) {
        this.modelBounds = modelBounds;
    }

    public void setModelBounds( Rectangle2D modelBounds ) {
        this.modelBounds = modelBounds;
    }

    /**
     * Returns an affine transform that will transform from model to
     * view coordinates, and invert the y axis.
     *
     * @param viewBounds
     * @return
     */
    public AffineTransform getTx( Rectangle viewBounds ) {
        AffineTransform aTx = new AffineTransform();
        aTx.translate( viewBounds.getMinX(), viewBounds.getMinY() );
        aTx.scale( viewBounds.getWidth() / modelBounds.getWidth(), viewBounds.getHeight() / -modelBounds.getHeight() );
        aTx.translate( -modelBounds.getMinX(), -modelBounds.getMaxY() );
        return aTx;
    }
}
