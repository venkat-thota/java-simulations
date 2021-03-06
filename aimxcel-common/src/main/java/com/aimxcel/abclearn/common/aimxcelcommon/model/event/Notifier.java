
package com.aimxcel.abclearn.common.aimxcelcommon.model.event;

import java.util.List;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;


public class Notifier<T> implements INotifier<T> {
    //The wrapped AbstractNotifier used to store listeners and fire events.
    private AbstractNotifier<VoidFunction1<? super T>> notifier = new AbstractNotifier<VoidFunction1<? super T>>();

    //Signify that the value has changed, passing the T value to all listeners
    public void updateListeners( final T value ) {
        notifier.updateListeners( new VoidFunction1<VoidFunction1<? super T>>() {
            public void apply( VoidFunction1<? super T> listener ) {
                listener.apply( value );
            }
        } );
    }

    public void addListener( VoidFunction1<? super T> listener ) {
        notifier.addListener( listener );
    }

    /**
     * Allows adding listeners that don't care about the type of the argument, just that something changed.
     * This is similar to how properties are handled, but we don't want to filter out "duplicate" values.
     */
    public void addUpdateListener( UpdateListener listener, boolean fireOnAdd ) {
        notifier.addListener( listener );
        if ( fireOnAdd ) {
            listener.update();
        }
    }

    public void removeListener( VoidFunction1<? super T> listener ) {
        notifier.removeListener( listener );
    }

    public List<VoidFunction1<? super T>> getListeners() {
        return notifier.getListeners();
    }
}
