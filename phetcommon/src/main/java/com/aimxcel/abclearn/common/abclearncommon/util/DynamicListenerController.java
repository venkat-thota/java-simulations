
package com.aimxcel.abclearn.common.abclearncommon.util;

import java.util.Collection;

public interface DynamicListenerController {
    void addListener( Object listener ) throws IllegalStateException;

    void removeListener( Object listener );

    Collection getAllListeners();
}
