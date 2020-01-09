package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import fj.Effect;
import fj.F;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;

public class KnotNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final F<KnotNode, Boolean> _free = new F<KnotNode, Boolean>() {
        @Override public Boolean f( final KnotNode knotNode ) {
            return knotNode.pullerNode == null;
        }
    };
    private PullerNode pullerNode;
    private final AimxcelPPath path;
    private final Color color;
    public static final Effect<KnotNode> _removeHighlight = new Effect<KnotNode>() {
        @Override public void e( final KnotNode knotNode ) {
            knotNode.setHighlighted( false );
        }
    };
    public static final F<KnotNode, Double> _force = new F<KnotNode, Double>() {
        @Override public Double f( final KnotNode knotNode ) {
            return knotNode.pullerNode == null ? 0 : knotNode.pullerNode.getForce();
        }
    };

    public KnotNode( final Double knotLocation, final Rectangle2D ropeBounds ) {
        this.color = PullerNode.TRANSPARENT;
        double w = 30;
        path = new AimxcelPPath( new Ellipse2D.Double( -w / 2, -w / 2, w, w ), new BasicStroke( 2 ), PullerNode.TRANSPARENT ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( ropeBounds.getX() + knotLocation + 1, ropeBounds.getCenterY() - getFullBounds().getHeight() / 2 + 17 );
        }};
        addChild( path );
    }

    public void setHighlighted( final boolean highlighted ) { path.setStrokePaint( highlighted ? Color.yellow : color ); }

    public void setPullerNode( final PullerNode pullerNode ) { this.pullerNode = pullerNode; }

    public PullerNode getPullerNode() { return pullerNode; }
}