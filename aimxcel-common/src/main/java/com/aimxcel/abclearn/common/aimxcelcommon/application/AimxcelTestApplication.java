
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import java.util.ArrayList;
import java.util.Arrays;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.FrameSetup;

public class AimxcelTestApplication {

    private String[] args;
    private FrameSetup frameSetup;
    private ApplicationConstructor appConstructor;
    private ArrayList modules = new ArrayList();

    public AimxcelTestApplication( String[] args ) {
        this( args, null );
    }

    public AimxcelTestApplication( String[] args, FrameSetup frameSetup ) {
        this.args = processArgs( args );
        this.frameSetup = frameSetup;
    }

    private String[] processArgs( String[] args ) {
        ArrayList list = new ArrayList( Arrays.asList( args ) );
        list.add( "-statistics-off" );
        list.add( "-updates-off" );
        return (String[]) list.toArray( new String[list.size()] );
    }

    public void addModule( Module module ) {
        modules.add( module );
    }

    public void setApplicationConstructor( ApplicationConstructor appConstructor ) {
        this.appConstructor = appConstructor;
    }

    public void startApplication() {

        AimxcelApplicationConfig config = new AimxcelApplicationConfig( args, "aimxcelcommon" );
        if ( frameSetup != null ) {
            config.setFrameSetup( frameSetup );
        }

        if ( appConstructor == null ) {
            appConstructor = new ApplicationConstructor() {
                public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
                    AimxcelApplication aimxcelApplication = new AimxcelApplication( config ) {
                    };
                    aimxcelApplication.setModules( (Module[]) modules.toArray( new Module[modules.size()] ) );
                    return aimxcelApplication;
                }
            };
        }

        new AimxcelApplicationLauncher().launchSim( config, appConstructor );
    }

    public void setModules( Module[] m ) {
        modules.addAll( Arrays.asList( m ) );
    }

    public AimxcelFrame getAimxcelFrame() {
        return AimxcelApplication.getInstance().getAimxcelFrame();
    }

}
