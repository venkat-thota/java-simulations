
package com.aimxcel.abclearn.common.abclearncommon.model.event;

/**
 * A notifier where the parameter for updating is removed. Instead, it will always
 * fire with the current value.
 */
public class ValueNotifier<T> extends Notifier<T> {
    private T value;

    public ValueNotifier( T value ) {
        this.value = value;
    }

    public void updateListeners() {
        updateListeners( value );
    }

    public T getValue() {
        return value;
    }

    public void setValue( T value ) {
        this.value = value;
    }
}
