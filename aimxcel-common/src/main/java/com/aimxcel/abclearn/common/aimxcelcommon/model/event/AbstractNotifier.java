
package com.aimxcel.abclearn.common.aimxcelcommon.model.event;

import java.util.ArrayList;
import java.util.LinkedList;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;


public class AbstractNotifier<T> {//T is the listener type
    private LinkedList<T> listeners = new LinkedList<T>();

    public void addListener( T listener ) {
        listeners.add( listener );
    }

    public void removeListener( T listener ) {
        listeners.remove( listener );
    }

    //Notify the listeners that an event has occurred.  This is called 'update' instead of 'notify' to avoid clashing with java 'notify' methods.
    public void updateListeners( VoidFunction1<T> callback ) {
        for ( T listener : new ArrayList<T>( listeners ) ) {
            callback.apply( listener );
        }
    }

    public LinkedList<T> getListeners() {
        return new LinkedList<T>( listeners );
    }
}