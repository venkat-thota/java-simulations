
package com.aimxcel.abclearn.common.abclearncommon.application;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import com.aimxcel.abclearn.common.abclearncommon.dialogs.AbcLearnAboutDialog;
import com.aimxcel.abclearn.common.abclearncommon.util.CommandLineUtils;
import com.aimxcel.abclearn.common.abclearncommon.util.IProguardKeepClass;
import com.aimxcel.abclearn.common.abclearncommon.util.Option;
import com.aimxcel.abclearn.common.abclearncommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.abclearncommon.view.ITabbedModulePane;
import com.aimxcel.abclearn.common.abclearncommon.view.JTabbedModulePane;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnExit;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnFrame;

import com.aimxcel.abclearn.common.abclearncommon.application.ISimInfo;
import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.ModuleManager;
import com.aimxcel.abclearn.common.abclearncommon.application.ModuleObserver;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;

import static java.lang.Integer.parseInt;

/**
 * The base class for ABC_LEARN applications.
 */
public class AbcLearnApplication
        implements IProguardKeepClass //since we are using reflection to call this constructor from the launcher, must direct proguard to keep it
{

    //----------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------

    public static final String DEVELOPER_CONTROLS_COMMAND_LINE_ARG = "-dev";//Command line argument to enable developer-only features
    private static ArrayList<AbcLearnApplication> abcLearnApplications = new ArrayList<AbcLearnApplication>();

    //----------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------

    private ITabbedModulePane tabbedModulePane;
    private AbcLearnApplicationConfig abcLearnApplicationConfig;

    private AbcLearnFrame abcLearnFrame;
    private ModuleManager moduleManager;
    private AbcLearnAboutDialog aboutDialog; // not null only when About dialog is shown for the first time
    private boolean applicationStarted = false;//flag to make sure we don't start the application more than once

    //The exit strategy, to be applied when the user closes the application.  By default it calls AbcLearnExit.exit
    private VoidFunction0 exitStrategy = new VoidFunction0() {
        public void apply() {
            AbcLearnExit.exit();
        }
    };
	private AbcLearnApplicationConfig ApplicationConfig;

    //----------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------

    public AbcLearnApplication( AbcLearnApplicationConfig config ) {
        this( config, new JTabbedModulePane() );
    }

    protected AbcLearnApplication( AbcLearnApplicationConfig ApplicationConfig, ITabbedModulePane tabbedPane ) {
        this.ApplicationConfig = ApplicationConfig;
        this.tabbedModulePane = tabbedPane;

        this.moduleManager = new ModuleManager( this );
        abcLearnFrame = createAbcLearnFrame();
        ApplicationConfig.getFrameSetup().initialize( abcLearnFrame );

        // Handle command line arguments
        parseArgs( ApplicationConfig.getCommandLineArgs() );

        abcLearnApplications.add( this );
    }

    //----------------------------------------------------------------
    // 
    //----------------------------------------------------------------

    public ITabbedModulePane getTabbedModulePane() {
        return tabbedModulePane;
    }

    /**
     * Are developer controls enabled?
     *
     * @return true or false
     */
    public boolean isDeveloperControlsEnabled() {
        return CommandLineUtils.contains( abcLearnApplicationConfig.getCommandLineArgs(), DEVELOPER_CONTROLS_COMMAND_LINE_ARG );
    }

    public ISimInfo getSimInfo() {
        return abcLearnApplicationConfig;
    }


    /**
     * Get the last created AbcLearnApplication.
     *
     * @return last created AbcLearnApplication.
     */
    public static AbcLearnApplication getInstance() {
        return (AbcLearnApplication) abcLearnApplications.get( abcLearnApplications.size() - 1 );
    }

    /**
     * Creates the AbcLearnFrame for the application
     * <p/>
     * Concrete subclasses implement this
     *
     * @return The AbcLearnFrame
     */
    protected AbcLearnFrame createAbcLearnFrame() {
        return new AbcLearnFrame( this );
    }

    /**
     * Processes command line arguments. May be extended by subclasses.
     *
     * @param args
     */
    protected void parseArgs( String[] args ) {
    }

    /**
     * Starts the AbcLearnApplication.
     * <p/>
     * Sets up the mechanism that sets the reference sizes of all ApparatusPanel2 instances.
     */
    public void startApplication() {
        if ( !applicationStarted ) {
            applicationStarted = true;
            if ( moduleManager.numModules() == 0 ) {
                throw new RuntimeException( "No modules in module manager" );
            }

            // Set up a mechanism that will set the reference sizes of all ApparatusPanel2 instances
            // after the AbcLearnFrame has been set to its startup size.
            // When the outer WindowAdapter gets called, the AbcLearnFrame is
            // at the proper size, but the ApparatusPanel2 has not yet gotten its resize event.
            abcLearnFrame.addWindowFocusListener( new WindowAdapter() {
                public void windowGainedFocus( WindowEvent e ) {
                    initializeModuleReferenceSizes();
                    abcLearnFrame.removeWindowFocusListener( this );
                }
            } );

            // #3395: dev feature, start with the module number specified on the command line.
            if ( isDeveloperControlsEnabled() ) {
                String startModuleNumber = abcLearnApplicationConfig.getOptionArg( "-startModule" );
                if ( startModuleNumber != null ) {
                    setStartModule( getModule( Integer.valueOf( startModuleNumber ) ) );
                }
            }

            moduleManager.setActiveModule( getStartModule() );
            abcLearnFrame.setVisible( true );

            updateLogoVisibility();
        }
        else {
            //TODO: put into standard logging framework
            System.out.println( "WARNING: AbcLearnApplication.startApplication was called more than once." );
        }
    }

    /**
     * This is supposed hides the logo panel in the control panel if there is already a logo visible in the tabbed module pane,
     * so that both aren't visible by default..
     * <p/>
     * This method appears to give the correct behavior in the test program: TestPiccoloAbcLearnApplication
     * <p/>
     * This method and functionality will be removed if is too awkward in practice (for example, too confusing to override this default).
     */
    protected void updateLogoVisibility() {
        for ( int i = 0; i < moduleManager.numModules(); i++ ) {
            if ( moduleAt( i ).isLogoPanelVisible() && abcLearnFrame.getTabbedModulePane() != null && abcLearnFrame.getTabbedModulePane().getLogoVisible() ) {
                moduleAt( i ).setLogoPanelVisible( false );
            }
        }
    }

    private void initializeModuleReferenceSizes() {
        for ( int i = 0; i < moduleManager.numModules(); i++ ) {
            ( moduleManager.moduleAt( i ) ).setReferenceSize();
        }
    }

    /**
     * Get the AbcLearnFrame for this Application.
     *
     * @return the AbcLearnFrame for this Application.
     */
    public AbcLearnFrame getAbcLearnFrame() {
        return abcLearnFrame;
    }

    //----------------------------------------------------------------
    // Module-related methods
    //----------------------------------------------------------------

    /**
     * Sets the modules in the application
     *
     * @param modules
     */
    public void setModules( Module[] modules ) {
        moduleManager.setModules( modules );
    }

    /**
     * Remove a Module from this AbcLearnApplication.
     *
     * @param module the Module to remove.
     */
    public void removeModule( Module module ) {
        moduleManager.removeModule( module );
    }

    /**
     * Add one Module to this AbcLearnApplication.
     *
     * @param module the Module to add.
     */
    public void addModule( Module module ) {
        moduleManager.addModule( module );
    }

    /**
     * Get the specified Module.
     *
     * @param i the Module index
     * @return the Module.
     */
    public Module moduleAt( int i ) {
        return moduleManager.moduleAt( i );
    }

    /**
     * Gets a module based on its index.
     * (This is a more common name for the moduleAt method.)
     *
     * @param i
     * @return the module
     */
    public Module getModule( int i ) {
        return moduleAt( i );
    }

    /**
     * Set the specified Module to be active.
     *
     * @param module the module to activate.
     */
    public void setActiveModule( Module module ) {
        moduleManager.setActiveModule( module );
    }

    /**
     * Set the specified Module to be active.
     *
     * @param i the module index to activate.
     */
    public void setActiveModule( int i ) {
        moduleManager.setActiveModule( i );
    }

    /**
     * Gets the module that will be activated on startup, by default, this is the first module added.
     * To change the default, call setStartupModule.
     * This can also be overriden in development mode, by passing the arguments "-dev" and "-module INT" where INT is the module index to start
     *
     * @return Module
     */
    public Module getStartModule() {
        Option<Integer> developmentModule = getDevelopmentModule();
        if ( developmentModule.isSome() ) {
            return moduleAt( developmentModule.get() );
        }
        else {
            return moduleManager.getStartModule();
        }
    }

    /**
     * Sets the module that will be activated on startup.
     *
     * @param module
     */
    public void setStartModule( Module module ) {
        moduleManager.setStartModule( module );
    }

    /**
     * Add a ModuleObserver to this AbcLearnApplication to observe changes in the list of Modules, and which Module is active.
     *
     * @param moduleObserver
     */
    public void addModuleObserver( ModuleObserver moduleObserver ) {
        moduleManager.addModuleObserver( moduleObserver );
    }

    /**
     * Removes the specified moduleObserver
     *
     * @param moduleObserver the ModuleObserver to unsubscribe from module events
     */
    public void removeModuleObserver( ModuleObserver moduleObserver ) {
        moduleManager.removeModuleObserver( moduleObserver );
    }

    /**
     * Get the index of the specified Module.
     *
     * @param m
     * @return the index of the specified Module.
     */
    public int indexOf( Module m ) {
        return moduleManager.indexOf( m );
    }

    /**
     * Get the number of modules.
     *
     * @return the number of modules.
     */
    public int numModules() {
        return moduleManager.numModules();
    }

    /**
     * Returns the active Module, or null if no module has been activated yet.
     *
     * @return the active Module, or null if no module has been activated yet.
     */
    public Module getActiveModule() {
        return moduleManager.getActiveModule();
    }

    /**
     * Adds modules.
     *
     * @param m the array of modules to add
     */
    public void addModules( Module[] m ) {
        for ( int i = 0; i < m.length; i++ ) {
            addModule( m[i] );
        }
    }

    /**
     * Returns all the Modules registered with this AbcLearnApplication.
     *
     * @return all the Modules registered with this AbcLearnApplication.
     */
    public Module[] getModules() {
        return moduleManager.getModules();
    }

    /**
     * Pauses the AbcLearnApplication (including any Modules that are active).
     */
    public void pause() {
        getActiveModule().deactivate();
    }

    /**
     * Resumes progress of the AbcLearnApplication (including any Modules that are active).
     */
    public void resume() {
        getActiveModule().activate();
    }

    /**
     * Displays an About Dialog for the simulation.
     */
    public void showAboutDialog() {
        if ( aboutDialog == null ) {
            aboutDialog = new AbcLearnAboutDialog( this );
            aboutDialog.addWindowListener( new WindowAdapter() {
                // called when the close button in the dialog's window dressing is clicked
                public void windowClosing( WindowEvent e ) {
                    aboutDialog.dispose();
                }

                // called by JDialog.dispose
                public void windowClosed( WindowEvent e ) {
                    aboutDialog = null;
                }
            } );
            aboutDialog.setVisible( true );
        }
    }

    /**
     * Sets the background color of the standard control panels.
     *
     * @param color
     */
    public void setControlPanelBackground( Color color ) {
        for ( Module module : getModules() ) {
            module.setControlPanelBackground( color );
            module.setClockControlPanelBackground( color );
            module.setHelpPanelBackground( color );
        }
    }

    /**
     * Saves the simulation's configuration.
     * Default implementation does nothing, subclasses should override.
     */
    public void save() {
    }

    /**
     * Loads the simulation's configuration.
     * Default implementation does nothing, subclasses should override.
     */
    public void load() {
    }

    /**
     * Sets a new exit strategy, which will be applied when the user closes the application.  This is used in simsharing features since closing a view should not exit the VM.
     *
     * @param exitStrategy the new exit strategy to be applied on sim exit
     */
    public void setExitStrategy( VoidFunction0 exitStrategy ) {
        this.exitStrategy = exitStrategy;
    }

    /**
     * Exit the application, applying the current exit strategy
     */
    public void exit() {
        exitStrategy.apply();
    }

    /**
     * Parse command line args for a directive like "-module 2" which will set that as the startup module (0-based indices)
     * This is done so that the developer can easily specify a starting tab for the simulation by changing the command line arguments, see #3055
     *
     * @return the index of the module that should be active on startup, if any
     */
    public Option<Integer> getDevelopmentModule() {

        //Make sure the sim is running in development mode
        if ( abcLearnApplicationConfig.isDev() ) {

            //Check the command line arguments for presence of "-module" command
            int index = Arrays.asList( abcLearnApplicationConfig.getCommandLineArgs() ).indexOf( "-module" );
            if ( index >= 0 && index + 1 < abcLearnApplicationConfig.getCommandLineArgs().length ) {

                //Find the next argument and parse
                String moduleIndexString = abcLearnApplicationConfig.getCommandLineArgs()[index + 1];
                int moduleIndex = parseInt( moduleIndexString );

                //Signify the selected module
                return new Option.Some<Integer>( moduleIndex );
            }
        }

        //Developer did not override the default (first) module
        return new Option.None<Integer>();
    }
}