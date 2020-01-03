
package com.aimxcel.abclearn.common.abclearncommon.model.event;

import com.aimxcel.abclearn.common.abclearncommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.abclearncommon.util.function.VoidFunction1;

/**
 * A simple observer that can be passed to a notifier, and will update when the notifier fires
 *
 * @author Jonathan Olson
 */
public abstract class UpdateListener implements SimpleObserver, VoidFunction1<Object> {
    public void apply( Object o ) {
        update();
    }
}
