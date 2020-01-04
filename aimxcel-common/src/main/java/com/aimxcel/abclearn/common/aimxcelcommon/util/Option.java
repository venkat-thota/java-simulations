
package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.util.ArrayList;
import java.util.Iterator;

import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;

public abstract class Option<T> implements Iterable<T> {
    public abstract T get();

    public abstract boolean isSome();

    public boolean isNone() {
        return !isSome();
    }

    //if Some, gets the option value, otherwise gets the elseValue
    public T getOrElse( T elseValue ) {
        return isSome() ? get() : elseValue;
    }

    public static class None<T> extends Option<T> {
        public T get() {
            throw new UnsupportedOperationException( "Cannot get value on none." );
        }

        public boolean isSome() {
            return false;
        }

        @Override public String toString() {
            return "None";
        }

        public Iterator<T> iterator() {
            return new ArrayList<T>().iterator();
        }

        @Override public boolean equals( Object obj ) {
            return obj instanceof None;
        }

    }

    public static class Some<T> extends Option<T> {
        private final T value;

        public Some( T value ) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public boolean isSome() {
            return true;
        }

        @Override public String toString() {
            return value.toString();
        }

        public Iterator<T> iterator() {
            return new ArrayList<T>() {{
                add( value );
            }}.iterator();
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) { return true; }
            if ( o == null || getClass() != o.getClass() ) { return false; }

            Some some = (Some) o;

            if ( value != null ? !value.equals( some.value ) : some.value != null ) { return false; }

            return true;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }
}
