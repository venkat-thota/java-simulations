package com.aimxcel.abclearn.signalcircuit;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.signalcircuit.SignalCircuitApplication.SignalCircuitApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;

public class Test {

	public static void main(String[] args) {
		System.out.println("THIS IS TEST CLASS MAIN METHOD...");
		 SignalCircuitApplicationConfig applicationConfig = new SignalCircuitApplicationConfig( args );
	        new AimxcelApplicationLauncher().launchSim( applicationConfig, new ApplicationConstructor() {
	            public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
	                return new SignalCircuitApplication( config );
	            }
	        } );
	}

}
