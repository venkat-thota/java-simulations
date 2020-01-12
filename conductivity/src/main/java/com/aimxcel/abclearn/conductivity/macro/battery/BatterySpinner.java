
package com.aimxcel.abclearn.conductivity.macro.battery;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.conductivity.ConductivityApplication;
import com.aimxcel.abclearn.conductivity.ConductivityResources;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;

public class BatterySpinner {
    private JSpinner spinner;
    private double min;
    private double max;
    private Battery battery;
    private ConductivityApplication conductivityApplication;

    public BatterySpinner( final Battery battery, ConductivityApplication conductivityApplication ) {
        this.battery = battery;
        this.conductivityApplication = conductivityApplication;
        min = 0.0D;
        max = 2D;
        spinner = new JSpinner( new SpinnerNumberModel( battery.getVoltage(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.10000000000000001D ) );

        if ( spinner.getEditor() instanceof JSpinner.DefaultEditor ) {
            final JSpinner.DefaultEditor ed = (JSpinner.DefaultEditor) spinner.getEditor();
            ed.getTextField().addKeyListener( new KeyAdapter() {
                public void keyReleased( KeyEvent e ) {
                    String text = ed.getTextField().getText();
                    try {
                        double volts = Double.parseDouble( text );
                        if ( volts >= min && volts <= max ) {
                            battery.setVoltage( volts );
                        }
                        else {
                            handleErrorInput();
                        }
                    }
                    catch( NumberFormatException n ) {
                    }
                }
            } );
        }

        TitledBorder titledborder = BorderFactory.createTitledBorder( ConductivityResources.getString( "BatterySpinner.BorderTitle" ) );
        titledborder.setTitleFont( new AimxcelFont( Font.BOLD, 12 ) );
        spinner.setBorder( titledborder );
        spinner.setPreferredSize( new Dimension( 150, spinner.getPreferredSize().height ) );
        spinner.addChangeListener( new ChangeListener() {

            public void stateChanged( ChangeEvent changeevent ) {
                double d = getSpinnerValue();
                if ( d >= min && d <= max ) {
                    battery.setVoltage( d );
                }
                else {
                    spinner.setValue( new Double( battery.getVoltage() ) );
                }
            }

        } );

    }

    private void handleErrorInput() {
        conductivityApplication.stopClock();
        AimxcelOptionPane.showErrorDialog( spinner, "Value out of range: Voltage should be betwen 0 and 2 Volts." );
        conductivityApplication.startClock();
        spinner.setValue( new Double( battery.getVoltage() ) );//doesn't fix the text field
        if ( spinner.getEditor() instanceof JSpinner.DefaultEditor ) {
            final JSpinner.DefaultEditor ed = (JSpinner.DefaultEditor) spinner.getEditor();
            ed.getTextField().setText( battery.getVoltage() + "" );
        }
    }

    private double getSpinnerValue() {
        Object obj = spinner.getValue();
        Double double1 = (Double) obj;
        return double1.doubleValue();
    }

    public JSpinner getSpinner() {
        return spinner;
    }

}
