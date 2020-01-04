
package com.aimxcel.abclearn.core.aimxcelcore.nodes.background;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform;


public class GroundNode extends GradientBackgroundNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GroundNode( ModelViewTransform mvt, Rectangle2D modelRect, double modelGradientDepth ) {
        this( mvt, modelRect, modelGradientDepth, new Color( 144, 199, 86 ), new Color( 103, 162, 87 ) );
    }

    public GroundNode( ModelViewTransform mvt, Rectangle2D modelRect, double modelGradientDepth, Color topColor, Color bottomColor ) {
        super( mvt, modelRect, topColor, bottomColor, 0, -modelGradientDepth );
    }
}