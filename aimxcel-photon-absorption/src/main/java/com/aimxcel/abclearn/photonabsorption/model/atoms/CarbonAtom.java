
package com.aimxcel.abclearn.photonabsorption.model.atoms;

import java.awt.Color;
import java.awt.geom.Point2D;
public class CarbonAtom extends Atom {

    //------------------------------------------------------------------------
    // Class Data
    //------------------------------------------------------------------------

    private static final Color REPRESENTATION_COLOR = Color.GRAY;
    public static final double MASS = 12.011;   // In atomic mass units (AMU).
    private static final double RADIUS = 77;     // In picometers.

    //------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------

    public CarbonAtom( Point2D position ) {
        super( REPRESENTATION_COLOR, RADIUS, MASS, position );
    }

    public CarbonAtom() {
        this( new Point2D.Double( 0, 0 ) );
    }
}
