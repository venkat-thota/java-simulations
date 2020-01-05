

package com.aimxcel.abclearn.magnetsandelectromagnets;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.core.aimxcelcore.PiccoloAimxcelApplication;
import com.aimxcel.abclearn.magnetsandelectromagnets.control.menu.MagnetsAndElectromagnetsOptionsMenu;
import com.aimxcel.abclearn.magnetsandelectromagnets.module.BarMagnetModule;
import com.aimxcel.abclearn.magnetsandelectromagnets.module.ElectromagnetModule;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;


public class MagnetsAndElectromagnetsApplication extends PiccoloAimxcelApplication {

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
    public MagnetsAndElectromagnetsApplication( AimxcelApplicationConfig config ) {
        super( config );
        initModules();
        initMenubar();
    }

    private void initModules() {
        
        BarMagnetModule barMagnetModule = new BarMagnetModule( true /* wiggleMeEnabled */);
        addModule( barMagnetModule );
        
        ElectromagnetModule electromagnetModule = new ElectromagnetModule();
        addModule( electromagnetModule );
    }

    /**
     * Initializes the menubar.
     */
    private void initMenubar() {
        // Options menu
        MagnetsAndElectromagnetsOptionsMenu optionsMenu = new MagnetsAndElectromagnetsOptionsMenu( this );
        getAimxcelFrame().addMenu( optionsMenu );
    }
    
    //----------------------------------------------------------------------------
    // main
    //----------------------------------------------------------------------------

    public static void main( final String[] args ) {

        ApplicationConstructor appConstructor = new ApplicationConstructor() {
            public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
                return new MagnetsAndElectromagnetsApplication( config );
            }
        };

        AimxcelApplicationConfig appConfig = new AimxcelApplicationConfig( args, MagnetsAndElectromagnetsConstants.PROJECT_NAME, MagnetsAndElectromagnetsConstants.FLAVOR_MAGNETS_AND_ELECTROMAGNETS );
        new AimxcelApplicationLauncher().launchSim( appConfig, appConstructor );
    }
}