package com.aimxcel.abclearn.greenhouse.filter;

public abstract class Filter1D {

    public abstract boolean passes( double value );

    public boolean absorbs( double value ) {
        return !passes( value );
    }
}
