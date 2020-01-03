
package edu.colorado.phet.common.piccolophet;

import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;

/**
 * Adds simsharing features to tab button presses.
 *
 * @author Sam Reid
 */
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