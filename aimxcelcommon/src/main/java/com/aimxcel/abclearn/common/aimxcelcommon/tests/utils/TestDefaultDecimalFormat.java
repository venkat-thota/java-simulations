

/*  */
package com.aimxcel.abclearn.common.aimxcelcommon.tests.utils;

import com.aimxcel.abclearn.common.aimxcelcommon.util.DefaultDecimalFormat;

/**
 * User: Sam Reid
 * Date: Jun 8, 2006
 * Time: 1:48:42 PM
 */

public class TestDefaultDecimalFormat {
    public static void main( String[] args ) {
        System.out.println( new DefaultDecimalFormat( "0.00" ).format( -0.00 ) );
    }
}
