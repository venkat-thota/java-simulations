

package edu.colorado.phet.faraday;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.core.aimxcelcore.PiccoloAimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;

import edu.colorado.phet.faraday.control.menu.FaradayOptionsMenu;
import edu.colorado.phet.faraday.module.BarMagnetModule;
import edu.colorado.phet.faraday.module.ElectromagnetModule;

/**
 * MagnetsAndElectromagnetsApplication is the main application 
 * for the "Magnets and Electromagnets" simulation.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class MagnetsAndElectromagnetsApplication extends PiccoloAimxcelApplication {

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

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
        FaradayOptionsMenu optionsMenu = new FaradayOptionsMenu( this );
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

        AimxcelApplicationConfig appConfig = new AimxcelApplicationConfig( args, FaradayConstants.PROJECT_NAME, FaradayConstants.FLAVOR_MAGNETS_AND_ELECTROMAGNETS );
        new AimxcelApplicationLauncher().launchSim( appConfig, appConstructor );
    }
}