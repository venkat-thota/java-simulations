

package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.GridPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPNode;
import com.aimxcel.abclearn.core.aimxcelcore.event.BoundedDragHandler;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.pswing.PSwing;


public class TestDragPSwing extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// panel with some check boxes
    private static class MyPanel extends GridPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyPanel( String title ) {
            setOpaque( false );
            setAnchor( Anchor.WEST );
            int row = 0;
            add( new JLabel( title ), row++, 0 );
            add( new JCheckBox( "option1" ), row++, 0 );
            add( new JCheckBox( "option2" ), row++, 0 );
        }
    }

    // composite node with a pswing child
    private static class MyCompositeNode extends AimxcelPNode {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyCompositeNode( String title ) {

            // JPanel wrapped in a PSwing
            PSwing pswing = new PSwing( new MyPanel( title ) );

            // background behind the pswing panel
            double width = 1.5 * pswing.getFullBoundsReference().getWidth();
            double height = 2 * pswing.getFullBoundsReference().getHeight();
            PNode background = new PPath( new Rectangle2D.Double( 0, 0, width, height ) );
            background.setPaint( Color.GRAY );

            // rendering order
            addChild( background );
            addChild( pswing );
        }
    }

    // canvas with a few example nodes
    private static class MyCanvas extends AimxcelPCanvas {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final PPath canvasBoundsNode;

        public MyCanvas() {
            setPreferredSize( new Dimension( 800, 600 ) );

            // canvas bounds, for constrained drags
            canvasBoundsNode = new PPath() {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setStroke( null );
            }};

            // PSwing check box, dragging unconstrained
            PSwing pswing = new PSwing( new JCheckBox( "PSwing" ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                addInputEventListener( new CursorHandler() );
                addInputEventListener( new PDragEventHandler() );
            }};

            // PSwing check box, dragging constrained to canvas
            PSwing pswingConstrained = new PSwing( new JCheckBox( "PSwingConstrained" ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                addInputEventListener( new CursorHandler() );
                addInputEventListener( new BoundedDragHandler( this, canvasBoundsNode ) );
            }};

            // PText, dragging unconstrained
            PText ptext = new PText( "PText" ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setFont( new AimxcelFont( 20 ) );
                addInputEventListener( new CursorHandler() );
                addInputEventListener( new PDragEventHandler() );
            }};

            // PText, dragging constrained to canvas
            PText ptextConstrained = new PText( "PTextConstrained" ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setFont( new AimxcelFont( 20 ) );
                addInputEventListener( new CursorHandler() );
                addInputEventListener( new BoundedDragHandler( this, canvasBoundsNode ) );
            }};

            // composite node, dragging constrained to canvas
            PNode compositeConstrained = new MyCompositeNode( "CompositeConstrained" ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                addInputEventListener( new CursorHandler() );
                addInputEventListener( new BoundedDragHandler( this, canvasBoundsNode ) );
            }};

            // rendering order
            getLayer().addChild( canvasBoundsNode );
            getLayer().addChild( pswing );
            getLayer().addChild( pswingConstrained );
            getLayer().addChild( ptext );
            getLayer().addChild( ptextConstrained );
            getLayer().addChild( compositeConstrained );

            // layout
            pswing.setOffset( 50, 50 );
            pswingConstrained.setOffset( 50, 300 );
            ptext.setOffset( 250, 50 );
            ptextConstrained.setOffset( 250, 300 );
            compositeConstrained.setOffset( 450, 300 );
        }

        // Adjust canvas bounds node, used to constrain dragging.
        @Override
        protected void updateLayout() {
            super.updateLayout();
            Dimension2D worldSize = getWorldSize();
            if ( worldSize.getWidth() > 0 && worldSize.getHeight() > 0 ) {
                canvasBoundsNode.setPathTo( new Rectangle2D.Double( 0, 0, worldSize.getWidth(), worldSize.getHeight() ) );
            }
        }
    }

    // main frame with sim-like layout
    public TestDragPSwing() {
        super( TestDragPSwing.class.getName() );
        setContentPane( new MyCanvas() );
        pack();
    }

    public static void main( String[] args ) {
        JFrame frame = new TestDragPSwing();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );
    }
}
