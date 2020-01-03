
package edu.colorado.phet.common.phetgraphics.test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnTestApplication;
import com.aimxcel.abclearn.common.abclearncommon.model.BaseModel;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;

import edu.colorado.phet.common.phetgraphics.application.AbcLearnGraphicsModule;
import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel;
import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel2;
import edu.colorado.phet.common.phetgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import edu.colorado.phet.common.phetgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnShapeGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnTextGraphic;

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

    static class TestModule extends AbcLearnGraphicsModule {

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
            AbcLearnGraphic pg = new TestGraphic( ap, font, "YO!", Color.blue, 100, 100 );
            pg.setCursorHand();
            ap.addGraphic( pg );
            AbcLearnGraphic pg2 = new TestGraphic2( ap, 50, 50 );
            pg2.setCursorHand();
            ap.addGraphic( pg2 );
        }
    }

    static class TestGraphic extends AbcLearnTextGraphic {
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

    static class TestGraphic2 extends AbcLearnShapeGraphic {
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
        AbcLearnTestApplication testApp = new AbcLearnTestApplication( args );
        TestModule module = new TestModule( new SwingClock( 30, 1 ) );
        testApp.addModule( module );
        testApp.setModules( new Module[] { module } );
        testApp.startApplication();
    }
}
