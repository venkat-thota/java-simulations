

/*  */
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.MeasuringTape;

import edu.umd.cs.piccolo.PCanvas;


public class TestMeasuringTape {
    private JFrame frame;

    public TestMeasuringTape() {
        frame = new JFrame( "Test Measuring Tape" );
        PCanvas contentPane = new PCanvas();
        contentPane.setPanEventHandler( null );
        frame.setContentPane( contentPane );
        MeasuringTape child = new MeasuringTape( new ModelViewTransform2D( new Rectangle2D.Double( 0, 0, 100, 100 ), new Rectangle2D.Double( 0, 0, 100, 100 ) ), new Point2D.Double( 0, 0 ) );
        child.setOffset( 100, 100 );
        contentPane.getLayer().addChild( child );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 600, 600 );
    }

    public static void main( String[] args ) {
        new TestMeasuringTape().start();
    }

    private void start() {
        frame.setVisible( true );
    }
}
