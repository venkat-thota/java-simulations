
package com.aimxcel.abclearn.common.aimxcelcommon.util.function;


public interface VoidFunction2<U, T> {
    void apply( U u, T t );

    public static class Null<U, T> implements VoidFunction2<U, T> {
        public void apply( U u, T t ) {
        }
    }
}
