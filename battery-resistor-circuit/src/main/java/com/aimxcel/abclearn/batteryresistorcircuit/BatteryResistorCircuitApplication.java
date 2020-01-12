package com.aimxcel.abclearn.batteryresistorcircuit;

import java.awt.*;
import java.io.IOException;

import com.aimxcel.abclearn.common.aimxcelcommon.application.*;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLookAndFeel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.FrameSetup;

public class BatteryResistorCircuitApplication extends AimxcelApplication {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BatteryResistorCircuitModule module;

    public BatteryResistorCircuitApplication( AimxcelApplicationConfig config ) {
        super( config );
        module = new BatteryResistorCircuitModule( config );
        addModule( module );
    }

    public static class BatteryResistorCircuitApplicationConfig extends AimxcelApplicationConfig {
        public BatteryResistorCircuitApplicationConfig( String[] commandLineArgs ) {
            super( commandLineArgs, "battery-resistor-circuit" );
            super.setLookAndFeel( new AimxcelLookAndFeel() );
            setFrameSetup( new FrameSetup.CenteredWithSize( BatteryResistorCircuitSimulationPanel.BASE_FRAME_WIDTH, 660 ) );
        }
    }

    private class BatteryResistorCircuitModule extends Module {
        public BatteryResistorCircuitModule( AimxcelApplicationConfig config ) {
            super( config.getName(), new ConstantDtClock( 30, 1 ) );
            BatteryResistorCircuitSimulationPanel simulationPanel = new BatteryResistorCircuitSimulationPanel( getClock() );
            try {
                simulationPanel.startApplication();
            }
            catch( IOException e ) {
                e.printStackTrace();
            }
            catch( FontFormatException e ) {
                e.printStackTrace();
            }
            setSimulationPanel( simulationPanel );
            setClockControlPanel( null );
            setLogoPanelVisible( false );
        }
    }

    public static void main( String[] args ) {

        BatteryResistorCircuitApplicationConfig dApplicationConfig = new BatteryResistorCircuitApplicationConfig( args );
        new AimxcelApplicationLauncher().launchSim( dApplicationConfig, new ApplicationConstructor() {
            public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
                return new BatteryResistorCircuitApplication( config );
            }
        } );
    }
}
