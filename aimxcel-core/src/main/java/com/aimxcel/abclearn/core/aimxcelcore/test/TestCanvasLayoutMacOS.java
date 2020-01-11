
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;


public class TestCanvasLayoutMacOS extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestCanvasLayoutMacOS() {
        super( TestCanvasLayoutMacOS.class.getName() );
        setPreferredSize( new Dimension( 600, 400 ) );
        setContentPane( new TestCanvas() );
        pack();
    }

    private class TestCanvas extends AimxcelPCanvas {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final PPath pathNode;

        public TestCanvas() {
            super();
            // red square
            pathNode = new PPath( new Rectangle2D.Double( 0, 0, 200, 200 ) );
            pathNode.setPaint( Color.RED );
            getLayer().addChild( pathNode );
            updateLayout();
        }

        protected void updateLayout() {
            PDimension canvasSize = new PDimension( getWidth(), getHeight() );
            System.out.println( "updateLayout canvasSize=" + (int) canvasSize.width + "x" + (int) canvasSize.height );
            if ( canvasSize.getWidth() > 0 && canvasSize.getHeight() > 0 ) {
                // center in the canvas
                double x = ( canvasSize.getWidth() - pathNode.getFullBoundsReference().getWidth() ) / 2;
                double y = ( canvasSize.getHeight() - pathNode.getFullBoundsReference().getHeight() ) / 2;
                ;
                pathNode.setOffset( x, y );
            }
        }

    }

    private static class SleepThread extends Thread {

        public SleepThread( long millis ) {
            super( new Runnable() {
                public void run() {
                    while ( true ) {
                        try {
                            SwingUtilities.invokeAndWait( new Runnable() {
                                public void run() {
                                    try {
                                        Thread.sleep( 1000 );
                                    }
                                    catch ( InterruptedException e ) {
                                        e.printStackTrace();
                                    }
                                }
                            } );
                        }
                        catch ( InterruptedException e ) {
                            e.printStackTrace();
                        }
                        catch ( InvocationTargetException e ) {
                            e.printStackTrace();
                        }
                    }
                }
            } );
        }
    }

    public static void main( String[] args ) {

        // This thread serves to make the problem more noticeable.
        new SleepThread( 1000 ).start();

        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                JFrame frame = new TestCanvasLayoutMacOS();
                frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
                SwingUtils.centerWindowOnScreen( frame );
                frame.setVisible( true );
            }
        } );
    }

}
