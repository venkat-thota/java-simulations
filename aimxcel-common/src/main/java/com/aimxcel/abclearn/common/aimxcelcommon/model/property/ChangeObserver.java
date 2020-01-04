
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;


public interface ChangeObserver<T> {
    void update( T newValue, T oldValue );
}
