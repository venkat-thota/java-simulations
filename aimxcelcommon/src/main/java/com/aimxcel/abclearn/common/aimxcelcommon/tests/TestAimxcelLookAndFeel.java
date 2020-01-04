

/*  */
package com.aimxcel.abclearn.common.aimxcelcommon.tests;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLookAndFeel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

/**
 * User: Sam Reid
 * Date: Jun 6, 2006
 * Time: 7:32:47 PM
 */

public class TestAimxcelLookAndFeel extends AimxcelTestApplication {
    public TestAimxcelLookAndFeel( String[] args ) {
        super( args );
        addModule( new TestAbcLearnLookAndFeelModule( "Module A" ) );
        addModule( new TestAbcLearnLookAndFeelModule( "Module 1" ) );
    }

    static class TestAbcLearnLookAndFeelExample extends AimxcelLookAndFeel {
        public TestAbcLearnLookAndFeelExample() {
            setBackgroundColor( Color.blue );
            setForegroundColor( Color.white );
            setFont( new AimxcelFont( Font.BOLD, 24 ) );
        }
    }

    static class TestAbcLearnLookAndFeelModule extends Module {
        public TestAbcLearnLookAndFeelModule( String name ) {
            super( name, new SwingClock( 30, 1 ) );
            setSimulationPanel( new JLabel( "Hello" ) );
        }
    }

    public static void main( String[] args ) {
        new TestAbcLearnLookAndFeelExample().initLookAndFeel();
        new TestAimxcelLookAndFeel( args ).startApplication();
    }
}
