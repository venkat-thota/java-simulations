
package com.aimxcel.abclearn.common.aimxcelcommon.math;

import java.awt.geom.Point2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.DecimalFormat;


public class SerializablePoint2D extends Point2D.Double implements Externalizable {
    public SerializablePoint2D() {
        super();
    }

    public SerializablePoint2D( double x, double y ) {
        super( x, y );
    }

    public SerializablePoint2D( Point2D pt ) {
        this( pt.getX(), pt.getY() );
    }

    public static final DecimalFormat format = new DecimalFormat( "0.00" );

    //Provide a useful debugging string for our units
    public String toString() {
        return "(" + format.format( getX() ) + ", " + format.format( getY() ) + ")";
    }

    public void readExternal( ObjectInput in ) throws IOException, ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
    }

    public void writeExternal( ObjectOutput out ) throws IOException {
        out.writeDouble( x );
        out.writeDouble( y );
    }
}
