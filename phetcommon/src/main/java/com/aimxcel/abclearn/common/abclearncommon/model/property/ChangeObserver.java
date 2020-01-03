
package com.aimxcel.abclearn.common.abclearncommon.model.property;

/**
 * Observer that is notified about both the old and new values.
 *
 * @author Sam Reid
 */
public interface ChangeObserver<T> {
    void update( T newValue, T oldValue );
}
