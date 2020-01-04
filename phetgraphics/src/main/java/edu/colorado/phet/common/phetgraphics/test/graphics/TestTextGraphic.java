
 package edu.colorado.phet.common.phetgraphics.test.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;

import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AimxcelShapeGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AimxcelTextGraphic;

/**
 * Draws a rectangle centered at (100,100) for purposes of aligning
 * a AimxcelTextGraphic and its bounding box.
 */

public class TestTextGraphic {
    public static void main( String[] args ) {
        ApparatusPanel apparatusPanel = new ApparatusPanel();
        JFrame frame = new JFrame( TestTextGraphic.class.getName() );
        frame.setContentPane( apparatusPanel );
        frame.setSize( 800, 800 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        SwingUtils.centerWindowOnScreen( frame );
        frame.setVisible( true );

        AimxcelShapeGraphic phetShapeGraphic = new AimxcelShapeGraphic( apparatusPanel, new Rectangle( 98, 98, 4, 4 ), Color.red );
        apparatusPanel.addGraphic( phetShapeGraphic );

        AimxcelTextGraphic textGraphic = new AimxcelTextGraphic( apparatusPanel, new AimxcelFont( Font.BOLD, 24 ), "Test Aimxcel Text & graphics", Color.blue, 0, 0 );
        apparatusPanel.addGraphic( textGraphic );
        textGraphic.setLocation( 100, 100 );

        Rectangle bounds = textGraphic.getBounds();
        AimxcelShapeGraphic boundGraphic = new AimxcelShapeGraphic( apparatusPanel, bounds, new BasicStroke( 1 ), Color.green );
        apparatusPanel.addGraphic( boundGraphic );

        int dy = bounds.y - 100;
        System.out.println( "dy = " + dy );
    }
}