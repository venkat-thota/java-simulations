

package edu.colorado.phet.faraday;

import com.aimxcel.abclearn.common.abclearncommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationLauncher;

import edu.colorado.phet.common.piccolophet.PiccoloAbcLearnApplication;
import edu.colorado.phet.faraday.control.menu.FaradayOptionsMenu;
import edu.colorado.phet.faraday.module.BarMagnetModule;

/**
 * MagnetsAndCompassApplication is the main application
 * for the "Magnet and Compass" simulation.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class MagnetAndCompassApplication extends PiccoloAbcLearnApplication {

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     */
    public MagnetAndCompassApplication( AbcLearnApplicationConfig config ) {
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
        FaradayOptionsMenu optionsMenu = new FaradayOptionsMenu( this );
        getAbcLearnFrame().addMenu( optionsMenu );
    }
    
    //----------------------------------------------------------------------------
    // main
    //----------------------------------------------------------------------------
    
    public static void main( final String[] args ) {

        ApplicationConstructor appConstructor = new ApplicationConstructor() {
            public AbcLearnApplication getApplication( AbcLearnApplicationConfig config ) {
                return new MagnetAndCompassApplication( config );
            }
        };

        AbcLearnApplicationConfig appConfig = new AbcLearnApplicationConfig( args, FaradayConstants.PROJECT_NAME, FaradayConstants.FLAVOR_MAGNET_AND_COMPASS );
        new AbcLearnApplicationLauncher().launchSim( appConfig, appConstructor );
    }
}