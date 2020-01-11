// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.photonabsorption.model.atoms;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelColorScheme;



/**
 * Class that represents an atom of oxygen in the model.
 *
 * @author John Blanco
 */
public class OxygenAtom extends Atom {

    //------------------------------------------------------------------------
    // Class Data
    //------------------------------------------------------------------------

    private static final Color REPRESENTATION_COLOR = AimxcelColorScheme.RED_COLORBLIND;
    public static final double MASS = 12.011;   // In atomic mass units (AMU).
    private static final double RADIUS = 73;     // In picometers.

    //------------------------------------------------------------------------
    // Constructor(s)
    //------------------------------------------------------------------------

    public OxygenAtom( Point2D position ) {
        super( REPRESENTATION_COLOR, RADIUS, MASS, position );
    }

    public OxygenAtom() {
        this( new Point2D.Double( 0, 0 ) );
    }
}
