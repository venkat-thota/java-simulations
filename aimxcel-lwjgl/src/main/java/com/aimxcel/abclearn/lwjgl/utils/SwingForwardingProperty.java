package com.aimxcel.abclearn.lwjgl.utils;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ChangeObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;

public class SwingForwardingProperty<T> extends Property<T> {
    public SwingForwardingProperty( final Property<T> property ) {
        super( property.get() );

        addObserver( new ChangeObserver<T>() {
            public void update( final T newValue, T oldValue ) {
                LWJGLUtils.invoke( new Runnable() {
                    public void run() {
                        property.set( newValue );
                    }
                } );
            }
        } );
    }
}
