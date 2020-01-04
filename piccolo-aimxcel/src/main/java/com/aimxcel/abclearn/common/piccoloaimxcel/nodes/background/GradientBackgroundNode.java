
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes.background;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform;
import com.aimxcel.abclearn.common.piccoloaimxcel.nodes.AimxcelPPath;

import edu.umd.cs.piccolo.PNode;

/**
 * Base class for nodes that are used as the background on a tab and that have
 * some sort of gradient to it.  Example include ground and sky.
 *
 * @author John Blanco
 * @author Sam Reid
 */
public class GradientBackgroundNode extends PNode {
    public GradientBackgroundNode( ModelViewTransform mvt, Rectangle2D modelRect, Color color1, Color color2, double y1, double y2 ) {
        Shape viewShape = mvt.modelToView( modelRect );
        float centerX = (float) viewShape.getBounds2D().getCenterX();
        GradientPaint gradientPaint = new GradientPaint( centerX,
                                                         (float) mvt.modelToViewY( y1 ),
                                                         color1,
                                                         centerX,
                                                         (float) mvt.modelToViewY( y2 ),
                                                         color2 );
        AimxcelPPath path = new AimxcelPPath( viewShape, gradientPaint );
        addChild( path );
    }
}
