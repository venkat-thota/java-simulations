

package com.aimxcel.abclearn.core.aimxcelcore.services;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;


public class ZPNodeShowHideControlTester {

    public static void main( String[] args ) {

        AimxcelPCanvas canvas = new AimxcelPCanvas();

        PPath pathNode = new PPath( new Rectangle2D.Double( 0, 0, 200, 200 ) );
        pathNode.setPaint( null );
        pathNode.setStrokePaint( Color.RED );
        pathNode.setStroke( new BasicStroke( 3f ) );

        PText textNode = new PText( "Blah blah blah" );

        PNode parentNode = new PComposite(); // breaks this test case! PComposite nodes don't forward events to children
        parentNode.addChild( pathNode );
        parentNode.addChild( textNode );
        textNode.setOffset( 0, 100 );
        parentNode.setOffset( 100, 100 );
        canvas.getLayer().addChild( parentNode );

        PNodeShowHideControl showHideControl = new PNodeShowHideControl( parentNode, "Press me" );

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( canvas );
        frame.setSize( 800, 600 );
        frame.setVisible( true );
    }

}
