

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.JFrame;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;


public class ZFineCrosshairNodeTester {

    public static void main( String[] args ) {

        PCanvas canvas = new PCanvas();

        // Exercise the first constructor
        FineCrosshairNode crosshair1 = new FineCrosshairNode( 20, new BasicStroke( 2f ), Color.RED );
        crosshair1.setOffset( 100, 200 );
        canvas.getLayer().addChild( crosshair1 );

        // Exercise the second constructor
        FineCrosshairNode crosshair2 = new FineCrosshairNode( new PDimension( 20, 40 ), new BasicStroke( 1f ), Color.GREEN );
        crosshair2.setOffset( 200, 200 );
        canvas.getLayer().addChild( crosshair2 );

        JFrame frame = new JFrame();
        frame.setContentPane( canvas );
        frame.setSize( 400, 400 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.show();
    }
}
