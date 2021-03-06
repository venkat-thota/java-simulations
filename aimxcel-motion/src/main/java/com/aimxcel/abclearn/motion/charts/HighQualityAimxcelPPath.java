
package com.aimxcel.abclearn.motion.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;


public class HighQualityAimxcelPPath extends AimxcelPPath {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HighQualityAimxcelPPath( Shape shape, Color color ) {
        super( shape, color );
    }

    public HighQualityAimxcelPPath( Color color, BasicStroke stroke, Color strokeColor ) {
        super( color, stroke, strokeColor );
    }

    /**
     * Use high quality rendering hints for painting this node.
     *
     * @param paintContext
     */
    protected void paint( PPaintContext paintContext ) {
        int rq = paintContext.getRenderQuality();
        paintContext.setRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
        super.paint( paintContext );
        paintContext.setRenderQuality( rq );
    }
}
