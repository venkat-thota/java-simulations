

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractSlider;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearSlider;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LogarithmicSlider;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.TestSliders;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;



public class TestSliders extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestSliders() {

        // Linear
        final String linearPrefix = "LinearSlider: ";
        final JLabel linearLabel = new JLabel();
        final DecimalFormat linearFormat = new DecimalFormat( "0.0" );
        final LinearSlider linearSlider = new LinearSlider( -1, 1 );
        linearSlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                updateLabel( linearLabel, linearPrefix, linearSlider, linearFormat );
            }
        } );
        updateLabel( linearLabel, linearPrefix, linearSlider, linearFormat );

        // Logarithmic
        final String logPrefix = "LogarithmicSlider: ";
        final JLabel logLabel = new JLabel();
        final DecimalFormat logFormat = new DecimalFormat( "0E0" );
        final LogarithmicSlider logSlider = new LogarithmicSlider( 1E2, 1E6 );
        logSlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                updateLabel( logLabel, logPrefix, logSlider, logFormat );
            }
        } );
        updateLabel( logLabel, logPrefix, logSlider, logFormat );

        // Layout
        JPanel panel = new JPanel();
        EasyGridBagLayout layout = new EasyGridBagLayout( panel );
        layout.setInsets( new Insets( 10, 10, 10, 10 ) );
        layout.setAnchor( GridBagConstraints.WEST );
        panel.setLayout( layout );
        int row = 0;
        int column = 0;
        layout.addComponent( linearLabel, row++, column );
        layout.addComponent( linearSlider, row++, column );
        layout.addFilledComponent( new JSeparator(), row++, column, GridBagConstraints.HORIZONTAL );
        layout.addComponent( logLabel, row++, column );
        layout.addComponent( logSlider, row++, column );

        // Frame setup
        setContentPane( panel );
        pack();
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    private static void updateLabel( JLabel label, String prefix, AbstractSlider slider, DecimalFormat format ) {
        label.setText( prefix + format.format( slider.getModelValue() ) );
    }

    public static void main( String[] args ) {
        TestSliders test = new TestSliders();
        test.show();
    }
}
