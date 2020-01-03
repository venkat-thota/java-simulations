
package edu.colorado.phet.common.phetgraphics.application;

import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;

/**
 * AbcLearngraphics module with sim-sharing messages for tab presses.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SimSharingAbcLearnGraphicsModule extends AbcLearnGraphicsModule {

    private final IUserComponent tabUserComponent;

    public SimSharingAbcLearnGraphicsModule( IUserComponent tabUserComponent, String name ) {
        super( name );
        this.tabUserComponent = tabUserComponent;
    }

    protected SimSharingAbcLearnGraphicsModule( IUserComponent tabUserComponent, String name, IClock clock ) {
        super( name, clock );
        this.tabUserComponent = tabUserComponent;
    }

    //Used in Tab node code for sim sharing, to have a good ID for the tab associated with this module.
    @Override public IUserComponent getTabUserComponent() {
        return tabUserComponent;
    }
}
