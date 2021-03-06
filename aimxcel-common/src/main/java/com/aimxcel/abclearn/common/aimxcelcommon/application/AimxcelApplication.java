
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ModuleManager;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ModuleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.AimxcelAboutDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.util.CommandLineUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.util.IProguardKeepClass;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelExit;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ITabbedModulePane;
import com.aimxcel.abclearn.common.aimxcelcommon.view.JTabbedModulePane;

import static java.lang.Integer.parseInt;

public class AimxcelApplication implements IProguardKeepClass {

	
	private static final long serialVersionUID = 1L;
	public static final String DEVELOPER_CONTROLS_COMMAND_LINE_ARG = "-dev";// Command line argument to enable
																			// developer-only features
	private static ArrayList<AimxcelApplication> aimxcelApplications = new ArrayList<AimxcelApplication>();

	// ----------------------------------------------------------------
	// Instance data
	// ----------------------------------------------------------------

	private ITabbedModulePane tabbedModulePane;
	private AimxcelApplicationConfig aimxcelApplicationConfig;

	private AimxcelFrame aimxcelFrame;
	private ModuleManager moduleManager;
	private AimxcelAboutDialog aboutDialog; // not null only when About dialog is shown for the first time
	private boolean applicationStarted = false;// flag to make sure we don't start the application more than once

	// The exit strategy, to be applied when the user closes the application. By
	// default it calls AimxcelExit.exit
	private VoidFunction0 exitStrategy = new VoidFunction0() {
		public void apply() {
			AimxcelExit.exit();
		}
	};

	// ----------------------------------------------------------------
	// Constructors
	// ----------------------------------------------------------------

	public AimxcelApplication(AimxcelApplicationConfig config) {
		
		this(config, new JTabbedModulePane());
	}

	protected AimxcelApplication(AimxcelApplicationConfig aimxcelApplicationConfig, ITabbedModulePane tabbedPane) {
		this.aimxcelApplicationConfig = aimxcelApplicationConfig;
		this.tabbedModulePane = tabbedPane;
		this.moduleManager = new ModuleManager(this);
		aimxcelFrame = createAimxcelFrame();
		aimxcelApplicationConfig.getFrameSetup().initialize(aimxcelFrame);

		// Handle command line arguments
		parseArgs(aimxcelApplicationConfig.getCommandLineArgs());

		aimxcelApplications.add(this);
	}

	// ----------------------------------------------------------------
	//
	// ----------------------------------------------------------------

	public ITabbedModulePane getTabbedModulePane() {
		return tabbedModulePane;
	}

	/**
	 * Are developer controls enabled?
	 *
	 * @return true or false
	 */
	public boolean isDeveloperControlsEnabled() {
		return CommandLineUtils.contains(aimxcelApplicationConfig.getCommandLineArgs(),
				DEVELOPER_CONTROLS_COMMAND_LINE_ARG);
	}

	public ISimInfo getSimInfo() {
		return aimxcelApplicationConfig;
	}

	/**
	 * Get the last created AimxcelApplication.
	 *
	 * @return last created AimxcelApplication.
	 */
	public static AimxcelApplication getInstance() {
		return (AimxcelApplication) aimxcelApplications.get(aimxcelApplications.size() - 1);
	}

	/**
	 * Creates the AimxcelFrame for the application
	 * <p/>
	 * Concrete subclasses implement this
	 *
	 * @return The AimxcelFrame
	 */
	protected AimxcelFrame createAimxcelFrame() {
		return new AimxcelFrame(this);
	}

	/**
	 * Processes command line arguments. May be extended by subclasses.
	 *
	 * @param args
	 */
	protected void parseArgs(String[] args) {
	}

	/**
	 * Starts the AimxcelApplication.
	 * <p/>
	 * Sets up the mechanism that sets the reference sizes of all ApparatusPanel2
	 * instances.
	 */
	public void startApplication() {
		if (!applicationStarted) {
			applicationStarted = true;
			if (moduleManager.numModules() == 0) {
				throw new RuntimeException("No modules in module manager");
			}

			// Set up a mechanism that will set the reference sizes of all ApparatusPanel2
			// instances
			// after the AimxcelFrame has been set to its startup size.
			// When the outer WindowAdapter gets called, the AimxcelFrame is
			// at the proper size, but the ApparatusPanel2 has not yet gotten its resize
			// event.
			aimxcelFrame.addWindowFocusListener(new WindowAdapter() {
				public void windowGainedFocus(WindowEvent e) {
					initializeModuleReferenceSizes();
					aimxcelFrame.removeWindowFocusListener(this);
				}
			});

			// #3395: dev feature, start with the module number specified on the command
			// line.
			if (isDeveloperControlsEnabled()) {
				String startModuleNumber = aimxcelApplicationConfig.getOptionArg("-startModule");
				if (startModuleNumber != null) {
					setStartModule(getModule(Integer.valueOf(startModuleNumber)));
				}
			}

			moduleManager.setActiveModule(getStartModule());
			aimxcelFrame.setVisible(true);

			updateLogoVisibility();
		} else {
			// TODO: put into standard logging framework
			System.out.println("WARNING: AimxcelApplication.startApplication was called more than once.");
		}
	}

	/**
	 * This is supposed hides the logo panel in the control panel if there is
	 * already a logo visible in the tabbed module pane, so that both aren't visible
	 * by default..
	 * <p/>
	 * This method appears to give the correct behavior in the test program:
	 * TestCoreAimxcelApplication
	 * <p/>
	 * This method and functionality will be removed if is too awkward in practice
	 * (for example, too confusing to override this default).
	 */
	protected void updateLogoVisibility() {
		for (int i = 0; i < moduleManager.numModules(); i++) {
			if (moduleAt(i).isLogoPanelVisible() && aimxcelFrame.getTabbedModulePane() != null
					&& aimxcelFrame.getTabbedModulePane().getLogoVisible()) {
				moduleAt(i).setLogoPanelVisible(false);
			}
		}
	}

	private void initializeModuleReferenceSizes() {
		for (int i = 0; i < moduleManager.numModules(); i++) {
			(moduleManager.moduleAt(i)).setReferenceSize();
		}
	}

	/**
	 * Get the AimxcelFrame for this Application.
	 *
	 * @return the AimxcelFrame for this Application.
	 */
	public AimxcelFrame getAimxcelFrame() {
		return aimxcelFrame;
	}

	// ----------------------------------------------------------------
	// Module-related methods
	// ----------------------------------------------------------------

	/**
	 * Sets the modules in the application
	 *
	 * @param modules
	 */
	public void setModules(Module[] modules) {
		moduleManager.setModules(modules);
	}

	/**
	 * Remove a Module from this AimxcelApplication.
	 *
	 * @param module the Module to remove.
	 */
	public void removeModule(Module module) {
		moduleManager.removeModule(module);
	}

	/**
	 * Add one Module to this AimxcelApplication.
	 *
	 * @param module the Module to add.
	 */
	public void addModule(Module module) {
		moduleManager.addModule(module);
	}

	/**
	 * Get the specified Module.
	 *
	 * @param i the Module index
	 * @return the Module.
	 */
	public Module moduleAt(int i) {
		return moduleManager.moduleAt(i);
	}

	/**
	 * Gets a module based on its index. (This is a more common name for the
	 * moduleAt method.)
	 *
	 * @param i
	 * @return the module
	 */
	public Module getModule(int i) {
		return moduleAt(i);
	}

	/**
	 * Set the specified Module to be active.
	 *
	 * @param module the module to activate.
	 */
	public void setActiveModule(Module module) {
		moduleManager.setActiveModule(module);
	}

	/**
	 * Set the specified Module to be active.
	 *
	 * @param i the module index to activate.
	 */
	public void setActiveModule(int i) {
		moduleManager.setActiveModule(i);
	}

	/**
	 * Gets the module that will be activated on startup, by default, this is the
	 * first module added. To change the default, call setStartupModule. This can
	 * also be overriden in development mode, by passing the arguments "-dev" and
	 * "-module INT" where INT is the module index to start
	 *
	 * @return Module
	 */
	public Module getStartModule() {
		Option<Integer> developmentModule = getDevelopmentModule();
		if (developmentModule.isSome()) {
			return moduleAt(developmentModule.get());
		} else {
			return moduleManager.getStartModule();
		}
	}

	/**
	 * Sets the module that will be activated on startup.
	 *
	 * @param module
	 */
	public void setStartModule(Module module) {
		moduleManager.setStartModule(module);
	}

	/**
	 * Add a ModuleObserver to this AimxcelApplication to observe changes in the
	 * list of Modules, and which Module is active.
	 *
	 * @param moduleObserver
	 */
	public void addModuleObserver(ModuleObserver moduleObserver) {
		moduleManager.addModuleObserver(moduleObserver);
	}

	/**
	 * Removes the specified moduleObserver
	 *
	 * @param moduleObserver the ModuleObserver to unsubscribe from module events
	 */
	public void removeModuleObserver(ModuleObserver moduleObserver) {
		moduleManager.removeModuleObserver(moduleObserver);
	}

	/**
	 * Get the index of the specified Module.
	 *
	 * @param m
	 * @return the index of the specified Module.
	 */
	public int indexOf(Module m) {
		return moduleManager.indexOf(m);
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
	public void addModules(Module[] m) {
		for (int i = 0; i < m.length; i++) {
			addModule(m[i]);
		}
	}

	/**
	 * Returns all the Modules registered with this AimxcelApplication.
	 *
	 * @return all the Modules registered with this AimxcelApplication.
	 */
	public Module[] getModules() {
		return moduleManager.getModules();
	}

	/**
	 * Pauses the AimxcelApplication (including any Modules that are active).
	 */
	public void pause() {
		getActiveModule().deactivate();
	}

	/**
	 * Resumes progress of the AimxcelApplication (including any Modules that are
	 * active).
	 */
	public void resume() {
		getActiveModule().activate();
	}

	/**
	 * Displays an About Dialog for the simulation.
	 */
	public void showAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new AimxcelAboutDialog(this);
			aboutDialog.addWindowListener(new WindowAdapter() {
				// called when the close button in the dialog's window dressing is clicked
				public void windowClosing(WindowEvent e) {
					aboutDialog.dispose();
				}

				// called by JDialog.dispose
				public void windowClosed(WindowEvent e) {
					aboutDialog = null;
				}
			});
			aboutDialog.setVisible(true);
		}
	}

	/**
	 * Sets the background color of the standard control panels.
	 *
	 * @param color
	 */
	public void setControlPanelBackground(Color color) {
		for (Module module : getModules()) {
			module.setControlPanelBackground(color);
			module.setClockControlPanelBackground(color);
			module.setHelpPanelBackground(color);
		}
	}

	/**
	 * Saves the simulation's configuration. Default implementation does nothing,
	 * subclasses should override.
	 */
	public void save() {
	}

	/**
	 * Loads the simulation's configuration. Default implementation does nothing,
	 * subclasses should override.
	 */
	public void load() {
	}

	/**
	 * Sets a new exit strategy, which will be applied when the user closes the
	 * application. This is used in simsharing features since closing a view should
	 * not exit the VM.
	 *
	 * @param exitStrategy the new exit strategy to be applied on sim exit
	 */
	public void setExitStrategy(VoidFunction0 exitStrategy) {
		this.exitStrategy = exitStrategy;
	}

	/**
	 * Exit the application, applying the current exit strategy
	 */
	public void exit() {
		exitStrategy.apply();
	}

	/**
	 * Parse command line args for a directive like "-module 2" which will set that
	 * as the startup module (0-based indices) This is done so that the developer
	 * can easily specify a starting tab for the simulation by changing the command
	 * line arguments, see #3055
	 *
	 * @return the index of the module that should be active on startup, if any
	 */
	public Option<Integer> getDevelopmentModule() {

		// Make sure the sim is running in development mode
		if (aimxcelApplicationConfig.isDev()) {

			// Check the command line arguments for presence of "-module" command
			int index = Arrays.asList(aimxcelApplicationConfig.getCommandLineArgs()).indexOf("-module");
			if (index >= 0 && index + 1 < aimxcelApplicationConfig.getCommandLineArgs().length) {

				// Find the next argument and parse
				String moduleIndexString = aimxcelApplicationConfig.getCommandLineArgs()[index + 1];
				int moduleIndex = parseInt(moduleIndexString);

				// Signify the selected module
				return new Option.Some<Integer>(moduleIndex);
			}
		}

		// Developer did not override the default (first) module
		return new Option.None<Integer>();
	}
}