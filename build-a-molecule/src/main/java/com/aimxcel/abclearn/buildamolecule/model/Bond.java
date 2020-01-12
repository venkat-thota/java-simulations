package com.aimxcel.abclearn.buildamolecule.model;

import com.aimxcel.abclearn.chemistry.model.Atom;

public class Bond<T extends Atom> {
    public T a;
    public T b;

    public Bond( T a, T b ) {
        this.a = a;
        this.b = b;
        assert ( a != b );
    }

    @Override
    public int hashCode() {
        return a.hashCode() + b.hashCode();
    }

    @Override
    public boolean equals( Object ob ) {
        if ( ob instanceof Bond/*, James Bond*/ ) {
            Bond other = (Bond) ob;
            return ( this.a == other.a && this.b == other.b ) || ( this.a == other.b && this.b == other.a );
        }
        else {
            return false;
        }
    }

    public boolean contains( T atom ) {
        return atom == a || atom == b;
    }

    public T getOtherAtom( T atom ) {
        assert ( contains( atom ) );
        return ( a == atom ? b : a );
    }

    public String toSerial2( int index ) {
        return String.valueOf( index );
    }
}
