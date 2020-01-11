
package com.aimxcel.abclearn.platetectonics;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import com.aimxcel.abclearn.platetectonics.dev.PerformanceFrame;
import com.aimxcel.abclearn.platetectonics.tabs.CrustTab;
import com.aimxcel.abclearn.platetectonics.tabs.PlateMotionTab;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.menu.OptionsMenu;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelTabbedPane.TabbedModule;
import com.aimxcel.abclearn.lwjgl.LWJGLAimxcelApplication;
import com.aimxcel.abclearn.lwjgl.LWJGLCanvas;
import com.aimxcel.abclearn.lwjgl.StartupUtils;
import com.aimxcel.abclearn.lwjgl.utils.ColorPropertyControl;
import com.aimxcel.abclearn.lwjgl.utils.GLActionListener;
import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;

/**
 * Main simulation entry point
 */
public class PlateTectonicsApplication extends LWJGLAimxcelApplication {

    public static final Property<Boolean> showFPSMeter = new Property<Boolean>( false );
    public static final Property<Boolean> showDebuggingItems = new Property<Boolean>( false );
    public static final Property<Boolean> moveTimeControl = new Property<Boolean>( false );

    /**
     * Sole constructor.
     *
     * @param config the configuration for this application
     */
    public PlateTectonicsApplication( AimxcelApplicationConfig config ) {
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

        final Frame parentFrame = getAimxcelFrame();

        final LWJGLCanvas canvas = LWJGLCanvas.getCanvasInstance( parentFrame );

        // uses our TabbedMolecule interface to present multiple tabs using the same LWJGL canvas.
        // at the current time, it is impractical to have multiple LWJGL canvases at the same time, and the module system was not flexible enough to
        // not hide and re-display the LWJGL canvas when switching tabs (which causes 1-2 seconds of artifacts)
        addModule( new TabbedModule( canvas ) {{
            addTab( new CrustTab( canvas ) );
            addTab( new PlateMotionTab( canvas ) );
        }} );
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

        if ( optionsMenu.getMenuComponentCount() > 0 ) {
            frame.addMenu( optionsMenu );
        }

        /*---------------------------------------------------------------------------*
        * developer controls
        *----------------------------------------------------------------------------*/
        JMenu developerMenu = frame.getDeveloperMenu();
        // add items to the Developer menu here...

        developerMenu.add( new JCheckBoxMenuItem( "Show Frames Per Second" ) {{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    final boolean show = isSelected();
                    LWJGLUtils.invoke( new Runnable() {
                        public void run() {
                            showFPSMeter.set( show );
                        }
                    } );
                }
            } );
        }} );

        developerMenu.add( new JCheckBoxMenuItem( "Show Debugging Items" ) {{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    final boolean show = isSelected();
                    LWJGLUtils.invoke( new Runnable() {
                        public void run() {
                            showDebuggingItems.set( show );
                        }
                    } );
                }
            } );
        }} );

        developerMenu.add( new JCheckBoxMenuItem( "Re-align time control" ) {{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    setSelected( moveTimeControl.get() );
                    final boolean show = isSelected();
                    LWJGLUtils.invoke( new Runnable() {
                        public void run() {
                            moveTimeControl.set( show );
                        }
                    } );
                }
            } );
        }} );

        developerMenu.add( new JSeparator() );

        developerMenu.add( new JMenuItem( "Performance Options" ) {{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    new PerformanceFrame();
                }
            } );
        }} );

        developerMenu.add( new JMenuItem( "Color Options" ) {{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    new JDialog( frame ) {{
                        setTitle( "Color Options" );
                        setResizable( false );

                        setContentPane( new JPanel() {{
                            setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
                            add( new ColorPropertyControl( frame, "Dial color: ", PlateTectonicsConstants.DIAL_HIGHLIGHT_COLOR ) );
                        }} );
                        pack();
                        SwingUtils.centerInParent( this );
                    }}.setVisible( true );
                }
            } );
        }} );

        developerMenu.add( new JMenuItem( "Show Error Dialog" ) {{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    LWJGLUtils.showErrorDialog( frame, new RuntimeException( "This is a test" ) );
                }
            } );
        }} );
        developerMenu.add( new JMenuItem( "Show Mac Java 1.7 Dialog" ) {{
            addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    LWJGLUtils.showMacJava7Warning( frame );
                }
            } );
        }} );
    }

    //----------------------------------------------------------------------------
    // main
    //----------------------------------------------------------------------------

    public static void main( final String[] args ) throws ClassNotFoundException {
        try {
            StartupUtils.setupLibraries();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }

        /*
        * If you want to customize your application (look-&-feel, window size, etc)
        * create your own AimxcelApplicationConfig and use one of the other launchSim methods
        */
        new AimxcelApplicationLauncher().launchSim( args, PlateTectonicsResources.PROJECT_NAME, PlateTectonicsApplication.class );
    }
}