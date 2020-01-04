

/*  */
package com.aimxcel.abclearn.common.piccoloaimxcel.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.piccoloaimxcel.AimxcelPCanvas;

import edu.umd.cs.piccolo.nodes.PPath;

/**
 * User: Sam Reid
 * Date: Aug 1, 2005
 * Time: 7:58:04 AM
 */

public class TestAimxcelPCanvas {
    private JFrame frame;

    public TestAimxcelPCanvas() {
        frame = new JFrame();
        AimxcelPCanvas phetPCanvas = new AimxcelPCanvas();
        frame.setContentPane( phetPCanvas );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 800, 800 );

        PPath path = new PPath( new Rectangle( 400, 400, 100, 100 ), new BasicStroke( 3 ) );
        path.setPaint( Color.blue );
        path.setStrokePaint( Color.black );
        phetPCanvas.addWorldChild( path );
    }

    public static void main( String[] args ) {
        TestAimxcelPCanvas testAimxcelPCanvas = new TestAimxcelPCanvas();
        testAimxcelPCanvas.start();
    }

    private void start() {
        frame.setVisible( true );
    }
}
