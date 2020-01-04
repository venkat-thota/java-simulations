

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPNode;

import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.util.PDimension;


public class FineCrosshairNode extends AimxcelPNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor for a crosshair where horizontal and vertical dimensions are the same size.
     *
     * @param size
     * @param stroke
     * @param strokePaint
     */
    public FineCrosshairNode( double size, Stroke stroke, Paint strokePaint ) {
        this( new PDimension( size, size ), stroke, strokePaint );
    }

    /**
     * Constructor for a crosshair where horizontal and vertical dimensions can be different.
     *
     * @param size
     * @param stroke
     * @param strokePaint
     */
    public FineCrosshairNode( Dimension2D size, Stroke stroke, Paint strokePaint ) {
        super();
        setPickable( false );
        setChildrenPickable( false );

        PPath hPath = new PPath( new Line2D.Double( -size.getWidth() / 2, 0, size.getWidth() / 2, 0 ) );
        hPath.setStrokePaint( strokePaint );
        hPath.setStroke( stroke );
        addChild( hPath );

        PPath vPath = new PPath( new Line2D.Double( 0, -size.getHeight() / 2, 0, size.getHeight() / 2 ) );
        vPath.setStrokePaint( strokePaint );
        vPath.setStroke( stroke );
        addChild( vPath );
    }
}