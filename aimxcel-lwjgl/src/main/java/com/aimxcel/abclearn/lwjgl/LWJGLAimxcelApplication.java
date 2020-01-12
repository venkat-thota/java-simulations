package com.aimxcel.abclearn.lwjgl;

import apple.dts.samplecode.osxadapter.OSXAdapter;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.util.AimxcelUtilities;
public class LWJGLAimxcelApplication extends AimxcelApplication {

    public LWJGLAimxcelApplication( AimxcelApplicationConfig config ) {
        super( config );
        registerForMacOSXEvents();
    }

    // Generic registration with the Mac OS X application menu
    // Checks the platform, then attempts to register with the Apple EAWT
    // See OSXAdapter.java to see how this is done without directly referencing any Apple APIs
    public void registerForMacOSXEvents() {
        if ( AimxcelUtilities.isMacintosh() ) {
            try {
                // Generate and register the OSXAdapter, passing it a hash of all the methods we wish to
                // use as delegates for various com.apple.eawt.ApplicationListener methods.
                // NOTE: This uses reflection and requires security privileges, so use only in signed applications.
                OSXAdapter.setQuitHandler( this, LWJGLAimxcelApplication.class.getDeclaredMethod( "macQuit", (Class[]) null ) );
            }
            catch( Exception e ) {
                System.err.println( "Error while loading the OSXAdapter:" );
                e.printStackTrace();
            }
        }
    }

    /**
     * General quit handler; fed to the OSXAdapter as the method to call when a system quit event occurs
     * A quit event is triggered by Cmd-Q, selecting Quit from the application or Dock menu, or logging out.
     * This will be called via reflection by OSXAdapter, so IDEs may identify this as an unused method.
     * <p/>
     * NOTE: This method is accessed by reflection (as needed by OSXAdapter), so DO NOT DELETE IT
     *
     * @return Always true
     */
    public boolean macQuit() {
        exit();
        return true; // yes, we really want to quit
    }
}
