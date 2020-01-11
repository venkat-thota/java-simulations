

/*  */
package com.aimxcel.abclearn.core.aimxcelcore.test.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.aimxcel.abclearn.core.aimxcelcore.help.MotionHelpBalloon;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dcore.util.PUtil;

public class TestMotionHelpBalloonAnimateToNode {
    private JFrame frame;
    private MotionHelpBalloon helpBalloon;


    public TestMotionHelpBalloonAnimateToNode() {
        frame = new JFrame();
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        PCanvas pCanvas = new PCanvas();

        PUtil.DEFAULT_ACTIVITY_STEP_RATE = 1;
        PUtil.ACTIVITY_SCHEDULER_FRAME_DELAY = 10;

        final PNode dst = new PText( "Destination" );
        pCanvas.getLayer().addChild( dst );
        helpBalloon = new MotionHelpBalloon( pCanvas, "<html>Help!<br>Wiggle Me!</html>" );
        pCanvas.getLayer().addChild( helpBalloon );
        helpBalloon.setOffset( 0, 0 );

        frame.setContentPane( pCanvas );
        Timer timer = new Timer( 30, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                double hz = 0.5;
                double angfreq = hz * 2 * Math.PI;
                dst.setOffset( 450, 400 + 100 * Math.sin( System.currentTimeMillis() / 1000.0 * angfreq ) );
            }
        } );

        frame.setVisible( true );
        timer.start();
        helpBalloon.animateTo( dst );
    }

    public static void main( String[] args ) {
        new TestMotionHelpBalloonAnimateToNode();
    }
}
