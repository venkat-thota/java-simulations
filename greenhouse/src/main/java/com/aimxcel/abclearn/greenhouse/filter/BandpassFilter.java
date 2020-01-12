package com.aimxcel.abclearn.greenhouse.filter;

public class BandpassFilter extends Filter1D {
    private double low;
    private double high;

    public BandpassFilter( double low, double high ) {
        this.low = low;
        this.high = high;
    }

    public boolean passes( double value ) {
        return value >= low && value <= high;
    }
}
