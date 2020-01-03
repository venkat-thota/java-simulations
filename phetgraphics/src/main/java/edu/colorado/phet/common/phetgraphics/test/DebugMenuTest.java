

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package edu.colorado.phet.common.phetgraphics.test;

import java.awt.Color;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnTestApplication;
import com.aimxcel.abclearn.common.abclearncommon.model.BaseModel;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;

import edu.colorado.phet.common.phetgraphics.application.AbcLearnGraphicsModule;
import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel2;

/**
 * DebugMenuTest
 *
 * @author Ron LeMaster
 * @version $Revision$
 */
public class DebugMenuTest {

    public static void main( String[] args ) {
        SwingClock clock = new SwingClock( 1, 25 );
//        ApplicationModel am = new ApplicationModel( "Debug Test", "", "" );
//        am.setClock( clock );
//        DebugMenuTestModule debugMenuTestModule = new DebugMenuTestModule( am.getClock() );
//        am.setModules( new AbcLearnGraphicsModule[]{debugMenuTestModule} );
//        am.setInitialModule( debugMenuTestModule );

        AbcLearnTestApplication app = new AbcLearnTestApplication( args );
        DebugMenuTestModule debugMenuTestModule = new DebugMenuTestModule( clock );
        app.addModule( debugMenuTestModule );
        app.startApplication();
    }

    static class DebugMenuTestModule extends AbcLearnGraphicsModule {
        protected DebugMenuTestModule( IClock clock ) {
            super( "Debug Menu Test", clock );

            BaseModel model = new BaseModel();
            setModel( model );
            ApparatusPanel2 ap = new ApparatusPanel2( clock );
//            ap.setUseOffscreenBuffer( true );
            ap.setBackground( Color.white );
            setApparatusPanel( ap );
        }
    }
}
