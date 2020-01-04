
package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.util.Collection;

public interface DynamicListenerController {
    void addListener( Object listener ) throws IllegalStateException;

    void removeListener( Object listener );

    Collection getAllListeners();
}
