

package com.aimxcel.abclearn.core.aimxcelcore.test;

import javax.swing.JFrame;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.BufferedAimxcelPCanvas;

import edu.umd.cs.piccolo.PNode;

public class PiccoloTestFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
