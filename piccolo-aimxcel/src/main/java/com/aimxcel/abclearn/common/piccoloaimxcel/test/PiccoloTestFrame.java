

package com.aimxcel.abclearn.common.piccoloaimxcel.test;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.piccoloaimxcel.AimxcelPCanvas;
import com.aimxcel.abclearn.common.piccoloaimxcel.BufferedAimxcelPCanvas;

import edu.umd.cs.piccolo.PNode;

public class PiccoloTestFrame extends JFrame {

    private AimxcelPCanvas canvas;

    public PiccoloTestFrame( String title ) {
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
