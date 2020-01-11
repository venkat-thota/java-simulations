
package com.aimxcel.abclearn.aimxcelgraphics.application;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;


public class SimSharingAimxcelGraphicsModule extends AimxcelGraphicsModule {

    private final IUserComponent tabUserComponent;

    public SimSharingAimxcelGraphicsModule( IUserComponent tabUserComponent, String name ) {
        super( name );
        this.tabUserComponent = tabUserComponent;
    }

    protected SimSharingAimxcelGraphicsModule( IUserComponent tabUserComponent, String name, IClock clock ) {
        super( name, clock );
        this.tabUserComponent = tabUserComponent;
    }

    //Used in Tab node code for sim sharing, to have a good ID for the tab associated with this module.
    @Override public IUserComponent getTabUserComponent() {
        return tabUserComponent;
    }
}
