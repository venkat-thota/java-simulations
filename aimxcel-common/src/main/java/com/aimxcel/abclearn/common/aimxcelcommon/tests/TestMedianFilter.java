

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author:samreid $
 * Revision : $Revision:14677 $
 * Date modified : $Date:2007-04-17 03:40:29 -0500 (Tue, 17 Apr 2007) $
 */
package com.aimxcel.abclearn.common.aimxcelcommon.tests;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MedianFilter;

/**
 * User: Sam Reid
 * Date: Dec 12, 2004
 * Time: 9:17:24 PM
 */

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