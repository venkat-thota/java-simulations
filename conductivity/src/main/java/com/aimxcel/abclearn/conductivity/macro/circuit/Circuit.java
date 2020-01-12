
package com.aimxcel.abclearn.conductivity.macro.circuit;

import com.aimxcel.abclearn.conductivity.macro.battery.Battery;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;

public class Circuit {

    public Circuit( double d, double d1 ) {
        circuit = new CompositeLinearBranch();
        at = new MutableVector2D( d, d1 );
    }

    public LinearBranch wireAt( int i ) {
        return circuit.branchAt( i );
    }

    public Wire wireTo( double d, double d1 ) {
        MutableVector2D aimxcelvector = new MutableVector2D( d, d1 );
        Wire wire = new Wire( at, aimxcelvector );
        circuit.addBranch( wire );
        at = aimxcelvector;
        return wire;
    }

    public Resistor resistorTo( double d, double d1 ) {
        MutableVector2D aimxcelvector = new MutableVector2D( d, d1 );
        Resistor resistor = new Resistor( at, aimxcelvector );
        circuit.addBranch( resistor );
        at = aimxcelvector;
        return resistor;
    }

    public Battery batteryTo( double d, double d1 ) {
        MutableVector2D aimxcelvector = new MutableVector2D( d, d1 );
        Battery battery = new Battery( at, aimxcelvector );
        circuit.addBranch( battery );
        at = aimxcelvector;
        return battery;
    }

    public double getLength() {
        return circuit.getLength();
    }

    public int numBranches() {
        return circuit.numBranches();
    }

    public Vector2D getPosition( double d ) {
        return circuit.getPosition( d );
    }

    public boolean contains( double d ) {
        return d >= 0.0D && d <= getLength();
    }

    CompositeLinearBranch circuit;
    MutableVector2D at;
}
