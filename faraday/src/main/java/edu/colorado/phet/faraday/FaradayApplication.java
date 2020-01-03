

package edu.colorado.phet.faraday;

import com.aimxcel.abclearn.common.abclearncommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationLauncher;

import edu.colorado.phet.common.piccolophet.PiccoloAbcLearnApplication;
import edu.colorado.phet.faraday.control.menu.FaradayOptionsMenu;
import edu.colorado.phet.faraday.module.*;

/**
 * FaradayApplication is the main application for the 
 * "Faraday's Electromagnetic Lab" simulation.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class FaradayApplication extends PiccoloAbcLearnApplication {

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     */
    public FaradayApplication( AbcLearnApplicationConfig config ) {
        super( config );
        initModules();
        initMenubar();
    }

    private void initModules() {
        
        BarMagnetModule barMagnetModule = new BarMagnetModule( true /* wiggleMeEnabled */ );
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
    }

    /**
     * Initializes the menubar.
     */
    private void initMenubar() {
        // Options menu
        FaradayOptionsMenu optionsMenu = new FaradayOptionsMenu( this );
        getAbcLearnFrame().addMenu( optionsMenu );
    }
    
    //----------------------------------------------------------------------------
    // main
    //----------------------------------------------------------------------------
    
    public static void main( final String[] args ) {
        
        ApplicationConstructor appConstructor = new ApplicationConstructor() {
            public AbcLearnApplication getApplication( AbcLearnApplicationConfig config ) {
                return new FaradayApplication( config );
            }
        };
        
        AbcLearnApplicationConfig appConfig = new AbcLearnApplicationConfig( args, FaradayConstants.PROJECT_NAME, FaradayConstants.FLAVOR_FARADAY );
        new AbcLearnApplicationLauncher().launchSim( appConfig, appConstructor );
    }
}