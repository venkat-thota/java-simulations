
package com.aimxcel.abclearn.core.aimxcelcore.nodes.background;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class GradientBackgroundNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
