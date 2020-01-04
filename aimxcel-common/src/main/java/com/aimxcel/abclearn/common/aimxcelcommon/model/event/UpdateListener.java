
package com.aimxcel.abclearn.common.aimxcelcommon.model.event;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;


public abstract class UpdateListener implements SimpleObserver, VoidFunction1<Object> {
    public void apply( Object o ) {
        update();
    }
}
