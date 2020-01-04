
package com.aimxcel.abclearn.core.aimxcelcore.nodes.kit;

import java.awt.Rectangle;

import com.aimxcel.abclearn.core.aimxcelcore.RichPNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolox.PFrame;


public class ZeroOffsetNode extends RichPNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZeroOffsetNode( PNode node ) {
        addChild( node );

        zeroNodeOffset( node );
    }

    public static void zeroNodeOffset( PNode node ) {
        // The following line makes sure that the bounds of the PNode are accurate.  Usually this is superfluous, but
        // we have seen occasions where this was needed in order for this class to work as intended.
        node.getFullBoundsReference();

        //Take away any local offset applied to the node before standardizing, otherwise will be off by that amount
        node.setOffset( 0, 0 );

        //Put the new origin to be at the (0,0)
        node.setOffset( -node.getFullBounds().getMinX(), -node.getFullBounds().getY() );
    }

    public static void main( String[] args ) {
        new PFrame() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            AimxcelPPath pathNode = new AimxcelPPath( new Rectangle( 1000, 1000, 50, 50 ) );
            debug( pathNode );
            pathNode.setOffset( 500, 500 );
            debug( pathNode );
            ZeroOffsetNode child = new ZeroOffsetNode( pathNode );
            debug( child );
            getCanvas().getLayer().addChild( child );
        }}.setVisible( true );
    }

    private static void debug( PNode child ) {
        System.out.println( "x = " + child.getFullBounds().getX() + ", minx = " + child.getFullBounds().getMinX() );
    }
}