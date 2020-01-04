
package com.aimxcel.abclearn.common.aimxcelcommon.util.function;

public interface Function3<W, V, U, T> {
    T apply( W w, V v, U u );

    public static class Constant<W, V, U, T> implements Function3<W, V, U, T> {

        private T value;

        public Constant( T value ) {
            this.value = value;
        }

        public T apply( W w, V v, U u ) {
            return value;
        }
    }
}