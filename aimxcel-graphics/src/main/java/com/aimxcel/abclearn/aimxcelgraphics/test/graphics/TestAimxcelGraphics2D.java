
 package com.aimxcel.abclearn.aimxcelgraphics.test.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;

/**
 * TestAimxcelGraphics2D
 *
 * @author Ron LeMaster
 * @version $Revision$
 */
public class TestAimxcelGraphics2D {

    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Test AimxcelGraphics2D" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        SwingClock clock = new SwingClock( 1, 25 );
        ApparatusPanel ap2 = new ApparatusPanel2( clock );
        clock.start();
        ap2.setPreferredSize( new Dimension( 400, 300 ) );
        frame.setContentPane( ap2 );
        frame.pack();
        frame.setVisible( true );

        AimxcelGraphic rect = new TestGraphic( ap2 );
        ap2.addGraphic( rect );

        ap2.setBackground( Color.black );

        ap2.revalidate();
        ap2.paintImmediately( ap2.getBounds() );
    }

    static class TestGraphic extends AimxcelGraphic {
        private Rectangle rect = new Rectangle( 50, 100, 100, 50 );
        Rectangle rect2 = new Rectangle( 150, 200, 100, 50 );

        public TestGraphic( Component ap2 ) {
            super( ap2 );
//            AimxcelShapeGraphic psg = new AimxcelShapeGraphic( ap2, new Rectangle( 50, 100, 100, 50 ), Color.red );
//            rect.setLocation( 20, 20 );
//            ap2.addGraphic( psg );
//            AimxcelShapeGraphic rect2 = new AimxcelShapeGraphic( ap2, new Rectangle( 150, 200, 100, 50 ), Color.blue );
//            ap2.addGraphic( rect2 );

        }

        protected Rectangle determineBounds() {
            return getComponent().getBounds();
        }

        public void paint( Graphics2D g2 ) {
            saveGraphicsState( g2 );

            g2.setColor( Color.red );
            g2.draw( rect );
            g2.setColor( Color.blue );
            g2.draw( rect2 );

            restoreGraphicsState();
        }
    }
}
