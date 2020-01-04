
package com.aimxcel.abclearn.common.aimxcelcommon.tests;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrameWorkaround;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.FrameSetup;

/**
 * This class is an attempt to demonstrate the problem for which AimxcelFrameWorkaround is a workaround.
 * This version works correctly (i.e. doesn't exhibit the problem) on Windows Vista on 2.6 GHz x 2 Athlon processors
 * <p/>
 * NOTE! Set USE_WORKAROUND to compare behavior with and without the workaround.
 */
public class TestAimxcelFrameWorkaround {

    // true = use a AimxcelFrameWorkaround
    // false = use a AimxcelFrame
    private static final boolean USE_WORKAROUND = true;

    private static class TestModule extends Module {

        private JButton contentPane;

        public TestModule( final Frame owner ) {
            super( "test", new SwingClock( 30, 1 ) );

            contentPane = new JButton( "Simulation Panel Button" ) {
                protected void paintComponent( Graphics g ) {
                    try {
                        Thread.sleep( 300 );
                    }
                    catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }
                    super.paintComponent( g );
                }
            };
            setSimulationPanel( contentPane );

            getClock().addClockListener( new ClockAdapter() {
                public void clockTicked( ClockEvent clockEvent ) {
                    contentPane.invalidate();
                    contentPane.revalidate();
                    contentPane.repaint();
                    contentPane.setText( System.currentTimeMillis() + " button time!" );
                    contentPane.paintImmediately( 0, 0, contentPane.getWidth(), contentPane.getHeight() );
                }
            } );

            contentPane.addActionListener( new ActionListener() {

                public void actionPerformed( ActionEvent e ) {
                    final Dialog dialog = new Dialog( owner, false );
                    dialog.setSize( 400, 300 );
                    dialog.addWindowListener( new WindowAdapter() {

                        public void windowClosing( WindowEvent e ) {
                            dialog.dispose();
                        }
                    } );
                    Button comp = new Button( "dialog button" );

                    comp.setBackground( Color.green );
                    dialog.add( comp );
                    //                    dialog.pack();
                    dialog.setVisible( true );
                }
            } );
        }

    }

    private static class TestApplication extends AimxcelApplication {

        public TestApplication( AimxcelApplicationConfig config ) {
            super( config );
            addModule( new TestModule( getAimxcelFrame() ) );
        }

        protected AimxcelFrame createAimxcelFrame() {
            if ( USE_WORKAROUND ) {
                return new AimxcelFrameWorkaround( this );
            }
            else {
                return new AimxcelFrame( this );
            }
        }
    }

    public static void main( final String[] args ) {
        AimxcelTestApplication app = new AimxcelTestApplication( args, new FrameSetup.CenteredWithSize( 800, 600 ) );
        app.setApplicationConstructor( new ApplicationConstructor() {
            public AimxcelApplication getApplication( AimxcelApplicationConfig config ) {
                return new TestApplication( config );
            }
        } );
        app.startApplication();
    }
}


