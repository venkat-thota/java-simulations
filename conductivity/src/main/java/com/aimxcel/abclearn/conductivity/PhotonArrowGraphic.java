
package com.aimxcel.abclearn.conductivity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import com.aimxcel.abclearn.conductivity.oldaimxcelgraphics.Graphic;
import com.aimxcel.abclearn.conductivity.oldaimxcelgraphics.ImageGraphic;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.AbstractVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.TransformListener;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;

public class PhotonArrowGraphic
        implements Graphic {

    public PhotonArrowGraphic( Photon photon1, ModelViewTransform2D modelviewtransform2d ) {
        photon = photon1;
        transform = modelviewtransform2d;
//        shapeGraphic = new ShapeGraphic( null, Color.red, Color.black, new BasicStroke( 1.0F, 2, 0 ) );
        try {
            shapeGraphic = new ImageGraphic( ImageLoader.loadBufferedImage( "conductivity/images/photon-comet-42.png" ) );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        modelviewtransform2d.addTransformListener( new TransformListener() {

            public void transformChanged( ModelViewTransform2D modelviewtransform2d1 ) {
                doUpdate();
            }

        } );
        photon1.addObserver( new SimpleObserver() {

            public void update() {
                doUpdate();
            }

        } );
        doUpdate();
    }

    private void doUpdate() {
        MutableVector2D position = new MutableVector2D( photon.getPosition() );
        AbstractVector2D velocity = photon.getVelocity();
        Point viewVelocity = transform.modelToViewDifferential( velocity.getX(), velocity.getY() );
        MutableVector2D viewVelocityVector = new MutableVector2D( viewVelocity );
        MutableVector2D positionViewVector = new MutableVector2D( transform.modelToView( position ) );
//        Vector2D.Double src = positionViewVector.getSubtractedInstance( viewVelocityVector.getInstanceForMagnitude( 25D ) );
//        ArrowShape arrowShape = new ArrowShape( src, positionViewVector, 10D, 10D, 3D );
        AffineTransform at = new AffineTransform();
        at.translate( positionViewVector.getX(), positionViewVector.getY() );
        double fudgeAngle = -Math.PI / 32;
        double theta = viewVelocityVector.getAngle() - Math.PI / 2 + fudgeAngle;
//        System.out.println( "theta = " + theta );
        at.rotate( -theta );
        shapeGraphic.setTransform( at );
//        shapeGraphic.setShape( arrowShape.getArrowPath() );
    }

    public void paint( Graphics2D graphics2d ) {
        shapeGraphic.paint( graphics2d );
    }

    Photon photon;
    ModelViewTransform2D transform;
    ImageGraphic shapeGraphic;

}
