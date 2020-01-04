
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * A simple invisible spacer with no stroke. Used to modify full-bounds
 */
public class Spacer extends AimxcelPPath {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Rectangle2D rectangle;

    public Spacer( double x, double y, double width, double height ) {
        this( new Rectangle2D.Double( x, y, width, height ) );
    }

    public Spacer( Rectangle2D rectangle ) {
        super( rectangle, new Color( 0, 0, 0, 0 ), null, Color.RED );
        this.rectangle = rectangle;
    }

    public Rectangle2D getRectangle() {
        return rectangle;
    }
}
