
package com.aimxcel.abclearn.common.abclearncommon.application;

import java.util.ArrayList;
import java.util.Arrays;

import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnFrame;
import com.aimxcel.abclearn.common.abclearncommon.view.util.FrameSetup;

import com.aimxcel.abclearn.common.abclearncommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationLauncher;

public class AbcLearnTestApplication {

    private String[] args;
    private FrameSetup frameSetup;
    private ApplicationConstructor appConstructor;
    private ArrayList modules = new ArrayList();

    public AbcLearnTestApplication( String[] args ) {
        this( args, null );
    }

    public AbcLearnTestApplication( String[] args, FrameSetup frameSetup ) {
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

        AbcLearnApplicationConfig config = new AbcLearnApplicationConfig( args, "abcLearncommon" );
        if ( frameSetup != null ) {
            config.setFrameSetup( frameSetup );
        }

        if ( appConstructor == null ) {
            appConstructor = new ApplicationConstructor() {
                public AbcLearnApplication getApplication( AbcLearnApplicationConfig config ) {
                    AbcLearnApplication abcLearnApplication = new AbcLearnApplication( config ) {
                    };
                    abcLearnApplication.setModules( (Module[]) modules.toArray( new Module[modules.size()] ) );
                    return abcLearnApplication;
                }
            };
        }

        new AbcLearnApplicationLauncher().launchSim( config, appConstructor );
    }

    public void setModules( Module[] m ) {
        modules.addAll( Arrays.asList( m ) );
    }

    public AbcLearnFrame getAbcLearnFrame() {
        return AbcLearnApplication.getInstance().getAbcLearnFrame();
    }

}
