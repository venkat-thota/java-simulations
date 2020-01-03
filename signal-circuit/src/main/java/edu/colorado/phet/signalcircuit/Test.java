package edu.colorado.phet.signalcircuit;

import com.aimxcel.abclearn.common.abclearncommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationLauncher;

import edu.colorado.phet.signalcircuit.SignalCircuitApplication.SignalCircuitApplicationConfig;

public class Test {

	public static void main(String[] args) {
		System.out.println("THIS IS TEST CLASS MAIN METHOD...");
		 SignalCircuitApplicationConfig applicationConfig = new SignalCircuitApplicationConfig( args );
	        new AbcLearnApplicationLauncher().launchSim( applicationConfig, new ApplicationConstructor() {
	            public AbcLearnApplication getApplication( AbcLearnApplicationConfig config ) {
	                return new SignalCircuitApplication( config );
	            }
	        } );
	}

}
