
package com.aimxcel.abclearn.common.aimxcelcommon.math;

import java.util.ArrayList;

public class DoubleSeries {
    private ArrayList data = new ArrayList();
    private int maxSize;

    public DoubleSeries( int maxSize ) {
        this.maxSize = maxSize;
    }

    public void add( double value ) {
        data.add( new Double( value ) );
        if ( data.size() > maxSize ) {
            data.remove( 0 );
        }
    }

    public double average() {
        return sum() / getSampleCount();
    }

    public double getSampleCount() {
        return data.size();
    }

    private double sum() {
        double sum = 0;
        for ( int i = 0; i < data.size(); i++ ) {
            sum += ( (Double) data.get( i ) ).doubleValue();
        }
        return sum;
    }

    public void clear() {
        data.clear();
    }
}
