
package com.aimxcel.abclearn.conductivity.macro.circuit;

import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;

public class CompositeLinearBranch {

    public CompositeLinearBranch() {
        branches = new ArrayList();
    }

    public void addBranch( LinearBranch linearbranch ) {
        branches.add( linearbranch );
    }

    public double getLength() {
        double d = 0.0D;
        for ( int i = 0; i < branches.size(); i++ ) {
            LinearBranch linearbranch = (LinearBranch) branches.get( i );
            d += linearbranch.getLength();
        }

        return d;
    }

    public Vector2D getPosition( double d ) {
        double d1 = 0.0D;
        for ( int i = 0; i < branches.size(); i++ ) {
            LinearBranch linearbranch = (LinearBranch) branches.get( i );
            if ( linearbranch.getLength() + d1 >= d ) {
                double d2 = d - d1;
                return linearbranch.getLocation( d2 );
            }
            d1 += linearbranch.getLength();
        }

        return null;
    }

    public LinearBranch branchAt( int i ) {
        return (LinearBranch) branches.get( i );
    }

    public int numBranches() {
        return branches.size();
    }

    ArrayList branches;
}
