

/*  */
package com.aimxcel.abclearn.core.aimxcelcore.test;

import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;


public class TestRenderingHints {
    public static void main( String[] args ) {
        PCanvas pCanvas = new PCanvas();
        final PNode pText = new PPath( new Ellipse2D.Double( 0, 0, 30, 30 ) );
        PNode parent1 = new PNode();
        parent1.addChild( pText );
//        parent1.setAntialias( true );
        pCanvas.getLayer().addChild( parent1 );

        PNode pText2 = new PPath( new Ellipse2D.Double( 0, 0, 30, 30 ) );

        PNode parent2 = new PNode();
        parent2.addChild( pText2 );
        parent2.addChild( new PPath( new Ellipse2D.Double( 32, 0, 30, 30 ) ) );
        parent2.setOffset( 0, parent1.getFullBounds().getHeight() + 2 );

//        parent2.setAntialias( false );
//        pText2.setAntialias( true );

        pCanvas.getLayer().addChild( parent2 );

        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( pCanvas );
        frame.setSize( 400, 600 );
        frame.setVisible( true );
    }
}
