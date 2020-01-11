


package com.aimxcel.abclearn.aimxcelgraphics.test.examples;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.aimxcel.abclearn.aimxcelgraphics.application.AimxcelGraphicsModule;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel;
import com.aimxcel.abclearn.aimxcelgraphics.view.help.HelpItem;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ControlPanel;

public class TestAimxcelApplication {
    static class MyModule extends AimxcelGraphicsModule {

        public MyModule( String name, IClock clock, Color color ) {
            super( name, clock );

//            final ControlPanel controlPanel = new ControlPanel( this );
//            setControlPanel( controlPanel );

            setApparatusPanel( new ApparatusPanel() );
            setModel( new BaseModel() );
            JTextArea ctrl = new JTextArea( 5, 20 );
            getApparatusPanel().addGraphic( new AimxcelShapeGraphic( getApparatusPanel(), new Rectangle( 200, 100, 300, 100 ), color ) );

            final ControlPanel controlPanel = new ControlPanel( this );
            setControlPanel( controlPanel );

            addHelpItem( new HelpItem( getApparatusPanel(), "HELP!!!", 300, 200 ) );

            controlPanel.addControl( ctrl );
            final JButton button1 = new JButton( "YO!" );
            controlPanel.addControl( button1 );
            JButton button2 = new JButton( "Y'ALL!" );
            controlPanel.addControlFullWidth( button2 );
            button2.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    controlPanel.removeControl( button1 );
                }
            } );
        }

        public void activate() {
            super.activate();
        }
    }

    static class MyModule2 extends AimxcelGraphicsModule {

        public MyModule2( String name, IClock clock, Color color ) {
            super( name, clock );
            setApparatusPanel( new ApparatusPanel() );
            setModel( new BaseModel() );
            JButton ctrl = new JButton( "Click Me" );
            ControlPanel controls = new ControlPanel( this );
            controls.addControl( ctrl );
            getApparatusPanel().addGraphic( new AimxcelShapeGraphic( getApparatusPanel(), new Rectangle( 200, 100, 300, 100 ), color ) );
            setControlPanel( controls );
            JPanel monitorPanel = new JPanel();
            monitorPanel.add( new JCheckBox( "yes/no" ) );
            setMonitorPanel( monitorPanel );
        }

        public void activate() {
            super.activate();
        }
    }

    static class Photon extends SimpleObservable implements ModelElement {
        double x;
        double y;
        double speed = 1.0;
        Random rand = new Random();

        public Photon( double x, double y ) {
            this.x = x;
            this.y = y;
        }

        public void stepInTime( double dt ) {
            //            x += ( rand.nextDouble() - .5 ) * 5;
            //            y += ( rand.nextDouble() - .5 ) * 5;
            x = x + speed * dt;
            if ( x > 600 ) {
                x = 0;
            }
//                    x = ++x % 600;
            //            if( x > 100 ) {
            //                x = 100;
            //            }
            if ( y > 100 ) {
                y = 100;
            }
            if ( x < 0 ) {
                x = 0;
            }
            if ( y < 0 ) {
                y = 0;
            }
            notifyObservers();

        }
    }

    static class PhotonGraphic extends AimxcelGraphic {
        private Photon ph;

        public PhotonGraphic( ApparatusPanel ap, Photon ph ) {
            super( ap );
            this.ph = ph;
        }

        public void paint( Graphics2D g ) {
            g.setColor( Color.blue );
            g.fillRect( (int) ph.x, (int) ph.y, 2, 2 );
        }

        protected Rectangle determineBounds() {
            return new Rectangle( (int) ph.x, (int) ph.y, 2, 2 );
        }
    }

    static class MyModule3 extends AimxcelGraphicsModule {
        public MyModule3( IClock clock ) {
            super( "Test Module", clock );
            setApparatusPanel( new ApparatusPanel() );
            setModel( new BaseModel() );

            Photon ph = new Photon( 100, 100 );
            addModelElement( ph );

            AimxcelGraphic g = new PhotonGraphic( getApparatusPanel(), ph );
            addGraphic( g, 0 );

            ph.addObserver( new SimpleObserver() {
                public void update() {
                    getApparatusPanel().repaint();
                }
            } );
        }

    }

    public static void main( String[] args ) {
        SwingClock clock = new SwingClock( 30, 1.0 );
        AimxcelGraphicsModule module = new MyModule( "Testing", clock, Color.blue );
        AimxcelGraphicsModule module2 = new MyModule( "1ntht", clock, Color.red );
        AimxcelGraphicsModule module3 = new MyModule2( "Button", clock, Color.red );

        MyModule3 modulePhotons = new MyModule3( clock );
        AimxcelGraphicsModule[] m = new AimxcelGraphicsModule[] { module, module2, module3, modulePhotons };

        AimxcelTestApplication app = new AimxcelTestApplication( args );
        for ( int i = 0; i < m.length; i++ ) {
            app.addModule( m[i] );
        }
        app.startApplication();
    }

}
