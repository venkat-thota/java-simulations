
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.*;

import com.aimxcel.abclearn.core.aimxcelcore.BufferedAimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class PNodeComponent extends BufferedAimxcelPCanvas {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PNode node;

    public PNodeComponent( PNode node ) {
        this.node = node;
        addScreenChild( node );
        node.setOffset( 2, 3 );
        updatePreferredSize();
    }

    public void updatePreferredSize() {
        setPreferredSize( new Dimension( (int) node.getFullBounds().getWidth() + 10, (int) node.getFullBounds().getHeight() + 4 ) );
    }
}
