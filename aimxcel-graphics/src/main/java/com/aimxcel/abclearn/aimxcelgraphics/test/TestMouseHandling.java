
 
package com.aimxcel.abclearn.aimxcelgraphics.test;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.event.MouseInputAdapter;

import com.aimxcel.abclearn.aimxcelgraphics.application.AimxcelGraphicsModule;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;


public class TestMouseHandling {

    /**
     * main
     */
    public static void main( String args[] ) throws IOException {
        TestMouseHandling test = new TestMouseHandling( args );
    }

    /**
     * TestMouseHandling creates the Aimxcel application.
     */
    public TestMouseHandling( String[] args ) throws IOException {

        // Set up the application descriptor.
        IClock clock = new SwingClock( 40, 1 );
        AimxcelGraphicsModule module = new TestModule( clock );

        // Create and start the application.
        AimxcelTestApplication app = new AimxcelTestApplication( args );
        app.addModule( module );
        app.startApplication();
    }

    /**
     * TestModule sets up a module with two draggable shapes.
     */
    private class TestModule extends AimxcelGraphicsModule {
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
            AimxcelShapeGraphic yellowGraphic = new AimxcelShapeGraphic( apparatusPanel );
            yellowGraphic.setName( "yellow" );
            yellowGraphic.setShape( new Rectangle( 0, 0, 50, 50 ) );
            yellowGraphic.setPaint( Color.YELLOW );
            yellowGraphic.setLocation( 100, 100 );
            yellowGraphic.setCursorHand();
            yellowGraphic.addMouseInputListener( new MouseHandler( yellowGraphic ) );
            apparatusPanel.addGraphic( yellowGraphic, 1 );

            // Red rectangle
            final AimxcelShapeGraphic redGraphic = new AimxcelShapeGraphic( apparatusPanel );
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

        private AimxcelGraphic _graphic;
        private Point _previousPoint;

        public MouseHandler( AimxcelGraphic graphic ) {
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
