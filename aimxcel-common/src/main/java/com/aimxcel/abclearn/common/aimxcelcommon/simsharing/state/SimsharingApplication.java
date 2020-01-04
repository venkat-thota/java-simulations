
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.state;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;


public interface SimsharingApplication<T> {
    public AimxcelFrame getAimxcelFrame();

    public void setTeacherMode( boolean b );

    public void setExitStrategy( VoidFunction0 exitStrategy );

    T getState();

    void setState( T state );

    void addModelSteppedListener( VoidFunction0 updateSharing );

    boolean isPaused();

    void setPlayButtonPressed( boolean b );
}
