
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


public class VBox extends Box {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Marker interface, to prevent use of inappropriate predefined strategies.
    public static interface HorizontalPositionStrategy extends PositionStrategy {
    }

    // predefined strategies that provide common forms of horizontal alignment
    public static final HorizontalPositionStrategy CENTER_ALIGNED = new CenterAligned();
    public static final HorizontalPositionStrategy LEFT_ALIGNED = new LeftAligned();
    public static final HorizontalPositionStrategy RIGHT_ALIGNED = new RightAligned();

    // defaults
    private static final double DEFAULT_Y_SPACING = 10;
    private static final HorizontalPositionStrategy DEFAULT_POSITION_STRATEGY = CENTER_ALIGNED;

    // Creates a VBox with default spacing and alignment.
    public VBox( PNode... children ) {
        this( DEFAULT_Y_SPACING, children );
    }

    // Creates a VBox with default alignment.
    public VBox( double ySpacing, PNode... children ) {
        this( ySpacing, DEFAULT_POSITION_STRATEGY, children );
    }

    // Creates a VBox with default spacing.
    public VBox( HorizontalPositionStrategy positionStrategy, PNode... children ) {
        this( DEFAULT_Y_SPACING, positionStrategy, children );
    }

    /**
     * Most general constructor.
     *
     * @param ySpacing         vertical space between children
     * @param positionStrategy strategy used to horizontally position a child.
     * @param children         children to be added to the box, in top to bottom order
     */
    public VBox( double ySpacing, HorizontalPositionStrategy positionStrategy, PNode... children ) {
        super( ySpacing,
               // For handling alignment, the relevant dimensions is the width of the widest child.
               new Function1<PBounds, Double>() {
                   public Double apply( PBounds bounds ) {
                       return bounds.getWidth();
                   }
               },
               // For vertical placement, the relevant dimension is a child's height.
               new Function1<PBounds, Double>() {
                   public Double apply( PBounds bounds ) {
                       return bounds.getHeight();
                   }
               },
               positionStrategy,
               children
        );
    }

    // Horizontally centers a node in a horizontal space.
    private static class CenterAligned implements HorizontalPositionStrategy {
        public Point2D getRelativePosition( PNode node, double maxWidth, double y ) {
            return new Point2D.Double( maxWidth / 2 - node.getFullBounds().getWidth() / 2, y );
        }
    }

    // Left aligns a node in a horizontal space.
    private static class LeftAligned implements HorizontalPositionStrategy {
        public Point2D getRelativePosition( PNode node, double maxWidth, double y ) {
            return new Point2D.Double( 0, y );
        }
    }

    // Right aligns a node in a horizontal space.
    private static class RightAligned implements HorizontalPositionStrategy {
        public Point2D getRelativePosition( PNode node, double maxWidth, double y ) {
            return new Point2D.Double( maxWidth - node.getFullBounds().getWidth(), y );
        }
    }

    //Test, demonstrates all predefined position strategies.
    public static void main( String[] args ) {

        class TestBox extends VBox {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public TestBox( HorizontalPositionStrategy positionStrategy, String name ) {
                super( 5, positionStrategy );
                addChild( new PText( name ) );
                addChild( new AimxcelPPath( new Rectangle2D.Double( 0, 0, 50, 50 ) ) );
                addChild( new AimxcelPPath( new Ellipse2D.Double( 0, 0, 75, 75 ) ) );
                addChild( new AimxcelPPath( new Ellipse2D.Double( -100, -100, 100, 100 ) ) );
            }
        }

        final double xSpacing = 20;
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
                TestBox box1 = new TestBox( LEFT_ALIGNED, "Left" );
                addScreenChild( box1 );
                box1.setOffset( 20, 20 );
                TestBox box2 = new TestBox( CENTER_ALIGNED, "Center" );
                addScreenChild( box2 );
                box2.setOffset( box1.getFullBounds().getMaxX() + xSpacing, box1.getYOffset() );
                TestBox box3 = new TestBox( RIGHT_ALIGNED, "Right" );
                addScreenChild( box3 );
                box3.setOffset( box2.getFullBounds().getMaxX() + xSpacing, box2.getYOffset() );
            }} );
            setSize( 800, 600 );
            setDefaultCloseOperation( EXIT_ON_CLOSE );
        }}.setVisible( true );
    }
}