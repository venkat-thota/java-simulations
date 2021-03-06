
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

public class CompositeProperty<T> extends ObservableProperty<T> {
    //Function for computing the new value, usually provided as a closure in the implementing class
    private Function0<T> function;
    private SimpleObserver observer;
    private final ObservableProperty<?>[] properties;

    public CompositeProperty( Function0<T> function, ObservableProperty<?>... properties ) {
        super( function.apply() );//sets the oldValue to be the current value
        this.function = function;
        this.properties = properties;

        //Observe all dependencies for changes, and if one of their changes causes this value to change, send out a notification
        observer = new SimpleObserver() {
            public void update() {
                notifyIfChanged();
            }
        };
        for ( ObservableProperty<?> property : properties ) {
            property.addObserver( observer );
        }
    }

    //Remove listeners that were attached to the dependencies to prevent potential memory leaks
    public void cleanup() {
        for ( ObservableProperty<?> property : properties ) {
            property.removeObserver( observer );
        }
    }

    //Get the composite value which is a function of the dependencies
    @Override public T get() {
        return function.apply();
    }
}
