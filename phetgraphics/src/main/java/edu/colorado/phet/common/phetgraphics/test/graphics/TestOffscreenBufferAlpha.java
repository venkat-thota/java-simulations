
package edu.colorado.phet.common.phetgraphics.test.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnTestApplication;
import com.aimxcel.abclearn.common.abclearncommon.model.BaseModel;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.abclearncommon.view.util.FrameSetup;

import edu.colorado.phet.common.phetgraphics.application.AbcLearnGraphicsModule;
import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel2;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnShapeGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnTextGraphic;

/**
 * TestOffscreenBufferAlpha tests support for alpha blending in the
 * offscreen buffer of ApparatusPanel2.
 * <p/>
 * If this buffer is a BufferedImage of TYPE_INT_RGB, then alpha blending
 * does not occur on Macintosh. Using TYPE_INT_RGBA results in a performance
 * hit (on all platforms?)
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 * @version $Revision$
 */
public class TestOffscreenBufferAlpha {

    private static final boolean USE_OFFSCREEN_BUFFER = true;

    public static void main( String args[] ) throws IOException {
        TestOffscreenBufferAlpha test = new TestOffscreenBufferAlpha( args );
    }

    public TestOffscreenBufferAlpha( String[] args ) throws IOException {

        // Set up the application descriptor.
        String title = "TestOffscreenBufferAlpha";
        FrameSetup frameSetup = new FrameSetup.CenteredWithSize( 300, 300 );
        IClock clock = new SwingClock( 40, 1 );
        AbcLearnGraphicsModule module = new TestModule( clock );

        // Create and start the application.
        AbcLearnTestApplication app = new AbcLearnTestApplication( args );
        app.addModule( module );
        app.startApplication();
    }

    private class TestModule extends AbcLearnGraphicsModule {
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
            AbcLearnShapeGraphic rectangleGraphic = new AbcLearnShapeGraphic( apparatusPanel );
            rectangleGraphic.setShape( new Rectangle( 0, 0, 50, 50 ) );
            rectangleGraphic.setPaint( Color.YELLOW );
            rectangleGraphic.setLocation( 100, 100 );
            apparatusPanel.addGraphic( rectangleGraphic, 1 );

            // Circle
            final AbcLearnShapeGraphic circleGraphic = new AbcLearnShapeGraphic( apparatusPanel );
            circleGraphic.setShape( new Ellipse2D.Double( -35, -35, 70, 70 ) );
            circleGraphic.setPaint( new Color( 255, 0, 0, 100 ) ); // white with alpha
            circleGraphic.setLocation( 100, 100 );
            apparatusPanel.addGraphic( circleGraphic, 2 );

            // Instructions
            Font font = new Font( null, Font.PLAIN, 14 );
            String message = "The red circle should be transparent.";
            AbcLearnTextGraphic textGraphic = new AbcLearnTextGraphic( apparatusPanel, font, message, Color.BLACK );
            textGraphic.setLocation( 10, 30 );
            apparatusPanel.addGraphic( textGraphic, 3 );
        }
    }
}
