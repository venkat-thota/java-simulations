
package com.aimxcel.abclearn.photonabsorption.model.molecules;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.photonabsorption.model.Molecule;
import com.aimxcel.abclearn.photonabsorption.model.atoms.OxygenAtom;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
public class O extends Molecule {

    // ------------------------------------------------------------------------
    // Class Data
    // ------------------------------------------------------------------------

    // ------------------------------------------------------------------------
    // Instance Data
    // ------------------------------------------------------------------------

    private final OxygenAtom oxygenAtom = new OxygenAtom();

    // ------------------------------------------------------------------------
    // Constructor(s)
    // ------------------------------------------------------------------------

    public O( Point2D inititialCenterOfGravityPos ) {

        addAtom( oxygenAtom );

        // Set the initial offsets.
        initializeAtomOffsets();

        // Set the initial COG position.
        setCenterOfGravityPos( inititialCenterOfGravityPos );
    }

    public O() {
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
        addInitialAtomCogOffset( oxygenAtom, new MutableVector2D( 0, 0 ) );

        updateAtomPositions();
    }
}
