

/*  */
package com.aimxcel.abclearn.common.abclearncommon.tests;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnTestApplication;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnLookAndFeel;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;

/**
 * User: Sam Reid
 * Date: Jun 6, 2006
 * Time: 7:32:47 PM
 */

public class TestAbcLearnLookAndFeel extends AbcLearnTestApplication {
    public TestAbcLearnLookAndFeel( String[] args ) {
        super( args );
        addModule( new TestAbcLearnLookAndFeelModule( "Module A" ) );
        addModule( new TestAbcLearnLookAndFeelModule( "Module 1" ) );
    }

    static class TestAbcLearnLookAndFeelExample extends AbcLearnLookAndFeel {
        public TestAbcLearnLookAndFeelExample() {
            setBackgroundColor( Color.blue );
            setForegroundColor( Color.white );
            setFont( new AbcLearnFont( Font.BOLD, 24 ) );
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
        new TestAbcLearnLookAndFeel( args ).startApplication();
    }
}
