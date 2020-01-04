
package com.aimxcel.abclearn.common.aimxcelcommon.util.function;


public interface VoidFunction1<T> {
    void apply( T t );

    
    public static class Null<T> implements VoidFunction1<T> {
        public void apply( T o ) {
        }
    }
}
