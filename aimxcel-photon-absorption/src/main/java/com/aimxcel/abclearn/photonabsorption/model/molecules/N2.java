
package com.aimxcel.abclearn.photonabsorption.model.molecules;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.photonabsorption.model.Molecule;
import com.aimxcel.abclearn.photonabsorption.model.atoms.AtomicBond;
import com.aimxcel.abclearn.photonabsorption.model.atoms.NitrogenAtom;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;

public class N2 extends Molecule {

    // ------------------------------------------------------------------------
    // Class Data
    // ------------------------------------------------------------------------

    private static final double INITIAL_NITROGEN_NITROGEN_DISTANCE = 170; // In picometers.

    // ------------------------------------------------------------------------
    // Instance Data
    // ------------------------------------------------------------------------

    private final NitrogenAtom nitrogenAtom1 = new NitrogenAtom();
    private final NitrogenAtom nitrogenAtom2 = new NitrogenAtom();
    private final AtomicBond nitrogenNitrogenBond = new AtomicBond( nitrogenAtom1, nitrogenAtom2, 3 );

    // ------------------------------------------------------------------------
    // Constructor(s)
    // ------------------------------------------------------------------------

    public N2( Point2D inititialCenterOfGravityPos ) {
        // Configure the base class.  It would be better to do this through
        // nested constructors, but I (jblanco) wasn't sure how to do this.
        addAtom( nitrogenAtom1 );
        addAtom( nitrogenAtom2 );
        addAtomicBond( nitrogenNitrogenBond );

        // Set the initial offsets.
        initializeAtomOffsets();

        // Set the initial COG position.
        setCenterOfGravityPos( inititialCenterOfGravityPos );
    }

    public N2() {
        this( new Point2D.Double( 0, 0 ) );
    }

    // ------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.colorado.aimxcel.common.photonabsorption.model.Molecule#initializeCogOffsets()
     */
    @Override
    protected void initializeAtomOffsets() {
        addInitialAtomCogOffset( nitrogenAtom1, new MutableVector2D( -INITIAL_NITROGEN_NITROGEN_DISTANCE / 2, 0 ) );
        addInitialAtomCogOffset( nitrogenAtom2, new MutableVector2D( INITIAL_NITROGEN_NITROGEN_DISTANCE / 2, 0 ) );

        updateAtomPositions();
    }
}
