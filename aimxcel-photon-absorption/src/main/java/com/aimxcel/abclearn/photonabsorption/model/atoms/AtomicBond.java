
package com.aimxcel.abclearn.photonabsorption.model.atoms;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;

public class AtomicBond extends SimpleObservable {

    // ------------------------------------------------------------------------
    // Instance Data
    // ------------------------------------------------------------------------

    private final Atom atom1, atom2;  // Atoms involved in the bond.
    private final int bondCount;      // Indicates whether this is a single,
    // double, triple, etc. bond.

    // ------------------------------------------------------------------------
    // Constructor(s)
    // ------------------------------------------------------------------------

    /**
     * Constructor.
     */
    public AtomicBond( Atom atom1, Atom atom2, int bondCount ) {
        this.atom1 = atom1;
        this.atom2 = atom2;
        this.bondCount = bondCount;

        // Listen to the atoms that are involved in this bond and send an
        // update notification whenever they move.
        this.atom1.addObserver( new SimpleObserver() {
            public void update() {
                notifyObservers();
            }
        } );
        this.atom2.addObserver( new SimpleObserver() {
            public void update() {
                notifyObservers();
            }
        } );
    }

    /**
     * Constructor that assumes a single bond.
     */
    public AtomicBond( Atom atom1, Atom atom2 ) {
        this( atom1, atom2, 1 );
    }

    // ------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------

    public Atom getAtom1() {
        return atom1;
    }


    public Atom getAtom2() {
        return atom2;
    }

    public int getBondCount() {
        return bondCount;
    }
}
