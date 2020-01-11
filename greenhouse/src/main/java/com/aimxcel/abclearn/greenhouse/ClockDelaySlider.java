// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.greenhouse;

import java.awt.Font;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

/**
 * Slider that controls the amount of delay between clock ticks for the given
 * clock.  This is intended for use in the clock control panel.
 * 
 * Author: Sam Reid, John Blanco
 */
public class ClockDelaySlider extends VerticalLayoutPanel {
	
    private LinearValueControl linearSlider;
    private int maxDelay;

    public ClockDelaySlider( int maxDelay, int minDelay, String textFieldPattern, final ConstantDtClock clock,
    		String title) {
    	this.maxDelay = maxDelay;
    	double minSliderValue = 1;
    	double maxSliderValue = (double)maxDelay / (double)minDelay;
        linearSlider = new LinearValueControl( minSliderValue, maxSliderValue, "", textFieldPattern, "" );
        linearSlider.setTextFieldVisible( false );
        Hashtable<Double, JLabel> table = new Hashtable<Double, JLabel>();
        table.put( new Double( minSliderValue ), new JLabel( AimxcelCommonResources.getString( "Common.time.slow" ) ) );
        table.put( new Double( maxSliderValue ), new JLabel( AimxcelCommonResources.getString( "Common.time.fast" ) ) );
        if (title != null){
        	final JLabel value = new JLabel(  title );
        	value.setFont( new AimxcelFont( Font.ITALIC, AimxcelFont.getDefaultFontSize()) );
        	table.put( new Double( ( minSliderValue + maxSliderValue ) / 2 ), value );
        }
        linearSlider.setTickLabels( table );
        clock.addConstantDtClockListener( new ConstantDtClock.ConstantDtClockAdapter() {
            public void delayChanged( ConstantDtClock.ConstantDtClockEvent event ) {
                update( clock );
            }
        } );
        update( clock );
        add( linearSlider );
    }

    public void addChangeListener( ChangeListener ch ) {
        linearSlider.addChangeListener( ch );
    }
    
    public void update(ConstantDtClock clock){
        linearSlider.setValue( mapDelayValueToSliderValue( clock.getDelay() ) );
    }
    
    private double mapDelayValueToSliderValue(int delay){
    	return (double)maxDelay / (double)delay;
    }
    
    public double getValue() {
        return linearSlider.getValue();
    }

    public void setValue( double dt ) {
        linearSlider.setValue( dt );
    }
    
    public static void main(String[] args) {
		JFrame testFrame = new JFrame();
		testFrame.add(new ClockDelaySlider(100, 30, "Test Slider", new ConstantDtClock(10, 10), "Test Clock"));
		testFrame.pack();
		testFrame.setVisible(true);
	}
}
