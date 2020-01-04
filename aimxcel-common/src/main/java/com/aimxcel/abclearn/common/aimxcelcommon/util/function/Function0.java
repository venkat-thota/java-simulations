
package com.aimxcel.abclearn.common.aimxcelcommon.util.function;

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
