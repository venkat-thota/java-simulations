
package com.aimxcel.abclearn.signalcircuit;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.FrameSetup;
import com.aimxcel.abclearn.core.aimxcelcore.PiccoloAimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;

public class SignalCircuitApplication extends PiccoloAimxcelApplication {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SignalCircuitApplication( AimxcelApplicationConfig config ) {
        super( config );
        SignalCircuitModule module = new SignalCircuitModule( config );
        addModule( module );
    }

    public static class SignalCircuitApplicationConfig extends AimxcelApplicationConfig {
        public SignalCircuitApplicationConfig( String[] commandLineArgs ) {
            super( commandLineArgs, "signal-circuit" );
            setFrameSetup( new FrameSetup.CenteredWithSize( 720,500) );
        }
    }

    private class SignalCircuitModule extends Module {

        public SignalCircuitModule( AimxcelApplicationConfig config ) {
            super( config.getName(), new ConstantDtClock( 22, 0.0216 ) );
            SignalCircuitSimulationPanel simulationPanel = new SignalCircuitSimulationPanel( getClock() );
            setSimulationPanel( simulationPanel );
            setClockControlPanel( null );
            setLogoPanelVisible( false );
        }

    }

    public static void main( String[] args ) {
        SignalCircuitApplicationConfig applicationConfig = new SignalCircuitApplicationConfig( args );
        new AimxcelApplicationLauncher().launchSim( applicationConfig, new ApplicationConstructor() {
            public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
                return new SignalCircuitApplication( config );
            }
        } );
    }
}
