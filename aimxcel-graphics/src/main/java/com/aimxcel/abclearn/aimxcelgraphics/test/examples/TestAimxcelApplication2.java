
package com.aimxcel.abclearn.aimxcelgraphics.test.examples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.HTMLGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ControlPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

public class TestAimxcelApplication2 {
    private static AimxcelTestApplication app;

    static class TestApparatusPanel extends ApparatusPanel {
        public TestApparatusPanel() {
//            HTMLGraphic htmlGraphic=new HTMLGraphic( this, getFont(), "Size="+Color.blue);
            Font font = new AimxcelFont( Font.BOLD, 22 );
            final HTMLGraphic htmlGraphic = new HTMLGraphic( this, font, "Size=" + getSize(), Color.blue );
            addGraphic( htmlGraphic, Double.POSITIVE_INFINITY );
            htmlGraphic.setLocation( 0, 100 );
            addComponentListener( new ComponentAdapter() {
                public void componentResized( ComponentEvent e ) {
                    htmlGraphic.setHtml( "Size=" + TestApparatusPanel.this.getSize() );
                }

                public void componentShown( ComponentEvent e ) {
                    htmlGraphic.setHtml( "Size=" + TestApparatusPanel.this.getSize() );
                }
            } );

        }
    }

    static class MyModule extends AimxcelGraphicsModule {
        private int count;

        public MyModule( String name, IClock clock, Color color ) {
            super( name, clock );


            setApparatusPanel( new TestApparatusPanel() );
            setModel( new BaseModel() );
            final JTextArea ctrl = new JTextArea( 5, 20 );
            ctrl.setText( "Click here to change column count." );
            getApparatusPanel().addGraphic( new AimxcelShapeGraphic( getApparatusPanel(), new Rectangle( 200, 100, 300, 100 ), color ) );

            final ControlPanel controlPanel = new ControlPanel( this );
            setControlPanel( controlPanel );

            addHelpItem( new HelpItem( getApparatusPanel(), "HELP!!!", 300, 200 ) );

            controlPanel.addControl( ctrl );
            count = 0;
            ctrl.addMouseListener( new MouseAdapter() {
                public void mousePressed( MouseEvent e ) {
                    count++;
                    int dx = count % 2 == 0 ? -5 : 5;
                    ctrl.setColumns( ctrl.getColumns() + dx );
                    ctrl.validate();
                    ctrl.doLayout();
                    controlPanel.validate();
                    controlPanel.doLayout();
                    app.getAimxcelFrame().invalidate();
                    app.getAimxcelFrame().validate();
                    app.getAimxcelFrame().doLayout();
                }
            } );
            final JButton button1 = new JButton( "YO!" );
            controlPanel.addControl( button1 );
            JButton button2 = new JButton( "Y'ALL!" );
            controlPanel.addControl( button2 );
            button2.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    controlPanel.addControl( button1 );
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
            setApparatusPanel( new TestApparatusPanel() );
            setModel( new BaseModel() );
            JButton ctrl = new JButton( "Click Me" );
            ControlPanel controls = new ControlPanel( this );
            controls.addControl( ctrl );
            getApparatusPanel().addGraphic( new AimxcelShapeGraphic( getApparatusPanel(), new Rectangle( 200, 100, 300, 100 ), color ) );
            setControlPanel( controls );
            JPanel monitorPanel = new JPanel();
            monitorPanel.add( new JCheckBox( "yes/no" ) );
            super.setMonitorPanel( monitorPanel );


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
            x = x + speed * dt;
            if ( x > 600 ) {
                x = 0;
            }
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
            setApparatusPanel( new TestApparatusPanel() );
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
        AimxcelGraphicsModule module = new MyModule( "Testing", clock, Color.green );
        AimxcelGraphicsModule module2 = new MyModule( "1ntht", clock, Color.red );
        AimxcelGraphicsModule module3 = new MyModule2( "Button", clock, Color.red );

        MyModule3 module4 = new MyModule3( clock );
        AimxcelGraphicsModule[] m = new AimxcelGraphicsModule[] { module, module2, module3, module4 };

        app = new AimxcelTestApplication( args );
        app.setModules( m );
        app.startApplication();

    }

}
