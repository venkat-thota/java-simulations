
package com.aimxcel.abclearn.aimxcelgraphics.test.jcomponents;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JTextField;

import com.aimxcel.abclearn.aimxcelgraphics.application.AimxcelGraphicsModule;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetcomponents.AimxcelJComponent;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.HTMLGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;


public class TestAimxcelJComponentTabTraversal {

    public static void main( String args[] ) throws IOException {
        TestAimxcelJComponentTabTraversal test = new TestAimxcelJComponentTabTraversal( args );
    }

    public TestAimxcelJComponentTabTraversal( String[] args ) throws IOException {

        IClock clock = new SwingClock( 40, 1 );
        AimxcelTestApplication app = new AimxcelTestApplication( args );

        // Add modules.
        AimxcelGraphicsModule module = new TestModule( clock );
        app.setModules( new AimxcelGraphicsModule[] { module } );

        // Start the app.
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
            setApparatusPanel( apparatusPanel );

            Font font = new AimxcelFont( Font.PLAIN, 14 );

            // Instructions
            String html = "<html>Click in a text field.<br>Then use Tab or Shift-Tab to move between text fields.</html>";
            HTMLGraphic instructions = new HTMLGraphic( apparatusPanel, font, html, Color.BLACK );
            instructions.setLocation( 15, 15 );
            apparatusPanel.addGraphic( instructions );

            // JTextFields, wrapped by AimxcelJComponent.
            for ( int i = 0; i < 5; i++ ) {
                JTextField textField = new JTextField();
                textField.setFont( font );
                textField.setColumns( 3 );
                AimxcelGraphic textFieldGraphic = AimxcelJComponent.newInstance( apparatusPanel, textField );
                textFieldGraphic.setLocation( 20 + ( i * 60 ), 100 );
                apparatusPanel.addGraphic( textFieldGraphic );
            }
        }
    }
}
