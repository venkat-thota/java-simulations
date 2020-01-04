
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.umd.cs.piccolox.pswing.PSwing;
import edu.umd.cs.piccolox.pswing.PSwingCanvas;


public class TestEmbeddedPSwingCanvas {
    private JFrame frame;
    private PSwingCanvas embeddedCanvas;
    private PSwingCanvas outerCanvas;
    private PSwing button;
    private PSwing embeddedPSwing;

    public TestEmbeddedPSwingCanvas() {
        embeddedCanvas = new PSwingCanvas();
        button = new PSwing( new JButton( "Button" ) );
        embeddedCanvas.getLayer().addChild( button );
        embeddedCanvas.setPreferredSize( new Dimension( 400, 400 ) );//one way to solve this problem
        embeddedCanvas.setPanEventHandler( null );
        embeddedCanvas.setZoomEventHandler( null );

        outerCanvas = new PSwingCanvas();
        embeddedPSwing = new PSwing( embeddedCanvas );
        outerCanvas.getLayer().addChild( embeddedPSwing );

        frame = new JFrame( "Test Embedded PSwingCanvas" );
        frame.setContentPane( outerCanvas );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 200, 200 );
    }

    public static void main( String[] args ) {
        new TestEmbeddedPSwingCanvas().start();

    }

    private void start() {
        frame.setVisible( true );
    }

}
