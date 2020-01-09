// Copyright 2002-2011, University of Colorado

/**
 * Class: SoundApplication
 * Package: edu.colorado.phet.sound
 * Author: Another Guy
 * Date: Aug 3, 2004
 */
package com.aimxcel.abclearn.sound;

import com.aimxcel.abclearn.common.aimxcelcommon.application.*;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.FrameSetup;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication;


public class SoundApplication extends CoreAimxcelApplication {
    
    public SoundApplication( AimxcelApplicationConfig config ) {
        super( config );

        //TODO performance tanks when window is made bigger
        getAimxcelFrame().setResizable( false );
        
        // Set up the modules
        addModule( new SingleSourceListenModule( ) );
        addModule( new SingleSourceMeasureModule( this ) );
        addModule( new TwoSpeakerInterferenceModule() );
        addModule( new WallInterferenceModule() );
        addModule( new SingleSourceWithBoxModule() );
    }
    
    public static void main( final String[] args ) {
        
        ApplicationConstructor appConstructor = new ApplicationConstructor() {
            public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
                return new SoundApplication( config );
            }
        };
        
        AimxcelApplicationConfig appConfig = new AimxcelApplicationConfig( args, SoundConfig.PROJECT_NAME );
        appConfig.setFrameSetup( new FrameSetup.CenteredWithSize( 900, 750 ) );
        new AimxcelApplicationLauncher().launchSim( appConfig, appConstructor );
    }
}
