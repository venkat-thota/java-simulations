
package com.aimxcel.abclearn.glaciers.view;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;

public class MarkerNode extends PPath {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MarkerNode() {
        super();
        setPathTo( new Ellipse2D.Double( -2, -2, 4, 4 ) );
        setStroke( null );
        setPaint( Color.BLUE );
    }
}
