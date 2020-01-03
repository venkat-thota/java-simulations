
package edu.colorado.phet.common.motion.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;

import edu.colorado.phet.common.piccolophet.nodes.AbcLearnPPath;
import edu.umd.cs.piccolo.util.PPaintContext;

/**
 * @author Sam Reid
 */
public class HighQualityAbcLearnPPath extends AbcLearnPPath {

    public HighQualityAbcLearnPPath( Shape shape, Color color ) {
        super( shape, color );
    }

    public HighQualityAbcLearnPPath( Color color, BasicStroke stroke, Color strokeColor ) {
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
