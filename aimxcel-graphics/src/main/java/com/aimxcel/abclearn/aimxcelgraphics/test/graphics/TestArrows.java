package com.aimxcel.abclearn.aimxcelgraphics.test.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelTextGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.GraphicLayerSet;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.RepaintDebugGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import com.aimxcel.abclearn.aimxcelgraphics.view.util.BasicGraphicsSetup;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.Arrow;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

public class TestArrows {
    static double theta = 0;
    private static Arrow arrow;
    private static AimxcelShapeGraphic shapeGraphic;

    final static int x0 = 400;
    final static int y0 = 400;
    final static double r = 200;

    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Arrow test" );

        final ApparatusPanel p = new ApparatusPanel();
        p.addGraphicsSetup( new BasicGraphicsSetup() );

        GraphicLayerSet compositeGraphic = new GraphicLayerSet( p );
        p.addGraphic( compositeGraphic );

        arrow = new Arrow( new Point( 200, 300 ), new Point( x0, y0 ), 100, 100, 35, .5, false );
        shapeGraphic = new AimxcelShapeGraphic( p, arrow.getShape(), Color.blue );
        SwingClock clock = new SwingClock( 30, 1.0 );
//        clock.addClockListener( new ClockAdapter() {
//            public void clockTicked( AbstractClock c, double dt ) {
//                theta += Math.PI / 128;
//                double x = x0 + r * Math.cos( theta );
//                double y = y0 + r * Math.sin( theta );
//                arrow.setTipLocation( new Point2D.Double( x, y ) );
//                shapeGraphic.setShape( new Area( arrow.getShape() ) );
//            }
//        } );
//        ClockTickListener tickListener = new ClockTickListener() {
//            public void clockTicked( ClockEvent event ) {
//                theta += Math.PI / 128;
//                double x = x0 + r * Math.cos( theta );
//                double y = y0 + r * Math.sin( theta );
//                arrow.setTipLocation( new Point2D.Double( x, y ) );
//                shapeGraphic.setShape( new Area( arrow.getShape() ) );
//            }
//        };
//        clock.addClockTickListener( tickListener );
        clock.addClockListener( new MyListener() );
        shapeGraphic.setCursorHand();
        shapeGraphic.addTranslationListener( new TranslationListener() {
            public void translationOccurred( TranslationEvent translationEvent ) {
                arrow.setTailLocation( translationEvent.getMouseEvent().getPoint() );
            }
        } );

        Font font = new AimxcelFont( Font.BOLD, 24 );
        AimxcelTextGraphic textGraphic = new AimxcelTextGraphic( p, font, "Hello Aimxcel", Color.blue, 200, 100 );
        compositeGraphic.addGraphic( textGraphic, 10 );
        compositeGraphic.addGraphic( shapeGraphic, 30 );
        frame.setContentPane( p );
        frame.setSize( 600, 600 );
        frame.setVisible( true );
        compositeGraphic.addGraphic( new RepaintDebugGraphic( p, clock ) );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        clock.start();
    }

    public static class MyListener extends ClockAdapter {

        public void clockTicked( ClockEvent event ) {
            theta += Math.PI / 128;
//            System.out.println( "event = " + event );
            double x = x0 + r * Math.cos( theta );
            double y = y0 + r * Math.sin( theta );
            arrow.setTipLocation( new Point2D.Double( x, y ) );
            shapeGraphic.setShape( new Area( arrow.getShape() ) );
        }
    }
}
