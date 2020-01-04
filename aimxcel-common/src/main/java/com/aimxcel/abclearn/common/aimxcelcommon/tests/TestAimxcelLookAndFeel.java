

package com.aimxcel.abclearn.common.aimxcelcommon.tests;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLookAndFeel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;


public class TestAimxcelLookAndFeel extends AimxcelTestApplication {
    public TestAimxcelLookAndFeel( String[] args ) {
        super( args );
        addModule( new TestAimxcelLookAndFeelModule( "Module A" ) );
        addModule( new TestAimxcelLookAndFeelModule( "Module 1" ) );
    }

    static class TestAimxcelLookAndFeelExample extends AimxcelLookAndFeel {
        public TestAimxcelLookAndFeelExample() {
            setBackgroundColor( Color.blue );
            setForegroundColor( Color.white );
            setFont( new AimxcelFont( Font.BOLD, 24 ) );
        }
    }

    static class TestAimxcelLookAndFeelModule extends Module {
        public TestAimxcelLookAndFeelModule( String name ) {
            super( name, new SwingClock( 30, 1 ) );
            setSimulationPanel( new JLabel( "Hello" ) );
        }
    }

    public static void main( String[] args ) {
        new TestAimxcelLookAndFeelExample().initLookAndFeel();
        new TestAimxcelLookAndFeel( args ).startApplication();
    }
}
