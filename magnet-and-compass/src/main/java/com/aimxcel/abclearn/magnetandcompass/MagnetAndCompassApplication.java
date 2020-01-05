

package com.aimxcel.abclearn.magnetandcompass;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.core.aimxcelcore.PiccoloAimxcelApplication;
import com.aimxcel.abclearn.magnetandcompass.control.menu.MagnetAndCompassOptionsMenu;
import com.aimxcel.abclearn.magnetandcompass.module.BarMagnetModule;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;


public class MagnetAndCompassApplication extends PiccoloAimxcelApplication {

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Sole constructor.
     */
    public MagnetAndCompassApplication( AimxcelApplicationConfig config ) {
        super( config );
        initModules();
        initMenubar();
    }

    private void initModules() {
        BarMagnetModule barMagnetModule = new BarMagnetModule( true /* wiggleMeEnabled */ );
        barMagnetModule.setClockControlPanelVisible( false );
        addModule( barMagnetModule );
    }

    /**
     * Initializes the menubar.
     */
    private void initMenubar() {
        // Options menu
        MagnetAndCompassOptionsMenu optionsMenu = new MagnetAndCompassOptionsMenu( this );
        getAimxcelFrame().addMenu( optionsMenu );
    }
    
    //----------------------------------------------------------------------------
    // main
    //----------------------------------------------------------------------------
    
    public static void main( final String[] args ) {

        ApplicationConstructor appConstructor = new ApplicationConstructor() {
            public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
                return new MagnetAndCompassApplication( config );
            }
        };

        AimxcelApplicationConfig appConfig = new AimxcelApplicationConfig( args, MagnetAndCompassConstants.PROJECT_NAME, MagnetAndCompassConstants.FLAVOR_MAGNET_AND_COMPASS );
        new AimxcelApplicationLauncher().launchSim( appConfig, appConstructor );
    }
}