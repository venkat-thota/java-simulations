
package com.aimxcel.abclearn.core.aimxcelcore.test.help;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestComponentListener {
    private JFrame frame;

    public TestComponentListener() {
        frame = new JFrame();
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JPanel pCanvas = new JPanel();
        pCanvas.addComponentListener( new ComponentListener() {
            public void componentHidden( ComponentEvent e ) {
            }

            public void componentMoved( ComponentEvent e ) {
            }

            public void componentResized( ComponentEvent e ) {
                System.out.println( "TestMotionHelpBalloon.resized" );
            }

            public void componentShown( ComponentEvent e ) {
                System.out.println( "TestMotionHelpBalloon.componentShown" );
            }
        } );
        frame.setContentPane( pCanvas );
    }

    public static void main( String[] args ) {
        new TestComponentListener().start();
    }

    private void start() {
        frame.setVisible( true );
    }
}

