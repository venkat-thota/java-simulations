
package com.aimxcel.abclearn.aimxcelgraphics.test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.aimxcelgraphics.application.AimxcelGraphicsModule;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelTextGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;

/**
 * Class: TestApparatusPanel2
 * Package: edu.colorado.phet.common.tests
 * Author: Another Guy
 * Date: Dec 13, 2004
 * <p/>
 * CVS Info:
 * Current revision:   $Revision$
 * On branch:          $Name$
 * Latest change by:   $Author$
 * On date:            $Date$
 */
public class TestApparatusPanel2 {

//    static class TestAppModel extends ApplicationModel {
//        public TestAppModel() {
//            super( "", "", "" );
//            this.setClock( new SwingClock( 30, 10.0 ) );
//            TestModule module = new TestModule( this, getClock() );
//            setModule( module );
//            setFrameCenteredSize( 400, 300 );
//            setInitialModule( module );
//        }
//    }

    static class TestModule extends AimxcelGraphicsModule {

        protected TestModule( IClock clock ) {
            super( "ApparatusPanel2 Test", clock );

//            BaseModel model = new BaseModel();
//            ApparatusPanel ap = new ApparatusPanel();
            BaseModel baseModel = new BaseModel();
            ApparatusPanel ap = new ApparatusPanel2( new SwingClock( 30, 1 ) );
            setApparatusPanel( ap );
            setModel( baseModel );

            String family = "Serif";
            int style = Font.PLAIN;
            int size = 12;
            Font font = new Font( family, style, size );
            AimxcelGraphic pg = new TestGraphic( ap, font, "YO!", Color.blue, 100, 100 );
            pg.setCursorHand();
            ap.addGraphic( pg );
            AimxcelGraphic pg2 = new TestGraphic2( ap, 50, 50 );
            pg2.setCursorHand();
            ap.addGraphic( pg2 );
        }
    }

    static class TestGraphic extends AimxcelTextGraphic {
        public TestGraphic( Component component, Font font, String text, Color color, int x, int y ) {
            super( component, font, text, color, x, y );
            addTranslationListener( new TranslationListener() {
                public void translationOccurred( TranslationEvent translationEvent ) {
                    setLocation( (int) ( getLocation().getX() + translationEvent.getDx() ),
                                 (int) ( getLocation().getY() + translationEvent.getDy() ) );
                }
            } );
        }

        public void fireMouseEntered( MouseEvent e ) {
            super.fireMouseEntered( e );
        }
    }

    static class TestGraphic2 extends AimxcelShapeGraphic {
        public TestGraphic2( Component component, int x, int y ) {
            super( component, new Rectangle( x, y, 30, 20 ), Color.red );
        }

        public void fireMouseEntered( MouseEvent e ) {
            super.fireMouseEntered( e );
            setColor( Color.blue );
        }

        public void fireMouseExited( MouseEvent e ) {
            super.fireMouseExited( e );
            setColor( Color.red );
        }
    }

    public static void main( String[] args ) {
        AimxcelTestApplication testApp = new AimxcelTestApplication( args );
        TestModule module = new TestModule( new SwingClock( 30, 1 ) );
        testApp.addModule( module );
        testApp.setModules( new Module[] { module } );
        testApp.startApplication();
    }
}
