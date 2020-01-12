
package com.aimxcel.abclearn.glaciers;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import com.aimxcel.abclearn.glaciers.module.advanced.AdvancedModule;
import com.aimxcel.abclearn.glaciers.module.intro.IntroModule;
import com.aimxcel.abclearn.glaciers.persistence.AdvancedConfig;
import com.aimxcel.abclearn.glaciers.persistence.GlaciersConfig;
import com.aimxcel.abclearn.glaciers.persistence.IntroConfig;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.common.aimxcelcommon.util.persistence.XMLPersistenceManager;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication;
public class GlaciersApplication extends CoreAimxcelApplication {

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IntroModule _introModule;
    private AdvancedModule _advancedModule;

    // Used to save/load simulation configurations.
    private XMLPersistenceManager _persistenceManager;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    public GlaciersApplication( AimxcelApplicationConfig config ) {
        super( config );
        initModules();
        initMenubar( config.getCommandLineArgs() );
    }

    //----------------------------------------------------------------------------
    // Initialization
    //----------------------------------------------------------------------------

    /*
     * Initializes the modules.
     */
    private void initModules() {

        Frame parentFrame = getAimxcelFrame();

        _introModule = new IntroModule( parentFrame );
        addModule( _introModule );

        _advancedModule = new AdvancedModule( parentFrame );
        addModule( _advancedModule );
    }

    /*
     * Initializes the menubar.
     */
    private void initMenubar( String[] args ) {

        // File->Save/Load
        final AimxcelFrame frame = getAimxcelFrame();
        frame.addFileSaveLoadMenuItems();
        if ( _persistenceManager == null ) {
            _persistenceManager = new XMLPersistenceManager( frame );
        }

        // Developer menu
        JMenu developerMenu = frame.getDeveloperMenu();
        final JCheckBoxMenuItem evolutionStateDialogItem = new JCheckBoxMenuItem( "Glacier Evolution State..." );
        developerMenu.add( evolutionStateDialogItem );
        evolutionStateDialogItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                setEvolutionStateDialogVisible( evolutionStateDialogItem.isSelected() );
            }
        } );
        final JCheckBoxMenuItem modelConstantsDialogItem = new JCheckBoxMenuItem( "Model Constants..." );
        developerMenu.add( modelConstantsDialogItem );
        modelConstantsDialogItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                setModelConstantsDialogVisible( modelConstantsDialogItem.isSelected() );
            }
        } );
    }

    //----------------------------------------------------------------------------
    // Setters and getters
    //----------------------------------------------------------------------------

    public void setEvolutionStateDialogVisible( boolean visible ) {
        _introModule.setEvolutionStateDialogVisible( visible );
        _advancedModule.setEvolutionStateDialogVisible( visible );
    }

    public void setModelConstantsDialogVisible( boolean visible ) {
        _introModule.setModelConstantsDialogVisible( visible );
        _advancedModule.setModelConstantsDialogVisible( visible );
    }

    //----------------------------------------------------------------------------
    // Persistence
    //----------------------------------------------------------------------------

    /**
     * Saves the simulation's configuration.
     */
    @Override
    public void save() {

        GlaciersConfig appConfig = new GlaciersConfig();

        appConfig.setVersionString( getSimInfo().getVersion().toString() );
        appConfig.setVersionMajor( getSimInfo().getVersion().getMajor() );
        appConfig.setVersionMinor( getSimInfo().getVersion().getMinor() );
        appConfig.setVersionDev( getSimInfo().getVersion().getDev() );
        appConfig.setVersionRevision( getSimInfo().getVersion().getRevision() );

        IntroConfig basicConfig = _introModule.save();
        appConfig.setBasicConfig( basicConfig );

        AdvancedConfig advancedConfig = _advancedModule.save();
        appConfig.setAdvancedConfig( advancedConfig );

        _persistenceManager.save( appConfig );
    }

    /**
     * Loads the simulation's configuration.
     */
    @Override
    public void load() {

        Object object = _persistenceManager.load();
        if ( object != null ) {

            if ( object instanceof GlaciersConfig ) {
                GlaciersConfig appConfig = (GlaciersConfig) object;

                IntroConfig basicConfig = appConfig.getBasicConfig();
                _introModule.load( basicConfig );

                AdvancedConfig advancedConfig = appConfig.getAdvancedConfig();
                _advancedModule.load( advancedConfig );
            }
            else {
                AimxcelOptionPane.showErrorDialog( getAimxcelFrame(), GlaciersStrings.MESSAGE_NOT_A_CONFIG_FILE );
            }
        }
    }

    //----------------------------------------------------------------------------
    // main
    //----------------------------------------------------------------------------

    /**
     * Main entry point.
     *
     * @param args command line arguments
     */
    public static void main( final String[] args ) {
        new AimxcelApplicationLauncher().launchSim( args, GlaciersConstants.PROJECT_NAME, GlaciersApplication.class );
    }
}
