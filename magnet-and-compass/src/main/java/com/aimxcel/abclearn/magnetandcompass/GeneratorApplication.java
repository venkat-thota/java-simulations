

package com.aimxcel.abclearn.magnetandcompass;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.core.aimxcelcore.CoreAimxcelApplication;
import com.aimxcel.abclearn.magnetandcompass.control.menu.MagnetAndCompassOptionsMenu;
import com.aimxcel.abclearn.magnetandcompass.module.*;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;


public class GeneratorApplication extends CoreAimxcelApplication {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     */
    public GeneratorApplication( AimxcelApplicationConfig config ) {
        super( config );
        initModules();
        initMenubar();
    }

    private void initModules() {
        
        BarMagnetModule barMagnetModule = new BarMagnetModule( false /* wiggleMeEnabled */ );
        barMagnetModule.setShowEarthVisible( false );
        addModule( barMagnetModule );
        
        PickupCoilModule pickupCoilModule = new PickupCoilModule();
        addModule( pickupCoilModule );
        
        ElectromagnetModule electromagnetModule = new ElectromagnetModule();
        addModule( electromagnetModule );
        
        TransformerModule transformerModule = new TransformerModule();
        addModule( transformerModule );
        
        GeneratorModule generatorModule = new GeneratorModule();
        addModule( generatorModule );
        
        setStartModule( generatorModule );
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
                return new GeneratorApplication( config );
            }
        };

        AimxcelApplicationConfig appConfig = new AimxcelApplicationConfig( args, MagnetAndCompassConstants.PROJECT_NAME, MagnetAndCompassConstants.FLAVOR_GENERATOR );
        new AimxcelApplicationLauncher().launchSim( appConfig, appConstructor );
    }
}