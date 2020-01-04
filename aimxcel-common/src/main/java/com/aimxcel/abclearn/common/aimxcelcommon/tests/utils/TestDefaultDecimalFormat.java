

/*  */
package com.aimxcel.abclearn.common.aimxcelcommon.tests.utils;

import com.aimxcel.abclearn.common.aimxcelcommon.util.DefaultDecimalFormat;



public class TestDefaultDecimalFormat {
    public static void main( String[] args ) {
        System.out.println( new DefaultDecimalFormat( "0.00" ).format( -0.00 ) );
    }
}
