
package com.aimxcel.abclearn.common.abclearncommon.simsharing.state;

import com.aimxcel.abclearn.common.abclearncommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnFrame;

/**
 * @author Sam Reid
 */
public interface SimsharingApplication<T> {
    public AbcLearnFrame getAbcLearnFrame();

    public void setTeacherMode( boolean b );

    public void setExitStrategy( VoidFunction0 exitStrategy );

    T getState();

    void setState( T state );

    void addModelSteppedListener( VoidFunction0 updateSharing );

    boolean isPaused();

    void setPlayButtonPressed( boolean b );
}
