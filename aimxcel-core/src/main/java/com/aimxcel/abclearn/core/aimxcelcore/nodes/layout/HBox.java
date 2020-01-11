
package com.aimxcel.abclearn.core.aimxcelcore.nodes.layout;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;

public class HBox extends Box {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Marker interface, to prevent use of inappropriate predefined strategies.
    public static interface VerticalPositionStrategy extends PositionStrategy {
    }

    // predefined strategies that provide common forms of vertical alignment
    public static final VerticalPositionStrategy CENTER_ALIGNED = new CenterAligned();
    public static final VerticalPositionStrategy TOP_ALIGNED = new TopAligned();
    public static final VerticalPositionStrategy BOTTOM_ALIGNED = new BottomAligned();

    // defaults
    private static final double DEFAULT_X_SPACING = 10;
    private static final VerticalPositionStrategy DEFAULT_POSITION_STRATEGY = CENTER_ALIGNED;

    // Creates a VBox with default spacing and alignment.
    public HBox( PNode... children ) {
        this( 10, children );
    }

    public HBox( double spacing, PNode... children ) {
        this( spacing, DEFAULT_POSITION_STRATEGY, children );
    }

    public HBox( VerticalPositionStrategy positionStrategy, PNode... children ) {
        this( DEFAULT_X_SPACING, positionStrategy, children );
    }

    // Creates a VBox with default alignment.
    public HBox( double spacing, VerticalPositionStrategy positionStrategy, PNode... children ) {
        super( spacing,
               // For handling alignment, the relevant dimensions is the height of the tallest child.
               new Function1<PBounds, Double>() {
                   public Double apply( PBounds bounds ) {
                       return bounds.getHeight();
                   }
               },
               // For horizontal placement, the relevant dimension is a child's width.
               new Function1<PBounds, Double>() {
                   public Double apply( PBounds bounds ) {
                       return bounds.getWidth();
                   }
               },
               positionStrategy,
               children
        );
    }

    // Vertically centers a node in a vertical space.
    private static class CenterAligned implements VerticalPositionStrategy {
        public Point2D getRelativePosition( PNode node, double maxHeight, double x ) {
            return new Point2D.Double( x, maxHeight / 2 - node.getFullBounds().getHeight() / 2 );
        }
    }

    // Top aligns a node in a vertical space.
    private static class TopAligned implements VerticalPositionStrategy {
        public Point2D getRelativePosition( PNode node, double maxHeight, double x ) {
            return new Point2D.Double( x, 0 );
        }
    }

    // Bottom aligns a node in a vertical space.
    private static class BottomAligned implements VerticalPositionStrategy {
        public Point2D getRelativePosition( PNode node, double maxHeight, double x ) {
            return new Point2D.Double( x, maxHeight - node.getFullBounds().getHeight() );
        }
    }

    //Test, demonstrates all predefined position strategies.
    public static void main( String[] args ) {

        class TestBox extends HBox {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public TestBox( VerticalPositionStrategy positionStrategy, String name ) {
                super( 5, positionStrategy );
                addChild( new PText( name ) );
                addChild( new AimxcelPPath( new Rectangle2D.Double( 0, 0, 50, 50 ) ) );
                addChild( new AimxcelPPath( new Ellipse2D.Double( 0, 0, 75, 75 ) ) );
                addChild( new AimxcelPPath( new Ellipse2D.Double( -100, -100, 100, 100 ) ) );
            }
        }

        final double ySpacing = 20;
        new JFrame() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setContentPane( new AimxcelPCanvas() {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                TestBox box1 = new TestBox( TOP_ALIGNED, "Top" );
                addScreenChild( box1 );
                box1.setOffset( 20, 20 );
                TestBox box2 = new TestBox( CENTER_ALIGNED, "Center" );
                addScreenChild( box2 );
                box2.setOffset( box1.getXOffset(), box1.getFullBounds().getMaxY() + ySpacing );
                TestBox box3 = new TestBox( BOTTOM_ALIGNED, "Bottom" );
                addScreenChild( box3 );
                box3.setOffset( box2.getXOffset(), box2.getFullBounds().getMaxY() + ySpacing );
            }} );
            setSize( 800, 600 );
            setDefaultCloseOperation( EXIT_ON_CLOSE );
        }}.setVisible( true );
    }
}