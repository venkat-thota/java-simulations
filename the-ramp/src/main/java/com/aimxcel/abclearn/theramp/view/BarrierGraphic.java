package com.aimxcel.abclearn.theramp.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public abstract class BarrierGraphic extends PNode {
    private RampPanel rampPanel;
    private SurfaceGraphic surfaceGraphic;
    protected PImage imageGraphic;

    public BarrierGraphic( Component component, RampPanel rampPanel, SurfaceGraphic surfaceGraphic ) {
        super();
        this.rampPanel = rampPanel;
        this.surfaceGraphic = surfaceGraphic;
        try {
            imageGraphic = new PImage( ImageLoader.loadBufferedImage( "the-ramp/images/barrier2.jpg" ) );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        addChild( imageGraphic );
        surfaceGraphic.getSurface().addObserver( new SimpleObserver() {
            public void update() {
                BarrierGraphic.this.update();
            }
        } );
        update();
    }

    private AffineTransform createTransform( double position, double scaleX, double fracSize ) {
        return surfaceGraphic.createTransform( position, new Dimension( (int) ( imageGraphic.getImage().getWidth( null ) * scaleX ), (int) ( imageGraphic.getImage().getHeight( null ) * fracSize ) ) );
    }

    private void update() {
        AffineTransform transform = createTransform( getBarrierPosition(), 1, 1 );
        transform.concatenate( AffineTransform.getTranslateInstance( 0, getYOffset() ) );
        transform.concatenate( AffineTransform.getTranslateInstance( getOffsetX(), 0 ) );
        imageGraphic.setTransform( transform );
    }

    protected abstract int getOffsetX();

    protected abstract double getBarrierPosition();

    public RampPanel getRampPanel() {
        return rampPanel;
    }

    public SurfaceGraphic getRampGraphic() {
        return surfaceGraphic;
    }
}
