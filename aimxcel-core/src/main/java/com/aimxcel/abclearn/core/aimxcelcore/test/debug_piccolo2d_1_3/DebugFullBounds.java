
package com.aimxcel.abclearn.core.aimxcelcore.test.debug_piccolo2d_1_3;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.nodes.PComposite;


public class DebugFullBounds extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color BOUNDS_COLOR = Color.RED;
    private static final Color FULL_BOUNDS_COLOR = Color.BLUE;

    private final PNode compositeNode;
    private final PText boundsText, fullBoundsText;
    private final PPath boundsPath, fullBoundsPath;
    private final Rectangle2D boundsRect, fullBoundsRect;
    private final IntegerSpinner xSpinner, ySpinner, widthSpinner, heightSpinner;

    public DebugFullBounds() {
        setSize( new Dimension( 500, 400 ) );

        // canvas
        PCanvas canvas = new PCanvas();
        canvas.removeInputEventListener( canvas.getZoomEventHandler() );
        canvas.removeInputEventListener( canvas.getPanEventHandler() );

        // a composite node
        compositeNode = new MyComposite();
        canvas.getLayer().addChild( compositeNode );

        // display the composite node's bounds as text
        boundsText = new PText( "?" );
        boundsText.setTextPaint( BOUNDS_COLOR );
        boundsText.setOffset( 20, 250 );
        canvas.getLayer().addChild( boundsText );

        // display the composite node's full bounds as text
        fullBoundsText = new PText( "?" );
        fullBoundsText.setTextPaint( FULL_BOUNDS_COLOR );
        fullBoundsText.setOffset( 20, boundsText.getFullBoundsReference().getMaxY() + 20 );
        canvas.getLayer().addChild( fullBoundsText );

        // display the composite node's bounds as a path
        boundsRect = new Rectangle2D.Double();
        boundsPath = new PPath();
        boundsPath.setStrokePaint( BOUNDS_COLOR );
        boundsPath.setStroke( new BasicStroke( 1f ) );
        canvas.getLayer().addChild( boundsPath );

        // display the composite node's full bounds as a path
        fullBoundsRect = new Rectangle2D.Double();
        fullBoundsPath = new PPath();
        fullBoundsPath.setStrokePaint( FULL_BOUNDS_COLOR );
        fullBoundsPath.setStroke( new BasicStroke( 1f ) );
        canvas.getLayer().addChild( fullBoundsPath );

        // controls for the composite node's bounds
        xSpinner = new IntegerSpinner( 0, 0, 100 );
        ySpinner = new IntegerSpinner( 0, 0, 100 );
        widthSpinner = new IntegerSpinner( 0, 0, 100 );
        heightSpinner = new IntegerSpinner( 0, 0, 100 );
        JPanel controlPanel = new JPanel();
        controlPanel.setBorder( new LineBorder( Color.BLACK ) );
        controlPanel.add( new JLabel( "bounds.x=" ) );
        controlPanel.add( xSpinner );
        controlPanel.add( new JLabel( "y=" ) );
        controlPanel.add( ySpinner );
        controlPanel.add( new JLabel( "w=" ) );
        controlPanel.add( widthSpinner );
        controlPanel.add( new JLabel( "h=" ) );
        controlPanel.add( heightSpinner );

        JPanel panel = new JPanel( new BorderLayout() );
        panel.add( canvas, BorderLayout.CENTER );
        panel.add( controlPanel, BorderLayout.SOUTH );
        setContentPane( panel );

        // When any spinner changes, update the composite node's bounds.
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                updateNodeBounds();
            }
        };
        xSpinner.addChangeListener( changeListener );
        ySpinner.addChangeListener( changeListener );
        widthSpinner.addChangeListener( changeListener );
        heightSpinner.addChangeListener( changeListener );

        // When the composite node's bounds or full bounds change, update the display.
        compositeNode.addPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
                if ( evt.getPropertyName() == PNode.PROPERTY_BOUNDS ) {
                    updateBoundsNode();
                }
                else if ( evt.getPropertyName() == PNode.PROPERTY_FULL_BOUNDS ) {
                    updateFullBoundsNode();
                }
            }
        } );

        // initial state
        updateNodeBounds();
        updateBoundsNode();
        updateFullBoundsNode();
    }

    /*
     * A composite node whose child is offset from the container's origin.
     * This node draws nothing itself, all drawing is done by the child,
     * in this case a rectangle.
     */
    private static class MyComposite extends PComposite {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyComposite() {
            PPath rectangleNode = new PPath( new Rectangle2D.Double( 0, 0, 100, 100 ) );
            rectangleNode.setOffset( 50, 50 );
            addChild( rectangleNode );
        }
    }

    /* A spinner for integer values. This implementation is not robust! */
    private static class IntegerSpinner extends JSpinner {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IntegerSpinner( int value, int min, int max ) {
            super( new SpinnerNumberModel( value, min, max, 1 ) );
        }

        public int getIntValue() {
            return ( (Integer) getValue() ).intValue();
        }
    }

    /* Update the composite node's bounds to match the control values. */
    private void updateNodeBounds() {
        PBounds b = new PBounds( xSpinner.getIntValue(), ySpinner.getIntValue(), widthSpinner.getIntValue(), heightSpinner.getIntValue() );
        compositeNode.setBounds( b );
    }

    /* Update text and path that shows the composite node's bounds. */
    private void updateBoundsNode() {
        PBounds bounds = compositeNode.getBoundsReference();
        boundsText.setText( "bounds=" + bounds.toString() );
        boundsRect.setRect( bounds );
        boundsPath.setPathTo( boundsRect );
    }

    /* Update text and path that shows the composite node's full bounds. */
    private void updateFullBoundsNode() {
        PBounds fullBounds = compositeNode.getFullBoundsReference();
        fullBoundsText.setText( "fullBounds=" + fullBounds.toString() );
        fullBoundsRect.setRect( fullBounds );
        fullBoundsPath.setPathTo( fullBoundsRect );
    }

    public static void main( String[] args ) {
        JFrame frame = new DebugFullBounds();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
}
