
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AWTSplashWindow;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.aimxcelcommon.application.KSUCreditsWindow;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.sponsorship.SponsorDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.sponsorship.SponsorMenuItem;
import com.aimxcel.abclearn.common.aimxcelcommon.statistics.StatisticsManager;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.AutomaticUpdatesManager;
import com.aimxcel.abclearn.common.aimxcelcommon.updates.ManualUpdatesManager;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;

public class AimxcelApplicationLauncher {

    //for splash window
    private AWTSplashWindow splashWindow;
    private Frame splashWindowOwner;

    private void showSplashWindow( String title ) {
        if ( splashWindow == null ) {
            // AimxcelFrame doesn't exist when this is called, so create and manage the window's owner.
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

    public void launchSim( String[] commandLineArgs, String project, final Class aimxcelApplicationClass ) {
        launchSim( commandLineArgs, project, new ReflectionApplicationConstructor( aimxcelApplicationClass ) );
    }

    public static class ReflectionApplicationConstructor implements ApplicationConstructor {
        private Class aimxcelApplicationClass;

        public ReflectionApplicationConstructor( Class aimxcelApplicationClass ) {
            this.aimxcelApplicationClass = aimxcelApplicationClass;
        }

        public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
            try {
                return (AimxcelApplication) aimxcelApplicationClass.getConstructor( new Class[] { config.getClass() } ).newInstance( new Object[] { config } );
            }
            catch ( Exception e ) {
                throw new RuntimeException( e );
            }
        }

    }

    public void launchSim( String[] commandLineArgs, String project, String flavor, final Class aimxcelApplicationClass ) {
        launchSim( commandLineArgs, project, flavor, new ReflectionApplicationConstructor( aimxcelApplicationClass ) );
    }

    public void launchSim( String[] commandLineArgs, String project, ApplicationConstructor applicationConstructor ) {
        launchSim( new AimxcelApplicationConfig( commandLineArgs, project ), applicationConstructor );
    }

    public void launchSim( String[] commandLineArgs, String project, String flavor, ApplicationConstructor applicationConstructor ) {
        launchSim( new AimxcelApplicationConfig( commandLineArgs, project, flavor ), applicationConstructor );
    }

    public void launchSim( final AimxcelApplicationConfig config, final Class aimxcelApplicationClass ) {
        launchSim( config, new ReflectionApplicationConstructor( aimxcelApplicationClass ) );
    }

    public void launchSim( final AimxcelApplicationConfig config, final ApplicationConstructor applicationConstructor ) {

        //Initializes the sim-sharing subsystem.
        //Nothing happens unless the "-study" flag is provided on the command line
        SimSharingManager.init( config );

        /*
         * Wrap the body of main in invokeAndWait, so that all initialization occurs
         * in the event dispatch thread. Sun now recommends doing all Swing init in
         * the event dispatch thread. And the Core-based tabs in TabbedModulePaneCore
         * seem to cause startup deadlock problems if they aren't initialized in the
         * event dispatch thread. Since we don't have an easy way to separate Swing and
         * non-Swing init, we're stuck doing everything in invokeAndWait.
         */
        try {

            //Use invoke and wait since many older aimxcel simulations
            //require the existence of a reference to the AimxcelApplication as soon as launchSim exits
            //
            //If/when these references have been changed/removed, we can change this to invokeLater()
            SwingUtilities.invokeAndWait( new Runnable() {
                public void run() {

                    config.getLookAndFeel().initLookAndFeel();

                    new JSpinner(); // WORKAROUND for Unfuddle #1372 (Apple bug #6710919)

                    if ( applicationConstructor != null ) {

                        // sim initialization
                        showSplashWindow( config.getName() );
                        final AimxcelApplication app = applicationConstructor.getApplication( config );
                        app.startApplication();
                        disposeSplashWindow();

                        // Function for displaying Sponsor dialog and adding Sponsor menu item.
                        final VoidFunction0 sponsorFunction = new VoidFunction0() {
                            public void apply() {
                                if ( SponsorDialog.shouldShow( config ) ) {
                                    SponsorDialog.show( config, app.getAimxcelFrame(), true /* startDisposeTimer */ );
                                    app.getAimxcelFrame().getHelpMenu().add( new SponsorMenuItem( config, app.getAimxcelFrame() ) );
                                }
                            }
                        };

                        // Function for displaying KSU Credits window
                        final VoidFunction0 ksuFunction = new VoidFunction0() {
                            public void apply() {
                                // Display KSU Credits window, followed by Sponsor dialog (both optional)
                                if ( KSUCreditsWindow.shouldShow( config ) ) {
                                    JWindow window = KSUCreditsWindow.show( app.getAimxcelFrame() );
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
