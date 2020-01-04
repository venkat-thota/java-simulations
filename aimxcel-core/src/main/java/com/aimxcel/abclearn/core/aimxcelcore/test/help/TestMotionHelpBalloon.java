
package com.aimxcel.abclearn.core.aimxcelcore.test.help;


import javax.swing.JFrame;

import com.aimxcel.abclearn.core.aimxcelcore.help.MotionHelpBalloon;

import edu.umd.cs.piccolo.PCanvas;

public class TestMotionHelpBalloon {
    private JFrame frame;
    private MotionHelpBalloon helpBalloon;

    public TestMotionHelpBalloon() {
        frame = new JFrame();
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        PCanvas pCanvas = new PCanvas();
        frame.setContentPane( pCanvas );

        helpBalloon = new MotionHelpBalloon( pCanvas, "<html>Help!<br>Wiggle Me!</html>" );

        pCanvas.getLayer().addChild( helpBalloon );
        helpBalloon.setOffset( 500, -20 );

        frame.setVisible( true );
        helpBalloon.animateTo( 250, 250 );
    }

    public static void main( String[] args ) {
        new TestMotionHelpBalloon();
    }
}

