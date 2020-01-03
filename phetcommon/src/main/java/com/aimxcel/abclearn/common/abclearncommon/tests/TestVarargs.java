package com.aimxcel.abclearn.common.abclearncommon.tests;

import com.aimxcel.abclearn.common.abclearncommon.util.Option;
import com.aimxcel.abclearn.common.abclearncommon.util.Option.None;
import com.aimxcel.abclearn.common.abclearncommon.util.Option.Some;

/**
 * Test application for generics warnings, see #2996
 *
 * @author Sam Reid
 */
public class TestVarargs {
    public void combine( Option<Integer>... valueLists ) {
    }

    public static void main( String[] args ) {
        new TestVarargs().combine( new Some<Integer>( 3 ), new Some<Integer>( 4 ), new None<Integer>() );
    }
}
