
 package com.aimxcel.abclearn.aimxcelgraphics.test.jcomponents;

import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.aimxcel.abclearn.aimxcelgraphics.application.AimxcelGraphicsModule;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelcomponents.AimxcelJComponent;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelTestApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.util.QuickProfiler;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.FrameSetup;



public class TestManyAimxcelJComponents extends AimxcelGraphicsModule {
    private static final int numComponents = 500;
    private static Random random = new Random();

    /**
     * @param name
     * @param clock
     */
    public TestManyAimxcelJComponents( String name, IClock clock ) {
        super( name, clock );
        setApparatusPanel( new ApparatusPanel2( clock ) );
        setModel( new BaseModel() );
        for ( int i = 0; i < numComponents; i++ ) {
            JButton but = new JButton( "button_" + i );
            AimxcelGraphic pjc = AimxcelJComponent.newInstance( getApparatusPanel(), but );
            pjc.setLocation( random.nextInt( 400 ), random.nextInt( 400 ) );
            getApparatusPanel().addGraphic( pjc );
        }
    }

    public static void main( String[] args ) {
        QuickProfiler main = new QuickProfiler( "main" );
        SwingClock clock = new SwingClock( 30, 1.0 );
        AimxcelTestApplication aimxcelApplication = new AimxcelTestApplication( args, new FrameSetup.CenteredWithSize( 600, 600 ) );

        aimxcelApplication.startApplication();
        TestManyAimxcelJComponents module = new TestManyAimxcelJComponents( "name", clock );
        aimxcelApplication.setModules( new AimxcelGraphicsModule[] { module } );
        System.out.println( "time to make & display " + numComponents + ", aimxcelJComponents= " + main + " ms" );

        QuickProfiler swing = new QuickProfiler( "frame" );
        JFrame f = new JFrame();
        JPanel conten = new JPanel();
        conten.setLayout( null );
        for ( int i = 0; i < numComponents; i++ ) {
            JButton but = new JButton( "button_" + i );

            but.setLocation( random.nextInt( 400 ), random.nextInt( 400 ) );
            but.reshape( but.getX(), but.getY(), but.getPreferredSize().width, but.getPreferredSize().height );
            conten.add( but );
        }
        f.setContentPane( conten );
        f.setSize( 600, 600 );
        f.setVisible( true );
        System.out.println( "time to make & display " + numComponents + ", normal JButtons= " + swing + " ms" );
    }
}