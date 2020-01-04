
package com.aimxcel.abclearn.common.aimxcelcommon.util.function;


public interface Function1<U, T> {
    T apply( U u );

    public static class Constant<U, T> implements Function1<U, T> {
        private final T value;

        public Constant( T value ) {
            this.value = value;
        }

        public T apply( U u ) {
            return value;
        }
    }

    /**
     * Identity function that returns its input.
     *
     * @param <T>
     */
    public static class Identity<T> implements Function1<T, T> {
        public T apply( T t ) {
            return t;
        }
    }
}
