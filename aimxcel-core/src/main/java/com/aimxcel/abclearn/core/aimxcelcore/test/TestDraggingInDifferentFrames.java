
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PDragEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PZoomEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;


public class TestDraggingInDifferentFrames {
    private JFrame frame = new JFrame();

    public static class BoxNode extends AimxcelPPath {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BoxNode() {
            this( 100, 100 );
            addChild( new PText( "Origin" ) );
            final PText child = new PText( "Drag me" );
            child.setOffset( 50, 50 );
            child.addInputEventListener( new PDragEventHandler() );
            addChild( child );
        }

        public BoxNode( int width, int height ) {
            super( new Rectangle2D.Double( 0, 0, width, height ), new BasicStroke(), Color.darkGray );
        }
    }

    public TestDraggingInDifferentFrames() {
        final AimxcelPCanvas contentPane = new AimxcelPCanvas();
        contentPane.setZoomEventHandler( new PZoomEventHandler() );
        frame.setContentPane( contentPane );
        frame.setSize( 1024, 768 );

        BoxNode boxNode = new BoxNode();
        contentPane.addScreenChild( boxNode );

        PNode rotateFrame = new PNode();
        rotateFrame.rotate( Math.PI / 2 );
        rotateFrame.translate( 0, -300 );
        rotateFrame.addChild( new BoxNode() );
        contentPane.addScreenChild( rotateFrame );

        PNode offsetFrame = new PNode();
        offsetFrame.setOffset( 200, 200 );
        offsetFrame.addChild( new BoxNode() );

        PNode offsetScaleNode = new PNode();
        offsetScaleNode.setOffset( 0, 200 );
        offsetScaleNode.scale( 2.0 );
        offsetScaleNode.addChild( new BoxNode() );
        contentPane.addScreenChild( offsetScaleNode );

        PNode parentNode = new PNode();
        parentNode.translate( 400, 400 );
        parentNode.scale( 1.2 );
        parentNode.rotate( Math.PI / 12 );
        parentNode.addChild( new BoxNode() );
        contentPane.addScreenChild( parentNode );

        PNode childNode = new PNode();
        childNode.scale( 1.2 );
        childNode.rotate( Math.PI / 6 );
        childNode.translate( 50, 50 );
        childNode.addChild( boxNode );

        parentNode.addChild( childNode );
    }

    public static void main( String[] args ) {
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                new TestDraggingInDifferentFrames().start();
            }
        } );
    }

    private void start() {
        frame.setVisible( true );
    }
}