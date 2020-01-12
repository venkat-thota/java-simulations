
package com.aimxcel.abclearn.buildamolecule;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing.UserComponent;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.buildamolecule.model.MoleculeList;
import com.aimxcel.abclearn.buildamolecule.module.AbstractBuildAMoleculeModule;
import com.aimxcel.abclearn.buildamolecule.module.CollectMultipleModule;
import com.aimxcel.abclearn.buildamolecule.module.LargerMoleculesModule;
import com.aimxcel.abclearn.buildamolecule.module.MakeMoleculeModule;
import com.aimxcel.abclearn.buildamolecule.tests.MoleculeTableDialog;

import com.aimxcel.abclearn.games.GameAudioPlayer;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.ColorControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyCheckBoxMenuItem;
import com.aimxcel.abclearn.common.aimxcelcommon.view.menu.OptionsMenu;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication;

/**
 * The main application for this simulation.
 */
public class BuildAMoleculeApplication extends CoreAimxcelApplication {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * Shows a cursor that indicates a bond will be split when the mouse is over, and on a click it will break the bond
     */
    public static final Property<Boolean> allowBondBreaking = new Property<Boolean>( true );


    /*---------------------------------------------------------------------------*
    * audio
    *----------------------------------------------------------------------------*/

    private static final GameAudioPlayer audioPlayer = new GameAudioPlayer( true ) {{
        init();
    }};
    public static final Property<Boolean> soundEnabled = new Property<Boolean>( true );

    /**
     * Sole constructor.
     *
     * @param config the configuration for this application
     */
    public BuildAMoleculeApplication( AimxcelApplicationConfig config ) {
        super( config );
        initModules();
        initMenubar();
    }

    //----------------------------------------------------------------------------
    // Initialization
    //----------------------------------------------------------------------------

    /*
     * Initializes the modules.
     */
    private void initModules() {

        Frame parentFrame = getAimxcelFrame();

        Module makeMoleculeModule = new MakeMoleculeModule( parentFrame );
        addModule( makeMoleculeModule );

        Module collectMultipleModule = new CollectMultipleModule( parentFrame );
        addModule( collectMultipleModule );

        Module largerMolecules = new LargerMoleculesModule( parentFrame );
        addModule( largerMolecules );
    }

    /*
     * Initializes the menubar.
     */
    private void initMenubar() {

        // Create main frame.
        final AimxcelFrame frame = getAimxcelFrame();

        // Options menu
        OptionsMenu optionsMenu = new OptionsMenu();
        // add menu items here, or in a subclass on OptionsMenu

        optionsMenu.add( new JMenuItem( BuildAMoleculeStrings.RESET_CURRENT_TAB ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    getActiveModule().reset();
                    SimSharingManager.sendUserMessage( UserComponent.resetCurrentTab, UserComponentTypes.menuItem, UserActions.activated );
                }
            } );
        }} );

        if ( optionsMenu.getMenuComponentCount() > 0 ) {
            frame.addMenu( optionsMenu );
        }

        // Developer menu
        JMenu developerMenu = frame.getDeveloperMenu();
        // add items to the Developer menu here...
        developerMenu.add( new JMenuItem( "Show Table of Molecules" ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    new MoleculeTableDialog( getAimxcelFrame() ).setVisible( true );
                }
            } );
        }} );
        developerMenu.add( new JMenuItem( "Regenerate model" ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    ( (AbstractBuildAMoleculeModule) getActiveModule() ).addGeneratedCollection();
                }
            } );
        }} );
        developerMenu.add( new PropertyCheckBoxMenuItem( "Enable bond breaking", allowBondBreaking ) );
        developerMenu.add( new JMenuItem( "Change Filled Collection Box Color" ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    new JDialog( frame ) {/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

					{
                        setTitle( "Build a Molecule Colors" );
                        setResizable( false );

                        final ColorControl control = new ColorControl( frame, "box highlight:", BuildAMoleculeConstants.MOLECULE_COLLECTION_BOX_HIGHLIGHT.get() );
                        control.addChangeListener( new ChangeListener() {
                            public void stateChanged( ChangeEvent e ) {
                                BuildAMoleculeConstants.MOLECULE_COLLECTION_BOX_HIGHLIGHT.set( control.getColor() );
                            }
                        } );

                        setContentPane( control );
                        pack();
                        SwingUtils.centerInParent( this );
                    }}.setVisible( true );
                }
            } );
        }} );
        developerMenu.add( new JMenuItem( "Trigger complete dialog" ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    ( (AbstractBuildAMoleculeModule) getActiveModule() ).getCanvas().getCurrentCollection().allCollectionBoxesFilled.set( true );
                }
            } );
        }} );
        developerMenu.add( new JMenuItem( "Load additional data for profiling" ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    MoleculeList.testLoadingForProfiling();
                }
            } );
        }} );
    }

    //----------------------------------------------------------------------------
    // main
    //----------------------------------------------------------------------------

    public static void main( final String[] args ) throws ClassNotFoundException {
        MoleculeList.startInitialization();

        /*
         * If you want to customize your application (look-&-feel, window size, etc)
         * create your own AimxcelApplicationConfig and use one of the other launchSim methods
         */
        new AimxcelApplicationLauncher().launchSim( args, BuildAMoleculeConstants.PROJECT_NAME, BuildAMoleculeApplication.class );
    }

    public static void playCollectionBoxFilledSound() {
        if ( soundEnabled.get() ) {
            audioPlayer.correctAnswer();
        }
    }
}
