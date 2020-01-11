

/*  */
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JFrame;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;



public class TestAimxcelPCanvas {
    private JFrame frame;

    public TestAimxcelPCanvas() {
        frame = new JFrame();
        AimxcelPCanvas aimxcelPCanvas = new AimxcelPCanvas();
        frame.setContentPane( aimxcelPCanvas );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 800, 800 );

        PPath path = new PPath( new Rectangle( 400, 400, 100, 100 ), new BasicStroke( 3 ) );
        path.setPaint( Color.blue );
        path.setStrokePaint( Color.black );
        aimxcelPCanvas.addWorldChild( path );
    }

    public static void main( String[] args ) {
        TestAimxcelPCanvas testAimxcelPCanvas = new TestAimxcelPCanvas();
        testAimxcelPCanvas.start();
    }

    private void start() {
        frame.setVisible( true );
    }
}
