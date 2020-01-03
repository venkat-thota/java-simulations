
package com.aimxcel.abclearn.common.abclearncommon.tests;

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

import com.aimxcel.abclearn.common.abclearncommon.application.ApplicationConstructor;
import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnTestApplication;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnFrame;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnFrameWorkaround;
import com.aimxcel.abclearn.common.abclearncommon.view.util.FrameSetup;

/**
 * This class is an attempt to demonstrate the problem for which AbcLearnFrameWorkaround is a workaround.
 * This version works correctly (i.e. doesn't exhibit the problem) on Windows Vista on 2.6 GHz x 2 Athlon processors
 * <p/>
 * NOTE! Set USE_WORKAROUND to compare behavior with and without the workaround.
 */
public class TestAbcLearnFrameWorkaround {

    // true = use a AbcLearnFrameWorkaround
    // false = use a AbcLearnFrame
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

    private static class TestApplication extends AbcLearnApplication {

        public TestApplication( AbcLearnApplicationConfig config ) {
            super( config );
            addModule( new TestModule( getAbcLearnFrame() ) );
        }

        protected AbcLearnFrame createAbcLearnFrame() {
            if ( USE_WORKAROUND ) {
                return new AbcLearnFrameWorkaround( this );
            }
            else {
                return new AbcLearnFrame( this );
            }
        }
    }

    public static void main( final String[] args ) {
        AbcLearnTestApplication app = new AbcLearnTestApplication( args, new FrameSetup.CenteredWithSize( 800, 600 ) );
        app.setApplicationConstructor( new ApplicationConstructor() {
            public AbcLearnApplication getApplication( AbcLearnApplicationConfig config ) {
                return new TestApplication( config );
            }
        } );
        app.startApplication();
    }
}


