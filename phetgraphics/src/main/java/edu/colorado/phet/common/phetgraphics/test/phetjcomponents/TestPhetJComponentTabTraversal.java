
package edu.colorado.phet.common.phetgraphics.test.phetjcomponents;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JTextField;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnTestApplication;
import com.aimxcel.abclearn.common.abclearncommon.model.BaseModel;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;

import edu.colorado.phet.common.phetgraphics.application.AbcLearnGraphicsModule;
import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel2;
import edu.colorado.phet.common.phetgraphics.view.phetcomponents.AbcLearnJComponent;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.HTMLGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnGraphic;

/**
 * Tests tab traversal of Swing components that are wrapped by AbcLearnJComponent.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class TestAbcLearnJComponentTabTraversal {

    public static void main( String args[] ) throws IOException {
        TestAbcLearnJComponentTabTraversal test = new TestAbcLearnJComponentTabTraversal( args );
    }

    public TestAbcLearnJComponentTabTraversal( String[] args ) throws IOException {

        IClock clock = new SwingClock( 40, 1 );
        AbcLearnTestApplication app = new AbcLearnTestApplication( args );

        // Add modules.
        AbcLearnGraphicsModule module = new TestModule( clock );
        app.setModules( new AbcLearnGraphicsModule[] { module } );

        // Start the app.
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
            setApparatusPanel( apparatusPanel );

            Font font = new AbcLearnFont( Font.PLAIN, 14 );

            // Instructions
            String html = "<html>Click in a text field.<br>Then use Tab or Shift-Tab to move between text fields.</html>";
            HTMLGraphic instructions = new HTMLGraphic( apparatusPanel, font, html, Color.BLACK );
            instructions.setLocation( 15, 15 );
            apparatusPanel.addGraphic( instructions );

            // JTextFields, wrapped by AbcLearnJComponent.
            for ( int i = 0; i < 5; i++ ) {
                JTextField textField = new JTextField();
                textField.setFont( font );
                textField.setColumns( 3 );
                AbcLearnGraphic textFieldGraphic = AbcLearnJComponent.newInstance( apparatusPanel, textField );
                textFieldGraphic.setLocation( 20 + ( i * 60 ), 100 );
                apparatusPanel.addGraphic( textFieldGraphic );
            }
        }
    }
}
