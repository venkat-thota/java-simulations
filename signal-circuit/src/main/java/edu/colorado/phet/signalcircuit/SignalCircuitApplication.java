
package edu.colorado.phet.signalcircuit;

import com.aimxcel.abclearn.common.abclearncommon.application.*;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.abclearncommon.view.util.FrameSetup;

import com.aimxcel.abclearn.common.abclearncommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationLauncher;
import edu.colorado.phet.common.piccolophet.PiccoloAbcLearnApplication;

public class SignalCircuitApplication extends PiccoloAbcLearnApplication {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SignalCircuitApplication( AbcLearnApplicationConfig config ) {
        super( config );
        SignalCircuitModule module = new SignalCircuitModule( config );
        addModule( module );
    }

    public static class SignalCircuitApplicationConfig extends AbcLearnApplicationConfig {
        public SignalCircuitApplicationConfig( String[] commandLineArgs ) {
            super( commandLineArgs, "signal-circuit" );
            setFrameSetup( new FrameSetup.CenteredWithSize( 720,500) );
        }
    }

    private class SignalCircuitModule extends Module {

        public SignalCircuitModule( AbcLearnApplicationConfig config ) {
            super( config.getName(), new ConstantDtClock( 22, 0.0216 ) );
            SignalCircuitSimulationPanel simulationPanel = new SignalCircuitSimulationPanel( getClock() );
            setSimulationPanel( simulationPanel );
            setClockControlPanel( null );
            setLogoPanelVisible( false );
        }

    }

    public static void main( String[] args ) {
        SignalCircuitApplicationConfig applicationConfig = new SignalCircuitApplicationConfig( args );
        new AbcLearnApplicationLauncher().launchSim( applicationConfig, new ApplicationConstructor() {
            public AbcLearnApplication getApplication( AbcLearnApplicationConfig config ) {
                return new SignalCircuitApplication( config );
            }
        } );
    }
}
