
package com.aimxcel.abclearn.common.abclearncommon.util.function;

/**
 * A zero-parameter function that returns a value of type T.
 *
 * @author Sam Reid
 */
public interface Function0<T> {
    T apply();

    public static class Constant<T> implements Function0<T> {
        T value;

        public Constant( T value ) {
            this.value = value;
        }

        public T apply() {
            return value;
        }
    }
}
