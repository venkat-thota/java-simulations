
package com.aimxcel.abclearn.core.aimxcelcore.test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearValueControl;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolox.pswing.PSwing;

/**
 * @author Sam Reid
 */
public class TestLinearValueControlPSwing {
    private final JFrame frame;

    public TestLinearValueControlPSwing() {
        frame = new JFrame( getClass().getName() );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        final AimxcelPCanvas contentPane = new AimxcelPCanvas();
        contentPane.setWorldTransformStrategy( new AimxcelPCanvas.CenteringBoxStrategy( contentPane, new PDimension( 800, 600 ) ) );

        LinearValueControl control = new LinearValueControl( 0, 10, "label", "0.00", "units" );
        final PSwing swing = new PSwing( control );

//        final PSwing swing = new PSwing(new JLabel("label"));
//        final JPanel mypanel = new JPanel();
//        mypanel.add(new JLabel("hello"));
//        mypanel.add(new JTextField("secondtime"));
//        mypanel.add(new JLabel("secondtime"));
//        final PSwing swing = new PSwing(mypanel);

        swing.setOffset( 400, 300 );
        contentPane.addWorldChild( swing );
        frame.setContentPane( contentPane );
        frame.setSize( 800, 600 );
    }

    public static void main( String[] args ) {
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                new TestLinearValueControlPSwing().start();
            }
        } );
    }

    private void start() {
        frame.setVisible( true );
    }
}
