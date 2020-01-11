// Copyright 2002-2011, University of Colorado

/*  */
package edu.colorado.phet.theramp;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import edu.colorado.phet.theramp.view.RampPanel;

/**
 * User: Sam Reid
 * Date: Feb 11, 2005
 * Time: 9:57:09 AM
 */

public class SimpleRampModule extends RampModule {
    public SimpleRampModule( AimxcelFrame phetFrame, IClock clock ) {
        super( TheRampStrings.getString( "module.introduction" ), phetFrame, clock );
    }

    protected RampControlPanel createRampControlPanel() {
        return new SimpleRampControlPanel( this );
    }

    protected RampPanel createRampPanel() {
        return new SimpleRampPanel( this );
    }

}
