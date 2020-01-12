
package com.aimxcel.abclearn.aimxcelgraphics.test.graphics;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelTextGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import com.aimxcel.abclearn.aimxcelgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;


public class TestApparatusPanel2Resize {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "Test AP2" );
        final BaseModel model = new BaseModel();
        SwingClock clock = new SwingClock( 30, 1.0 );
        ApparatusPanel2 panel = new ApparatusPanel2( clock );
        JLabel comp = new JLabel( "Label" );
        comp.reshape( 250, 250, comp.getPreferredSize().width, comp.getPreferredSize().height );
        panel.add( comp );
        frame.setContentPane( panel );
        frame.setSize( 600, 600 );
        frame.show();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        Rectangle rectangle = new Rectangle( 0, 0, 200, 200 );
        final AimxcelShapeGraphic aimxcelShapeGraphic = new AimxcelShapeGraphic( panel, rectangle, Color.blue );
        aimxcelShapeGraphic.setLocation( 50, 50 );

        panel.addGraphic( aimxcelShapeGraphic );
        aimxcelShapeGraphic.addTranslationListener( new TranslationListener() {
            public void translationOccurred( TranslationEvent translationEvent ) {
                aimxcelShapeGraphic.setLocation( translationEvent.getX(), translationEvent.getY() );
            }
        } );

        AimxcelTextGraphic textGraphic = new AimxcelTextGraphic( panel, new AimxcelFont( 0, 24 ), "Hello", Color.black, 0, 0 );
        textGraphic.setLocation( 400, 100 );
        panel.addGraphic( textGraphic );

        JButton button = new JButton( "Test button" );
        button.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.out.println( "Pressed" );
            }
        } );
        panel.setLayout( null );

        button.reshape( 100, 100, button.getPreferredSize().width, button.getPreferredSize().height );
        panel.add( button );


        aimxcelShapeGraphic.setCursorHand();
        clock.addClockListener( new ClockAdapter() {
            public void clockTicked( ClockEvent clockEvent ) {
                model.update( clockEvent );
            }
        } );
        clock.start();
    }
}
