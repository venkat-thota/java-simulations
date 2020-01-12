
package com.aimxcel.abclearn.aimxcelgraphics.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

import com.aimxcel.abclearn.aimxcelgraphics.application.AimxcelGraphicsModule;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelTextGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;


public class TestOffscreenBufferAlpha {

    private static final boolean USE_OFFSCREEN_BUFFER = true;

    public static void main( String args[] ) throws IOException {
        TestOffscreenBufferAlpha test = new TestOffscreenBufferAlpha( args );
    }

    public TestOffscreenBufferAlpha( String[] args ) throws IOException {

        // Set up the application descriptor.
        IClock clock = new SwingClock( 40, 1 );
        AimxcelGraphicsModule module = new TestModule( clock );

        // Create and start the application.
        AimxcelTestApplication app = new AimxcelTestApplication( args );
        app.addModule( module );
        app.startApplication();
    }

    private class TestModule extends AimxcelGraphicsModule {
        public TestModule( IClock clock ) {
            super( "Test Module", clock );

            // Model
            BaseModel model = new BaseModel();
            setModel( model );

            // Apparatus Panel
            ApparatusPanel2 apparatusPanel = new ApparatusPanel2( clock );
            apparatusPanel.setBackground( Color.WHITE );
            apparatusPanel.setUseOffscreenBuffer( USE_OFFSCREEN_BUFFER );
            setApparatusPanel( apparatusPanel );

            // Rectangle
            AimxcelShapeGraphic rectangleGraphic = new AimxcelShapeGraphic( apparatusPanel );
            rectangleGraphic.setShape( new Rectangle( 0, 0, 50, 50 ) );
            rectangleGraphic.setPaint( Color.YELLOW );
            rectangleGraphic.setLocation( 100, 100 );
            apparatusPanel.addGraphic( rectangleGraphic, 1 );

            // Circle
            final AimxcelShapeGraphic circleGraphic = new AimxcelShapeGraphic( apparatusPanel );
            circleGraphic.setShape( new Ellipse2D.Double( -35, -35, 70, 70 ) );
            circleGraphic.setPaint( new Color( 255, 0, 0, 100 ) ); // white with alpha
            circleGraphic.setLocation( 100, 100 );
            apparatusPanel.addGraphic( circleGraphic, 2 );

            // Instructions
            Font font = new Font( null, Font.PLAIN, 14 );
            String message = "The red circle should be transparent.";
            AimxcelTextGraphic textGraphic = new AimxcelTextGraphic( apparatusPanel, font, message, Color.BLACK );
            textGraphic.setLocation( 10, 30 );
            apparatusPanel.addGraphic( textGraphic, 3 );
        }
    }
}
