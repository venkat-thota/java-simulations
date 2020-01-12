package com.aimxcel.abclearn.greenhouse.filter;
public class IrFilter extends Filter1D {
    public IrFilter() {
    }

    public boolean passes( double value ) {
        return value < 800E-9 || value > 1500E-9;
    }
}
