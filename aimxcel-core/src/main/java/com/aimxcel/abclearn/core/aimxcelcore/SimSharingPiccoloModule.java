
package com.aimxcel.abclearn.core.aimxcelcore;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;

public class SimSharingPiccoloModule extends PiccoloModule {

    private final IUserComponent tabUserComponent;

    public SimSharingPiccoloModule( IUserComponent tabUserComponent, String name, IClock clock ) {
        super( name, clock );
        this.tabUserComponent = tabUserComponent;
    }

    public SimSharingPiccoloModule( IUserComponent tabUserComponent, String name, IClock clock, boolean startsPaused ) {
        super( name, clock, startsPaused );
        this.tabUserComponent = tabUserComponent;
    }

    //Used in Tab node code for sim sharing, to have a good ID for the tab associated with this module.
    @Override public IUserComponent getTabUserComponent() {
        return tabUserComponent;
    }
}