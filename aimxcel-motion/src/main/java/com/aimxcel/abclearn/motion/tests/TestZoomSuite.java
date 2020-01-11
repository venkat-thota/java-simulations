
package com.aimxcel.abclearn.motion.tests;


import javax.swing.JFrame;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.motion.graphs.ZoomSuiteNode;

public class TestZoomSuite {
    private JFrame frame;

    public TestZoomSuite() {
        frame = new JFrame();
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        AimxcelPCanvas phetPCanvas = new AimxcelPCanvas();
        ZoomSuiteNode suiteNode = new ZoomSuiteNode();
        suiteNode.setHorizontalZoomOutEnabled( false );
        phetPCanvas.addScreenChild( suiteNode );
        frame.setContentPane( phetPCanvas );
    }

    public static void main( String[] args ) {
        new TestZoomSuite().start();
    }

    private void start() {
        frame.setVisible( true );
    }
}
