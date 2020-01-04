
package com.aimxcel.abclearn.common.aimxcelcommon.model.event;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;


public interface INotifier<T> {

    public void addListener( VoidFunction1<? super T> listener );

    // add the listener, and if fireOnAdd is true, call listener.update()
    public void addUpdateListener( UpdateListener listener, boolean fireOnAdd );

    public void removeListener( VoidFunction1<? super T> listener );
}
