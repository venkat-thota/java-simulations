

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
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.event.MouseInputAdapter;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnTestApplication;
import com.aimxcel.abclearn.common.abclearncommon.model.BaseModel;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;

import edu.colorado.phet.common.phetgraphics.application.AbcLearnGraphicsModule;
import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel2;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnShapeGraphic;

/**
 * TestMouseHandling is a simple simulation that is useful for testing of mouse event handling.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 * @version $Revision$
 */
public class TestMouseHandling {

    /**
     * main
     */
    public static void main( String args[] ) throws IOException {
        TestMouseHandling test = new TestMouseHandling( args );
    }

    /**
     * TestMouseHandling creates the PhET application.
     */
    public TestMouseHandling( String[] args ) throws IOException {

        // Set up the application descriptor.
        IClock clock = new SwingClock( 40, 1 );
        AbcLearnGraphicsModule module = new TestModule( clock );

        // Create and start the application.
        AbcLearnTestApplication app = new AbcLearnTestApplication( args );
        app.addModule( module );
        app.startApplication();
    }

    /**
     * TestModule sets up a module with two draggable shapes.
     */
    private class TestModule extends AbcLearnGraphicsModule {
        public TestModule( IClock clock ) {
            super( "Test Module", clock );

            // Model
            BaseModel model = new BaseModel();
            setModel( model );

            // Apparatus Panel
            ApparatusPanel2 apparatusPanel = new ApparatusPanel2( clock );
            apparatusPanel.setBackground( Color.BLACK );
            setApparatusPanel( apparatusPanel );

            // Yellow rectangle
            AbcLearnShapeGraphic yellowGraphic = new AbcLearnShapeGraphic( apparatusPanel );
            yellowGraphic.setName( "yellow" );
            yellowGraphic.setShape( new Rectangle( 0, 0, 50, 50 ) );
            yellowGraphic.setPaint( Color.YELLOW );
            yellowGraphic.setLocation( 100, 100 );
            yellowGraphic.setCursorHand();
            yellowGraphic.addMouseInputListener( new MouseHandler( yellowGraphic ) );
            apparatusPanel.addGraphic( yellowGraphic, 1 );

            // Red rectangle
            final AbcLearnShapeGraphic redGraphic = new AbcLearnShapeGraphic( apparatusPanel );
            redGraphic.setName( "red" );
            redGraphic.setShape( new Rectangle( 0, 0, 50, 50 ) );
            redGraphic.setPaint( Color.RED );
            redGraphic.setLocation( 200, 100 );
            redGraphic.setCursorHand();
            redGraphic.addMouseInputListener( new MouseHandler( redGraphic ) );
            apparatusPanel.addGraphic( redGraphic, 2 );
        }
    }

    /**
     * MouseHandler handles mouse events for a specified graphic.
     * It prints a debugging message for each event received,
     * and moves the graphic during dragging.
     */
    private class MouseHandler extends MouseInputAdapter {

        private AbcLearnGraphic _graphic;
        private Point _previousPoint;

        public MouseHandler( AbcLearnGraphic graphic ) {
            super();
            _graphic = graphic;
            _previousPoint = new Point();
        }

        public void mouseClicked( MouseEvent event ) {
            System.out.println( "mouseClicked on " + _graphic.getName() );
        }

        public void mouseDragged( MouseEvent event ) {
            System.out.println( "mouseDragged on " + _graphic.getName() );
            int dx = event.getX() - _previousPoint.x;
            int dy = event.getY() - _previousPoint.y;
            int x = _graphic.getX() + dx;
            int y = _graphic.getY() + dy;
            _graphic.setLocation( x, y );
            _previousPoint.setLocation( event.getPoint() );
        }

        public void mouseEntered( MouseEvent event ) {
            System.out.println( "mouseEntered on " + _graphic.getName() );
        }

        public void mouseExited( MouseEvent event ) {
            System.out.println( "mouseExited on " + _graphic.getName() );
        }

        /* You may find it useful to comment out this "chatty" method. */
        public void mouseMoved( MouseEvent event ) {
            System.out.println( "mouseMoved on " + _graphic.getName() );
        }

        public void mousePressed( MouseEvent event ) {
            System.out.println( "mousePressed on " + _graphic.getName() );
            _previousPoint.setLocation( event.getPoint() );
        }

        public void mouseReleased( MouseEvent event ) {
            System.out.println( "mouseReleased on " + _graphic.getName() );
        }
    }
}
