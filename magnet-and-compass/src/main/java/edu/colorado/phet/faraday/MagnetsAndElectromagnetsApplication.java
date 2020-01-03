

package edu.colorado.phet.faraday;

import com.aimxcel.abclearn.common.abclearncommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationLauncher;

import edu.colorado.phet.common.piccolophet.PiccoloAbcLearnApplication;
import edu.colorado.phet.faraday.control.menu.FaradayOptionsMenu;
import edu.colorado.phet.faraday.module.BarMagnetModule;
import edu.colorado.phet.faraday.module.ElectromagnetModule;

/**
 * MagnetsAndElectromagnetsApplication is the main application 
 * for the "Magnets and Electromagnets" simulation.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class MagnetsAndElectromagnetsApplication extends PiccoloAbcLearnApplication {

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     */
    public MagnetsAndElectromagnetsApplication( AbcLearnApplicationConfig config ) {
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
        getAbcLearnFrame().addMenu( optionsMenu );
    }
    
    //----------------------------------------------------------------------------
    // main
    //----------------------------------------------------------------------------

    public static void main( final String[] args ) {

        ApplicationConstructor appConstructor = new ApplicationConstructor() {
            public AbcLearnApplication getApplication( AbcLearnApplicationConfig config ) {
                return new MagnetsAndElectromagnetsApplication( config );
            }
        };

        AbcLearnApplicationConfig appConfig = new AbcLearnApplicationConfig( args, FaradayConstants.PROJECT_NAME, FaradayConstants.FLAVOR_MAGNETS_AND_ELECTROMAGNETS );
        new AbcLearnApplicationLauncher().launchSim( appConfig, appConstructor );
    }
}