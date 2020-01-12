package com.aimxcel.abclearn.buildamolecule.model;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
public class Molecule extends MoleculeStructure<Atom2D> {

    /**
     * @return Where the molecule is right now
     */
    public PBounds getPositionBounds() {
        PBounds bounds = null;
        for ( Atom2D atom : getAtoms() ) {
            PBounds atomBounds = atom.getPositionBounds();
            if ( bounds == null ) {
                bounds = atomBounds;
            }
            else {
                bounds.add( atomBounds );
            }
        }
        return bounds;
    }

    /**
     * @return Where the molecule will end up
     */
    public PBounds getDestinationBounds() {
        PBounds bounds = null;
        for ( Atom2D atom : getAtoms() ) {
            PBounds atomBounds = atom.getDestinationBounds();
            if ( bounds == null ) {
                bounds = atomBounds;
            }
            else {
                bounds.add( atomBounds );
            }
        }
        return bounds;
    }

    public void shiftDestination( Vector2D delta ) {
        for ( Atom2D atom : getAtoms() ) {
            atom.setDestination( atom.getDestination().plus( delta ) );
        }
    }
}
