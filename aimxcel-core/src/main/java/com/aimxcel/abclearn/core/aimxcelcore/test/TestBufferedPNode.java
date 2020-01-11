
package com.aimxcel.abclearn.core.aimxcelcore.test;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.util.Random;

import javax.swing.JFrame;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.BufferedPNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PPanEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PZoomEventHandler;

public class TestBufferedPNode {
    private JFrame frame;
    private AimxcelPCanvas aimxcelPCanvas;

    public TestBufferedPNode() {
        frame = new JFrame();
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        aimxcelPCanvas = new AimxcelPCanvas();
        aimxcelPCanvas.setPanEventHandler( new PPanEventHandler() );
        aimxcelPCanvas.setZoomEventHandler( new PZoomEventHandler() );

        frame.setContentPane( aimxcelPCanvas );
        final ExpensiveNode expensiveNode = new ExpensiveNode();
        final BufferedPNode bufferedPNode = new BufferedPNode( aimxcelPCanvas, expensiveNode );
//        aimxcelPCanvas.addWorldChild( expensiveNode );
        aimxcelPCanvas.addWorldChild( bufferedPNode );

        aimxcelPCanvas.addKeyListener( new KeyAdapter() {
            public void keyPressed( KeyEvent e ) {
                System.out.println( "TestBufferedPNode.keyPressed" );
                aimxcelPCanvas.getCamera().scaleView( 1.1 );
                aimxcelPCanvas.repaint();
            }
        } );
    }

    static class ExpensiveNode extends PNode {
        Random random = new Random();

        public ExpensiveNode() {
            int w = 500;
            int h = 500;
            for ( int i = 0; i < 2000; i++ ) {
                AimxcelPPath path = new AimxcelPPath( new Line2D.Double( random.nextDouble() * w, random.nextDouble() * h, random.nextDouble() * w, random.nextDouble() * h ), new BasicStroke( random.nextFloat() * 3 ), new Color( random.nextFloat(), random.nextFloat(), random.nextFloat() ) );
                addChild( path );
            }
        }
    }

    private void start() {
        frame.setVisible( true );
        aimxcelPCanvas.requestFocus();
    }

    public static void main( String[] args ) {
        new TestBufferedPNode().start();
    }
}
