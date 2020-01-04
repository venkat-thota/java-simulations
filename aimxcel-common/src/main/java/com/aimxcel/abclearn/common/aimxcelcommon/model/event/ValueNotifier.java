
package com.aimxcel.abclearn.common.aimxcelcommon.model.event;

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
