// Copyright 2002-2011, University of Colorado
package edu.colorado.phet.theramp.v2.view;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication;

public class TestRamp extends CoreAimxcelApplication {
    public TestRamp( AimxcelApplicationConfig config ) {
        super( config );
        addModule( new TestRampModule() );
    }

    public static void main( String[] args ) {
        new AimxcelApplicationLauncher().launchSim( args, "the-ramp", TestRamp.class );
    }
}
