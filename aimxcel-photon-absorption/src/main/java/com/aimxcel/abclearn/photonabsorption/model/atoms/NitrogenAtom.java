
package com.aimxcel.abclearn.photonabsorption.model.atoms;

import java.awt.Color;
import java.awt.geom.Point2D;

public class NitrogenAtom extends Atom {

    //------------------------------------------------------------------------
    // Class Data
    //------------------------------------------------------------------------

    private static final Color REPRESENTATION_COLOR = Color.BLUE;
    public static final double MASS = 14.00674;   // In atomic mass units (AMU).
    private static final double RADIUS = 75;       // In picometers.

    //------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------

    public NitrogenAtom( Point2D position ) {
        super( REPRESENTATION_COLOR, RADIUS, MASS, position );
    }

    public NitrogenAtom() {
        this( new Point2D.Double( 0, 0 ) );
    }
}
