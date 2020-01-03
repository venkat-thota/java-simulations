
package com.aimxcel.abclearn.common.abclearncommon.model;

import com.aimxcel.abclearn.common.abclearncommon.util.function.VoidFunction0;

/**
 * The 'ResetModel' provides an instance that can be listened to for 'reset' messages.
 *
 * @author Sam Reid
 */
public interface ResetModel {
    void addResetListener( VoidFunction0 resetAction );
}
