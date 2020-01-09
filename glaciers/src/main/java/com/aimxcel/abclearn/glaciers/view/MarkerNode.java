// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.glaciers.view;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;

/**
 * MarkerNode is used to mark (x,z) locations in the view, for debugging purposes.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class MarkerNode extends PPath {

    public MarkerNode() {
        super();
        setPathTo( new Ellipse2D.Double( -2, -2, 4, 4 ) );
        setStroke( null );
        setPaint( Color.BLUE );
    }
}
