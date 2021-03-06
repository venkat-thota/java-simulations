
package com.aimxcel.abclearn.aimxcelgraphics.test.jcomponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelcomponents.AimxcelJComponent;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.util.BasicGraphicsSetup;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;


public class AimxcelJComponentContainerTest {
    private JFrame frame;
    private ApparatusPanel2 ap;
    private SwingClock swingClock;

    public AimxcelJComponentContainerTest() {
        /*Set up the application frame and apparatusPanel.*/
        frame = new JFrame( "Frame" );
        AimxcelJComponent.init( frame );
        swingClock = new SwingClock( 30, 1.0 );
        ap = new ApparatusPanel2( swingClock );
        ap.addGraphicsSetup( new BasicGraphicsSetup() );

        frame.setContentPane( ap );
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        swingClock.addClockListener( new ClockAdapter() {
            public void clockTicked( ClockEvent event ) {
                ap.handleUserInput();
                ap.paint();
            }
        } );

        /**Create a JButton as you normally would. This can include fonts, foreground, action listeners, whatever.
         * (intrinsic data).
         * However, locations (and other extrinsic data like that) will be ignored.
         */
        JButton jb = new JButton( "JButton" );
        jb.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.out.println( "You clicked it at time=" + System.currentTimeMillis() );
            }
        } );

        /**That's all.*/

        JPanel jPanel = new JPanel();
//        jPanel.setLocation( 200,200);
        jPanel.setLayout( new FlowLayout() );
//        jPanel.setLayout( new BoxLayout( jPanel, BoxLayout.Y_AXIS ) );
        jPanel.setBorder( BorderFactory.createTitledBorder( "hello" ) );
        jPanel.add( new JTextField( 8 ) );
        JButton comp = new JButton( "mybutton" );
        comp.addComponentListener( new ComponentAdapter() {
            public void componentHidden( ComponentEvent e ) {
                System.out.println( "e = " + e );
            }

            public void componentMoved( ComponentEvent e ) {
                System.out.println( "e = " + e );
            }

            public void componentResized( ComponentEvent e ) {
                System.out.println( "e = " + e );
            }

            public void componentShown( ComponentEvent e ) {
                System.out.println( "e = " + e );
            }
        } );
        jPanel.add( comp );

        JPanel container2 = new JPanel() {
//            protected void paintChildren( Graphics g ) {
////                super.paintChildren( g );
//            }
        };
        container2.setLayout( new BorderLayout() );
        JLabel center = new JLabel( "Center" );
        container2.add( center, BorderLayout.CENTER );
        jPanel.add( container2 );

        JButton northButton = new JButton( "Northbutton" );
        container2.add( northButton, BorderLayout.NORTH );
        JButton southButton = new JButton( "Souhthbutton" );
        container2.add( southButton, BorderLayout.SOUTH );
        container2.setBorder( BorderFactory.createTitledBorder( "BorderLayout" ) );

        AimxcelGraphic panelComponent = AimxcelJComponent.newInstance( ap, jPanel );
        ap.addGraphic( panelComponent );

        JSpinner spinner = new JSpinner( new SpinnerNumberModel( 5, 0, 10, 1 ) );
        spinner.setBorder( BorderFactory.createTitledBorder( "Spinner" ) );
        AimxcelGraphic pj = AimxcelJComponent.newInstance( ap, spinner );
        ap.addGraphic( pj );
        pj.setLocation( 100, 100 );

        JTextField topField = new JTextField( "TopField" );
        AimxcelGraphic topFieldGraphic = AimxcelJComponent.newInstance( ap, topField );
        topFieldGraphic.setLocation( 100, 300 );
        ap.addGraphic( topFieldGraphic );

        ap.addMouseListener( new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                ap.requestFocus();
            }
        } );
    }

    public static void main( String[] args ) {
//        AimxcelJComponent.newInstance().start();
        new AimxcelJComponentContainerTest().start();
    }

    private void start() {
        swingClock.start();
        frame.setVisible( true );
        ap.requestFocus();
    }
}
