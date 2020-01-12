
package com.aimxcel.abclearn.conductivity.common;

import com.aimxcel.abclearn.conductivity.oldaimxcelgraphics.Graphic;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.TransformListener;


public abstract class TransformGraphic
        implements Graphic {

    public TransformGraphic( ModelViewTransform2D modelviewtransform2d ) {
        transform = modelviewtransform2d;
        modelviewtransform2d.addTransformListener( new TransformListener() {

            public void transformChanged( ModelViewTransform2D modelviewtransform2d1 ) {
                update();
            }

        } );
    }

    public ModelViewTransform2D getTransform() {
        return transform;
    }

    public abstract void update();

    ModelViewTransform2D transform;
}
