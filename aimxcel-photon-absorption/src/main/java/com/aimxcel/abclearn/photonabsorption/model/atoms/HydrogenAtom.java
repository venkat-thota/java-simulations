
package com.aimxcel.abclearn.photonabsorption.model.atoms;

import java.awt.Color;
import java.awt.geom.Point2D;

public class HydrogenAtom extends Atom {

    //------------------------------------------------------------------------
    // Class Data
    //------------------------------------------------------------------------

    private static final Color REPRESENTATION_COLOR = Color.WHITE;
    public static final double MASS = 1;   // In atomic mass units (AMU).
    private static final double RADIUS = 37;     // In picometers.

    //------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------

    public HydrogenAtom( Point2D position ) {
        super( REPRESENTATION_COLOR, RADIUS, MASS, position );
    }

    public HydrogenAtom() {
        this( new Point2D.Double( 0, 0 ) );
    }
}
