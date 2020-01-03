
package com.aimxcel.abclearn.common.abclearncommon.application;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.abclearncommon.sponsorship.SponsorDialog;
import com.aimxcel.abclearn.common.abclearncommon.sponsorship.SponsorMenuItem;
import com.aimxcel.abclearn.common.abclearncommon.statistics.StatisticsManager;
import com.aimxcel.abclearn.common.abclearncommon.updates.AutomaticUpdatesManager;
import com.aimxcel.abclearn.common.abclearncommon.updates.ManualUpdatesManager;
import com.aimxcel.abclearn.common.abclearncommon.util.function.VoidFunction0;

import com.aimxcel.abclearn.common.abclearncommon.application.AWTSplashWindow;
import com.aimxcel.abclearn.common.abclearncommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.abclearncommon.application.KSUCreditsWindow;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
//import com.aimxcel.abclearn.common.abclearncommon.application.ReflectionApplicationConstructor;

/**
 * This launcher solves the following problems:
 * 1. Consolidate (instead of duplicate) launch code
 * 2. Make sure that all AbcLearnSimulations launch in the Swing Event Thread
 * Note: The application main class should not invoke any unsafe Swing operations outside of the Swing thread.
 * 3. Make sure all AbcLearnSimulations instantiate and use a AbcLearnLookAndFeel, which is necessary to enable font support for many laungages.
 * <p/>
 * This implementation uses ApplicationConstructor instead of reflection to ensure compile-time checking (at the expense of slightly more complicated subclass implementations).
 */
public class AbcLearnApplicationLauncher {

    //for splash window
    private AWTSplashWindow splashWindow;
    private Frame splashWindowOwner;

    private void showSplashWindow( String title ) {
        if ( splashWindow == null ) {
            // AbcLearnFrame doesn't exist when this is called, so create and manage the window's owner.
            splashWindowOwner = new Frame();
            splashWindow = new AWTSplashWindow( splashWindowOwner, title );
            splashWindow.setVisible( true );
        }
    }

    private void disposeSplashWindow() {
        if ( splashWindow != null ) {
            splashWindow.dispose();
            splashWindow = null;
            // Clean up the window's owner that we created in showSplashWindow.
            splashWindowOwner.dispose();
            splashWindowOwner = null;
        }
    }

    public void launchSim( String[] commandLineArgs, String project, final Class phetApplicationClass ) {
        launchSim( commandLineArgs, project, new ReflectionApplicationConstructor( phetApplicationClass ) );
    }

    public static class ReflectionApplicationConstructor implements ApplicationConstructor {
        private Class phetApplicationClass;

        public ReflectionApplicationConstructor( Class phetApplicationClass ) {
            this.phetApplicationClass = phetApplicationClass;
        }

        public AbcLearnApplication getApplication( AbcLearnApplicationConfig config ) {
            try {
                return (AbcLearnApplication) phetApplicationClass.getConstructor( new Class[] { config.getClass() } ).newInstance( new Object[] { config } );
            }
            catch ( Exception e ) {
                throw new RuntimeException( e );
            }
        }

    }

    public void launchSim( String[] commandLineArgs, String project, String flavor, final Class phetApplicationClass ) {
        launchSim( commandLineArgs, project, flavor, new ReflectionApplicationConstructor( phetApplicationClass ) );
    }

    public void launchSim( String[] commandLineArgs, String project, ApplicationConstructor applicationConstructor ) {
        launchSim( new AbcLearnApplicationConfig( commandLineArgs, project ), applicationConstructor );
    }

    public void launchSim( String[] commandLineArgs, String project, String flavor, ApplicationConstructor applicationConstructor ) {
        launchSim( new AbcLearnApplicationConfig( commandLineArgs, project, flavor ), applicationConstructor );
    }

    public void launchSim( final AbcLearnApplicationConfig config, final Class phetApplicationClass ) {
        launchSim( config, new ReflectionApplicationConstructor( phetApplicationClass ) );
    }

    public void launchSim( final AbcLearnApplicationConfig config, final ApplicationConstructor applicationConstructor ) {

        //Initializes the sim-sharing subsystem.
        //Nothing happens unless the "-study" flag is provided on the command line
        SimSharingManager.init( config );

        /*
         * Wrap the body of main in invokeAndWait, so that all initialization occurs
         * in the event dispatch thread. Sun now recommends doing all Swing init in
         * the event dispatch thread. And the Piccolo-based tabs in TabbedModulePanePiccolo
         * seem to cause startup deadlock problems if they aren't initialized in the
         * event dispatch thread. Since we don't have an easy way to separate Swing and
         * non-Swing init, we're stuck doing everything in invokeAndWait.
         */
        try {

            //Use invoke and wait since many older PhET simulations
            //require the existence of a reference to the AbcLearnApplication as soon as launchSim exits
            //
            //If/when these references have been changed/removed, we can change this to invokeLater()
            SwingUtilities.invokeAndWait( new Runnable() {
                public void run() {

                    config.getLookAndFeel().initLookAndFeel();

                    new JSpinner(); // WORKAROUND for Unfuddle #1372 (Apple bug #6710919)

                    if ( applicationConstructor != null ) {

                        // sim initialization
                        showSplashWindow( config.getName() );
                        final AbcLearnApplication app = applicationConstructor.getApplication( config );
                        app.startApplication();
                        disposeSplashWindow();

                        // Function for displaying Sponsor dialog and adding Sponsor menu item.
                        final VoidFunction0 sponsorFunction = new VoidFunction0() {
                            public void apply() {
                                if ( SponsorDialog.shouldShow( config ) ) {
                                    SponsorDialog.show( config, app.getAbcLearnFrame(), true /* startDisposeTimer */ );
                                    app.getAbcLearnFrame().getHelpMenu().add( new SponsorMenuItem( config, app.getAbcLearnFrame() ) );
                                }
                            }
                        };

                        // Function for displaying KSU Credits window
                        final VoidFunction0 ksuFunction = new VoidFunction0() {
                            public void apply() {
                                // Display KSU Credits window, followed by Sponsor dialog (both optional)
                                if ( KSUCreditsWindow.shouldShow( config ) ) {
                                    JWindow window = KSUCreditsWindow.show( app.getAbcLearnFrame() );
                                    // wait until KSU Credits window is closed before calling sponsor function
                                    window.addWindowListener( new WindowAdapter() {
                                        @Override public void windowClosed( WindowEvent e ) {
                                            sponsorFunction.apply();
                                        }
                                    } );
                                }
                                else {
                                    // No KSU Credits window, call sponsor function
                                    sponsorFunction.apply();
                                }
                            }
                        };

                        // Start with "KSU" window
                        ksuFunction.apply();

                        //Ignore statistics and updates for sims that are still under development
                        if ( app.getSimInfo().getVersion().getMajorAsInt() >= 1 ) {
                            // statistics
                            StatisticsManager.initInstance( app ).start();

                            // updates
                            AutomaticUpdatesManager.initInstance( app ).start();
                            ManualUpdatesManager.initInstance( app );
                        }
                    }
                    else {
                        new RuntimeException( "No applicationconstructor specified" ).printStackTrace();
                    }
                }
            } );
        }
        catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        catch ( InvocationTargetException e ) {
            e.printStackTrace();
        }
    }
}
