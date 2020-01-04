

package com.aimxcel.abclearn.common.aimxcelcommon.tests.utils;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MedianFilter;


public class TestMedianFilter {
    public static void main( String[] args ) {
        double[] d = new double[] { 9, 8, 7, 6, 5, 6, 7, 4, 5, 8, 9 };
        MedianFilter mf = new MedianFilter( d );
        d = mf.filter( 3 );
        for ( int i = 0; i < d.length; i++ ) {
            System.out.println( d[i] );
        }
    }
}