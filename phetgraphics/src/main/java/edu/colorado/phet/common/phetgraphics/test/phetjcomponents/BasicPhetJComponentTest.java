
package edu.colorado.phet.common.phetgraphics.test.phetjcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.aimxcel.abclearn.common.abclearncommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;

import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel2;
import edu.colorado.phet.common.phetgraphics.view.phetcomponents.AbcLearnJComponent;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnGraphic;
import edu.colorado.phet.common.phetgraphics.view.util.BasicGraphicsSetup;

/**
 * Created by IntelliJ IDEA.
 * User: Sam Reid
 * Date: Mar 8, 2005
 * Time: 8:49:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicAbcLearnJComponentTest {
    private JFrame frame;
    private ApparatusPanel2 ap;
    private SwingClock swingClock;

    public BasicAbcLearnJComponentTest() {
        /*Set up the application frame and apparatusPanel.*/
        frame = new JFrame( "Frame" );
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

        /**Wrap the JComponent in a AbcLearnGraphic called AbcLearnJComponent*/
        AbcLearnGraphic buttonAbcLearnJ = AbcLearnJComponent.newInstance( ap, jb );
        ap.addGraphic( buttonAbcLearnJ );

        /**Now you can decorate or manipule the AbcLearnGraphic as per usual.*/
        buttonAbcLearnJ.setCursorHand();
        buttonAbcLearnJ.scale( 1.5 );
        buttonAbcLearnJ.setLocation( 100, 100 );

        /**That's all.*/
    }

    public static void main( String[] args ) {
        new BasicAbcLearnJComponentTest().start();
    }

    private void start() {
        swingClock.start();
        frame.setVisible( true );
    }
}
