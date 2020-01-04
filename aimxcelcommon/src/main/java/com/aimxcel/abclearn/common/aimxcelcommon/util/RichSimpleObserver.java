
package com.aimxcel.abclearn.common.aimxcelcommon.util;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;

/**
 * Adds convenience functionality to SimpleObserver.  Right now it just makes it more convenient to listen to (or ignore) multiple ObservableProperties.
 *
 * @author Sam Reid
 */
public abstract class RichSimpleObserver implements SimpleObserver {
    public abstract void update();

    /**
     * Add this instance as an observer of the specified Property<T> arguments.
     *
     * @param properties the properties to observe.
     */
    public void observe( ObservableProperty... properties ) {
        for ( ObservableProperty property : properties ) {
            property.addObserver( this );
        }
    }

    /**
     * Remove this instance as an observer of the specified Property<T> arguments.
     *
     * @param properties the properties to stop observing.
     */
    public void unobserve( ObservableProperty... properties ) {
        for ( ObservableProperty property : properties ) {
            property.removeObserver( this );
        }
    }
}
