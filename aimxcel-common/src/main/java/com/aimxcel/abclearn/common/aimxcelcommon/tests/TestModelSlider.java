

package com.aimxcel.abclearn.common.aimxcelcommon.tests;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.ModelSlider;

public class TestModelSlider {
    public static void main( String[] args ) {
        final ModelSlider ms = new ModelSlider( "Model Slider", "Newtons", 2, 4, 3 );
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( ms );
        frame.pack();
        frame.setVisible( true );
        ms.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                double value = ms.getValue();
                System.out.println( "value = " + value );
            }
        } );
    }
}