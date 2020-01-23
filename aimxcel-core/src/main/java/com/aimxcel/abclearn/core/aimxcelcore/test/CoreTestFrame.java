// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.core.aimxcelcore.test;

import javax.swing.JFrame;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.BufferedAimxcelPCanvas;


public class CoreTestFrame extends JFrame {

    private AimxcelPCanvas canvas;

    public CoreTestFrame( String title ) {
        super( title );

        canvas = new BufferedAimxcelPCanvas();
        setContentPane( canvas );
        setSize( 800, 600 );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    public void addNode( PNode node ) {
        canvas.getLayer().addChild( node );
    }
}