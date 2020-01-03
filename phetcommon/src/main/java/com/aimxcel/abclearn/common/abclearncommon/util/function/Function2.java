
package com.aimxcel.abclearn.common.abclearncommon.util.function;

/**
 * A two-parameter function that takes argument of types V and U, and returns a value of type T.
 *
 * @author Sam Reid
 */
public interface Function2<V, U, T> {
    T apply( V v, U u );
}