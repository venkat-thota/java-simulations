
 package com.aimxcel.abclearn.aimxcelgraphics.test;

import java.awt.Color;

import com.aimxcel.abclearn.aimxcelgraphics.application.AimxcelGraphicsModule;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;


public class DebugMenuTest {

    public static void main( String[] args ) {
        SwingClock clock = new SwingClock( 1, 25 );
//        ApplicationModel am = new ApplicationModel( "Debug Test", "", "" );
//        am.setClock( clock );
//        DebugMenuTestModule debugMenuTestModule = new DebugMenuTestModule( am.getClock() );
//        am.setModules( new AimxcelGraphicsModule[]{debugMenuTestModule} );
//        am.setInitialModule( debugMenuTestModule );

        AimxcelTestApplication app = new AimxcelTestApplication( args );
        DebugMenuTestModule debugMenuTestModule = new DebugMenuTestModule( clock );
        app.addModule( debugMenuTestModule );
        app.startApplication();
    }

    static class DebugMenuTestModule extends AimxcelGraphicsModule {
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
