package com.aimxcel.abclearn.theramp.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.BufferedImageUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;
import com.aimxcel.abclearn.theramp.model.Surface;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;

public class RampGraphic extends SurfaceGraphic {
    private PNode arrowGraphic;

    public RampGraphic( RampPanel rampPanel, Surface ramp ) {
        super( rampPanel, ramp );
        arrowGraphic = createArrowGraphic();
        addChild( arrowGraphic );

        getSurfaceGraphic().addInputEventListener( new PBasicInputEventHandler() {
            public void mouseDragged( PInputEvent event ) {
                arrowGraphic.setVisible( false );
            }
        } );
        getSurface().addObserver( new SimpleObserver() {
            public void update() {
                arrowGraphic.setVisible( false );
            }
        } );

        updateArrowGraphic();
    }

    private void updateArrowGraphic() {
        Point pt = getViewLocation( getSurface().getLocation( getSurface().getLength() * 0.8 ) );
        arrowGraphic.setOffset( pt.x - arrowGraphic.getWidth() / 2, pt.y - arrowGraphic.getHeight() / 2 );
    }

    private PNode createArrowGraphic() {
        String imageResourceName = "the-ramp/images/arrow-2.gif";
        BufferedImage image = null;
        try {
            image = ImageLoader.loadBufferedImage( imageResourceName );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        image = BufferedImageUtils.rescaleYMaintainAspectRatio( image, 100 );
        PImage aimxcelImageGraphic = new PImage( image );
                aimxcelImageGraphic.setPickable( false );
        aimxcelImageGraphic.setChildrenPickable( false );
        return aimxcelImageGraphic;
    }
}