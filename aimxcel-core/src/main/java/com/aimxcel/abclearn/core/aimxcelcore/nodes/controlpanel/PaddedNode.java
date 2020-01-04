 
package com.aimxcel.abclearn.core.aimxcelcore.nodes.controlpanel;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.kit.ZeroOffsetNode;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PText;


public class PaddedNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaddedNode( final double paddedWidth, final double paddedHeight, final PNode node ) {
        assert node.getFullBoundsReference().getWidth() <= paddedWidth && node.getFullBoundsReference().getHeight() <= paddedHeight;
        addChild( new AimxcelPPath( new Rectangle2D.Double( 0, 0, paddedWidth, paddedHeight ) ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setVisible( false );
            setPickable( false );
            setChildrenPickable( false );
            setStroke( null );
        }} );
        addChild( new ZeroOffsetNode( node ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( paddedWidth / 2 - node.getFullBounds().getWidth() / 2, paddedHeight / 2 - node.getFullBounds().getHeight() / 2 );
        }} );
    }

    public PaddedNode( final Dimension2D paddedSize, final PNode node ) {
        this( paddedSize.getWidth(), paddedSize.getHeight(), node );
    }

    // test
    public static void main( String[] args ) {

        PText text = new PText( "hello" );

        final double desiredWidth = text.getFullBoundsReference().getWidth() + 10;
        final double desiredHeight = text.getFullBoundsReference().getHeight() + 10;
        PaddedNode node = new PaddedNode( desiredWidth, desiredHeight, text );

        // Verify that PaddedNode gave us the size we asked for.
        System.out.println( "width: desired=" + desiredWidth + ", actual=" + node.getFullBoundsReference().getWidth() );
        System.out.println( "height: desired=" + desiredHeight + ", actual=" + node.getFullBoundsReference().getHeight() );
        assert ( desiredWidth == node.getFullBoundsReference().getWidth() );
        assert ( desiredHeight == node.getFullBoundsReference().getHeight() );
    }
}