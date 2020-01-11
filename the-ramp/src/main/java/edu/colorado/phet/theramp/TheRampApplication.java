// Copyright 2002-2011, University of Colorado

/*  */
package edu.colorado.phet.theramp;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication;

/**
 * User: Sam Reid
 * Date: Feb 11, 2005
 * Time: 9:57:09 AM
 */

public class TheRampApplication extends CoreAimxcelApplication {

    private final RampModule simpleRampModule;
    private final RampModule advancedFeatureModule;

    public TheRampApplication( AimxcelApplicationConfig config ) {
        super( config );
        simpleRampModule = new SimpleRampModule( getAimxcelFrame(), createClock() );
        advancedFeatureModule = new RampModule( getAimxcelFrame(), createClock() );
        setModules( new Module[] { simpleRampModule, advancedFeatureModule } );
    }

    private IClock createClock() {
        return new SwingClock( 30, 1.0 / 30.0 );
    }

    public void startApplication() {
        super.startApplication();
        simpleRampModule.getAimxcelPCanvas().requestFocus();
        simpleRampModule.applicationStarted();
    }

    public static void main( final String[] args ) {
        new AimxcelApplicationLauncher().launchSim( args, TheRampConstants.PROJECT_NAME, TheRampApplication.class );
    }
}