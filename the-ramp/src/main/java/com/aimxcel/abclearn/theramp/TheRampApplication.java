
package com.aimxcel.abclearn.theramp;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication;



public class TheRampApplication extends CoreAimxcelApplication {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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