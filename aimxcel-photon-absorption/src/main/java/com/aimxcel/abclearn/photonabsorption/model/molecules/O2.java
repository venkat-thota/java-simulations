
package com.aimxcel.abclearn.photonabsorption.model.molecules;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.photonabsorption.model.Molecule;
import com.aimxcel.abclearn.photonabsorption.model.atoms.AtomicBond;
import com.aimxcel.abclearn.photonabsorption.model.atoms.OxygenAtom;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;

public class O2 extends Molecule {

    // ------------------------------------------------------------------------
    // Class Data
    // ------------------------------------------------------------------------

    private static final double INITIAL_OXYGEN_OXYGEN_DISTANCE = 170; // In picometers.

    // ------------------------------------------------------------------------
    // Instance Data
    // ------------------------------------------------------------------------

    private final OxygenAtom oxygenAtom1 = new OxygenAtom();
    private final OxygenAtom oxygenAtom2 = new OxygenAtom();
    private final AtomicBond oxygenOxygenBond = new AtomicBond( oxygenAtom1, oxygenAtom2, 2 );

    // ------------------------------------------------------------------------
    // Constructor(s)
    // ------------------------------------------------------------------------

    public O2( Point2D inititialCenterOfGravityPos ) {
        // Configure the base class.  It would be better to do this through
        // nested constructors, but I (jblanco) wasn't sure how to do this.
        addAtom( oxygenAtom1 );
        addAtom( oxygenAtom2 );
        addAtomicBond( oxygenOxygenBond );

        // Set the initial offsets.
        initializeAtomOffsets();

        // Set the initial COG position.
        setCenterOfGravityPos( inititialCenterOfGravityPos );
    }

    public O2() {
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
        addInitialAtomCogOffset( oxygenAtom1, new MutableVector2D( -INITIAL_OXYGEN_OXYGEN_DISTANCE / 2, 0 ) );
        addInitialAtomCogOffset( oxygenAtom2, new MutableVector2D( INITIAL_OXYGEN_OXYGEN_DISTANCE / 2, 0 ) );

        updateAtomPositions();
    }
}
