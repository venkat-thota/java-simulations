
package com.aimxcel.abclearn.conductivity.macro.particles;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.conductivity.common.SimpleBufferedImageGraphic;
import com.aimxcel.abclearn.conductivity.common.TransformGraphic;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;

public class WireParticleGraphic extends TransformGraphic {

    public WireParticleGraphic( WireParticle wireparticle, ModelViewTransform2D modelviewtransform2d, BufferedImage bufferedimage ) {
        super( modelviewtransform2d );
        particle = wireparticle;
        imageGraphic = new SimpleBufferedImageGraphic( bufferedimage );
        wireparticle.addObserver( new SimpleObserver() {

            public void update() {
                WireParticleGraphic.this.update();
            }

        } );
    }

    public void update() {
    }

    public void paint( Graphics2D graphics2d ) {
        Vector2D aimxcelvector = particle.getPosition();
        java.awt.Point point = getTransform().modelToView( new Point2D.Double( aimxcelvector.getX(), aimxcelvector.getY() ) );
        imageGraphic.setPosition( point );
        imageGraphic.paint( graphics2d );
    }

    WireParticle particle;
    SimpleBufferedImageGraphic imageGraphic;
}
