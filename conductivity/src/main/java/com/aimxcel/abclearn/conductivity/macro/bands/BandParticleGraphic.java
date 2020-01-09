// Copyright 2002-2012, University of Colorado

package com.aimxcel.abclearn.conductivity.macro.bands;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.conductivity.common.SimpleBufferedImageGraphic;
import com.aimxcel.abclearn.conductivity.common.TransformGraphic;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.TransformListener;

// Referenced classes of package edu.colorado.phet.semiconductor.macro.bands:
//            BandParticle

public class BandParticleGraphic extends TransformGraphic {

    public BandParticleGraphic( BandParticle bandparticle, ModelViewTransform2D modelviewtransform2d, BufferedImage bufferedimage ) {
        super( modelviewtransform2d );
        bandParticle = bandparticle;
        graphic = new SimpleBufferedImageGraphic( bufferedimage );
        modelviewtransform2d.addTransformListener( new TransformListener() {

            public void transformChanged( ModelViewTransform2D modelviewtransform2d1 ) {
                update();
            }

        } );
    }

    public void paint( Graphics2D graphics2d ) {
        MutableVector2D phetvector = bandParticle.getPosition();
        java.awt.Point point = getTransform().modelToView( phetvector );
        graphic.setPosition( point );
        graphic.paint( graphics2d );
    }

    public void update() {
    }

    BandParticle bandParticle;
    SimpleBufferedImageGraphic graphic;
}
