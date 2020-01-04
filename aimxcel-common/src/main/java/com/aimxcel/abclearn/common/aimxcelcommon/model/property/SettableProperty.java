

package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;

public abstract class SettableProperty<T> extends ObservableProperty<T> {

    public SettableProperty( T oldValue ) {
        super( oldValue );
    }

    public abstract void set( T value );
}