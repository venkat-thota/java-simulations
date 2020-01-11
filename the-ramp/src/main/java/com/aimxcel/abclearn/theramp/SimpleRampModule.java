
package com.aimxcel.abclearn.theramp;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.theramp.view.RampPanel;


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
