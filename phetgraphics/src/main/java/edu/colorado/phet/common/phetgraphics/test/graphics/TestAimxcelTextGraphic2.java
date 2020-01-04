
package edu.colorado.phet.common.phetgraphics.test.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.FrameSetup;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

import edu.colorado.phet.common.phetgraphics.application.AimxcelGraphicsModule;
import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel2;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AimxcelGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AimxcelTextGraphic2;

/**
 * TestAimxcelTextGraphic2 test the bounds, justifications, and registration point
 * features of AimxcelTextGraphic22.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 * @version $Revision$
 */
public class TestAimxcelTextGraphic2 {

    public static void main( String args[] ) throws IOException {
        TestAimxcelTextGraphic2 test = new TestAimxcelTextGraphic2( args );
    }

    public TestAimxcelTextGraphic2( String[] args ) throws IOException {

        // Clock
        double timeStep = 1;
        double frameRate = 25; // fps
        int waitTime = (int) ( 1000 / frameRate ); // milliseconds
        IClock clock = new SwingClock( waitTime, timeStep );

        FrameSetup frameSetup = new FrameSetup.CenteredWithSize( 1024, 768 );

        AimxcelTestApplication app = new AimxcelTestApplication( args, frameSetup );

        AimxcelGraphicsModule module = new TestModule( clock );

        app.setModules( new AimxcelGraphicsModule[] { module } );

        app.startApplication();
    }

    private class TestModule extends AimxcelGraphicsModule {

        public TestModule( IClock clock ) {
            super( "TestModule", clock );

            int textLayer = 1;
            int debugLayer = 2;

            // Model
            BaseModel model = new BaseModel();
            setModel( model );

            // Apparatus Panel
            ApparatusPanel2 apparatusPanel = new ApparatusPanel2( clock );
            apparatusPanel.setBackground( Color.BLACK );
            setApparatusPanel( apparatusPanel );

            // Bounds debugger
            BoundsDebugger boundsDebugger = new BoundsDebugger( apparatusPanel );
            apparatusPanel.addGraphic( boundsDebugger, debugLayer );

            // Title
            AimxcelTextGraphic2 title = new AimxcelTextGraphic2( apparatusPanel );
            title.setFont( new AimxcelFont( Font.PLAIN, 24 ) );
            title.setColor( Color.YELLOW );
            title.setText( "AimxcelTextGraphic2: test of bounds, justifications, and registration points" );
            title.setLocation( 50, 50 );
            apparatusPanel.addGraphic( title, textLayer );

            // A bunch of AimxcelTextGraphic2s

            Font font = new AimxcelFont( Font.PLAIN, 18 );
            Color color = Color.LIGHT_GRAY;

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = NORTH_WEST", color );
                t.setLocation( 50, 150 );
                t.setJustification( AimxcelTextGraphic2.NORTH_WEST );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = NORTH", color );
                t.setLocation( 500, 150 );
                t.setJustification( AimxcelTextGraphic2.NORTH );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = NORTH_EAST", color );
                t.setLocation( 900, 150 );
                t.setJustification( AimxcelTextGraphic2.NORTH_EAST );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = WEST", color );
                t.setLocation( 50, 300 );
                t.setJustification( AimxcelTextGraphic2.WEST );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = CENTER", color );
                t.setLocation( 500, 300 );
                t.setJustification( AimxcelTextGraphic2.CENTER );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = EAST", color );
                t.setLocation( 900, 300 );
                t.setJustification( AimxcelTextGraphic2.EAST );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = SOUTH_WEST", color );
                t.setLocation( 50, 450 );
                t.setJustification( AimxcelTextGraphic2.SOUTH_WEST );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = SOUTH", color );
                t.setLocation( 500, 450 );
                t.setJustification( AimxcelTextGraphic2.SOUTH );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = SOUTH_EAST", color );
                t.setLocation( 900, 450 );
                t.setJustification( AimxcelTextGraphic2.SOUTH_EAST );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "justification = NONE", color );
                t.setLocation( 50, 600 );
                t.setJustification( AimxcelTextGraphic2.NONE );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }

            {
                AimxcelTextGraphic2 t = new AimxcelTextGraphic2( apparatusPanel, font, "custom registration point = bottom center", color );
                t.setLocation( 600, 600 );
                t.setRegistrationPoint( t.getWidth() / 2, t.getHeight() );
                apparatusPanel.addGraphic( t, textLayer );
                boundsDebugger.add( t );
            }
        }
    }

    /**
     * BoundsDebugger displays the bounds and locations of a set of AimxcelGraphics.
     * It is intended for use in debugging phetcommon and client applications.
     */
    public class BoundsDebugger extends AimxcelGraphic {

        //----------------------------------------------------------------------------
        // Instance data
        //----------------------------------------------------------------------------

        final Color _boundsColor = Color.GREEN;
        final Stroke _boundsStroke = new BasicStroke( 1f );
        final Color _locationColor = Color.RED;
        final Stroke _locationStroke = new BasicStroke( 1f );
        final Dimension _locationSize = new Dimension( 10, 10 );
        private ArrayList _graphics = new ArrayList();

        //----------------------------------------------------------------------------
        // Constructors
        //----------------------------------------------------------------------------

        public BoundsDebugger( Component component ) {
            super( component );
            setIgnoreMouse( true );
        }

        public void add( AimxcelGraphic graphic ) {
            _graphics.add( graphic );
        }

        public void remove( AimxcelGraphic graphic ) {
            _graphics.remove( graphic );
        }

        protected Rectangle determineBounds() {
            Rectangle bounds = null;
            for ( int i = 0; i < _graphics.size(); i++ ) {
                AimxcelGraphic graphic = (AimxcelGraphic) _graphics.get( i );
                if ( bounds == null ) {
                    bounds = new Rectangle( graphic.getBounds() );
                }
                else {
                    bounds.union( graphic.getBounds() );
                }
            }
            return bounds;
        }

        public void paint( Graphics2D g2 ) {
            if ( isVisible() ) {
                saveGraphicsState( g2 );
                for ( int i = 0; i < _graphics.size(); i++ ) {
                    // Get the rendering details for the next graphic.
                    AimxcelGraphic graphic = (AimxcelGraphic) _graphics.get( i );
                    Rectangle bounds = graphic.getBounds();
                    Point location = graphic.getLocation();

                    // Outline the bounds.
                    g2.setStroke( _boundsStroke );
                    g2.setPaint( _boundsColor );
                    g2.draw( bounds );

                    // Convert the graphic's location to screen coordinates.
                    AffineTransform transform = getNetTransform();
                    Point2D transformedLocation = new Point2D.Double();
                    transform.transform( location, transformedLocation );
                    int x = (int) transformedLocation.getX();
                    int y = (int) transformedLocation.getY();

                    // Draw a cross, centered at the graphic's location.
                    g2.setStroke( _locationStroke );
                    g2.setPaint( _locationColor );
                    g2.drawLine( x, y - _locationSize.height / 2, x, y + _locationSize.height / 2 );
                    g2.drawLine( x - _locationSize.width / 2, y, x + _locationSize.width / 2, y );
                }
                restoreGraphicsState();
            }
        }
    }
}