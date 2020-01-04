
package com.aimxcel.abclearn.common.piccoloaimxcel.nodes;

import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.util.AimxcelUtilities;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.util.PPaintContext;

/**
 * AimxcelPPath provides convenient constructors for setting up a PPath.
 * <p/>
 * That is, you can do PPath=new AimxcelPPath(myShape,fillPaint,stroke,strokePaint);
 * instead of 4 lines of code (as supported by the piccolo API).
 *
 * @author Sam Reid
 * @revision $Revision$
 */

public class AimxcelPPath extends PPath {

    private static final boolean IS_MAC_OS_10_4 = AimxcelUtilities.isMacOS_10_4();

    public AimxcelPPath() {
    }

    public AimxcelPPath( Shape shape ) {
        super( shape );
    }

    /**
     * Creates a AimxcelPPath with the specified fill paint and no stroke.
     *
     * @param fill the paint for fill
     */
    public AimxcelPPath( Paint fill ) {
        setStroke( null );
        setPaint( fill );
    }

    /**
     * Constructs a AimxcelPPath with the specified stroke and stroke paint, but no fill paint.
     *
     * @param stroke
     * @param strokePaint
     */
    public AimxcelPPath( Stroke stroke, Paint strokePaint ) {
        setStroke( stroke );
        setStrokePaint( strokePaint );
    }

    /**
     * Constructs a AimxcelPPath with the specified shape and fill paint, with no stroke.
     *
     * @param shape
     * @param fill
     */
    public AimxcelPPath( Shape shape, Paint fill ) {

        //Set the stroke before setting the path so that it doesn't waste time computing the layout twice (once for the default stroke and once for the specified stroke)
        setStroke( null );
        setPathTo( shape );
        setPaint( fill );
    }

    /**
     * Constructs a AimxcelPPath with the specified shape, stroke and stroke paint, but no fill paint.
     *
     * @param shape
     * @param stroke
     * @param strokePaint
     */
    public AimxcelPPath( Shape shape, Stroke stroke, Paint strokePaint ) {

        //Set the stroke before setting the path so that it doesn't waste time computing the layout twice (once for the default stroke and once for the specified stroke)
        setStroke( stroke );
        if ( shape != null ) {
            setPathTo( shape );
        }
        setStrokePaint( strokePaint );
    }

    /**
     * Constructs a AimxcelPPath with the specified shape, fill paint, stroke and stroke paint.
     *
     * @param shape
     * @param fill
     * @param stroke
     * @param strokePaint
     */
    public AimxcelPPath( Shape shape, Paint fill, Stroke stroke, Paint strokePaint ) {

        //Set the stroke before setting the path so that it doesn't waste time computing the layout twice (once for the default stroke and once for the specified stroke)
        setPaint( fill );
        setStroke( stroke );
        if ( shape != null ) {
            setPathTo( shape );
        }
        setStrokePaint( strokePaint );
    }

    /**
     * Constructs a AimxcelPPath with the specified fill paint, stroke and stroke paint.
     *
     * @param fill
     * @param stroke
     * @param strokePaint
     */
    public AimxcelPPath( Paint fill, Stroke stroke, Paint strokePaint ) {
        setPaint( fill );
        setStroke( stroke );
        setStrokePaint( strokePaint );
    }

    /**
     * WORKAROUND for Gradient Paint bug on Mac OS 10.4.
     * With the default rendering value (VALUE_RENDER_QUALITY), gradient paints will crash.
     * Using VALUE_RENDER_SPEED avoids the problem.
     */
    protected void paint( PPaintContext paintContext ) {
        if ( IS_MAC_OS_10_4 ) {
            boolean usesGradient = ( ( getPaint() instanceof GradientPaint ) || ( getStrokePaint() instanceof GradientPaint ) );
            if ( usesGradient ) {
                Object saveValueRender = paintContext.getGraphics().getRenderingHint( RenderingHints.KEY_RENDERING );
                paintContext.getGraphics().setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED );
                super.paint( paintContext );
                if ( saveValueRender != null ) {
                    paintContext.getGraphics().setRenderingHint( RenderingHints.KEY_RENDERING, saveValueRender );
                }
            }
            else {
                super.paint( paintContext );
            }
        }
        else {
            super.paint( paintContext );
        }
    }

    public void centerFullBoundsOnPoint( Point2D point ) {
        super.centerFullBoundsOnPoint( point.getX(), point.getY() );
    }

    public double getCenterX() {
        return getFullBounds().getCenterX();
    }

    public double getCenterY() {
        return getFullBounds().getCenterY();
    }
}