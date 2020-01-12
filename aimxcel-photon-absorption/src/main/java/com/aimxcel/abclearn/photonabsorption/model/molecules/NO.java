
package com.aimxcel.abclearn.photonabsorption.model.molecules;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.photonabsorption.model.Molecule;
import com.aimxcel.abclearn.photonabsorption.model.atoms.AtomicBond;
import com.aimxcel.abclearn.photonabsorption.model.atoms.NitrogenAtom;
import com.aimxcel.abclearn.photonabsorption.model.atoms.OxygenAtom;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
public class NO extends Molecule {

    // ------------------------------------------------------------------------
    // Class Data
    // ------------------------------------------------------------------------

    private static final double INITIAL_NITROGEN_OXYGEN_DISTANCE = 170; // In picometers.

    // ------------------------------------------------------------------------
    // Instance Data
    // ------------------------------------------------------------------------

    private final NitrogenAtom nitrogenAtom = new NitrogenAtom();
    private final OxygenAtom oxygenAtom = new OxygenAtom();
    private final AtomicBond nitrogenOxygenBond = new AtomicBond( nitrogenAtom, oxygenAtom, 2 );

    // ------------------------------------------------------------------------
    // Constructor(s)
    // ------------------------------------------------------------------------

    public NO( Point2D inititialCenterOfGravityPos ) {
        // Configure the base class.  It would be better to do this through
        // nested constructors, but I (jblanco) wasn't sure how to do this.
        addAtom( nitrogenAtom );
        addAtom( oxygenAtom );
        addAtomicBond( nitrogenOxygenBond );

        // Set the initial offsets.
        initializeAtomOffsets();

        // Set the initial COG position.
        setCenterOfGravityPos( inititialCenterOfGravityPos );
    }

    public NO() {
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
        addInitialAtomCogOffset( nitrogenAtom, new MutableVector2D( -INITIAL_NITROGEN_OXYGEN_DISTANCE / 2, 0 ) );
        addInitialAtomCogOffset( oxygenAtom, new MutableVector2D( INITIAL_NITROGEN_OXYGEN_DISTANCE / 2, 0 ) );

        updateAtomPositions();
    }
}
