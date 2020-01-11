
package com.aimxcel.abclearn.eatingandexercise.view;

import java.awt.*;


public class IndicatorHealthBar extends HealthBar {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HealthLevel healthLevel;

    public IndicatorHealthBar( String name, double min, double max, double minOptimal, double maxOptimal, double viewHeight, Color minColor, Color maxColor ) {
        super( name, min, max, minOptimal, maxOptimal, viewHeight, minColor, maxColor );
        healthLevel = new HealthLevel( this );
        addChild( healthLevel );
    }

    public void setValue( double value ) {
        healthLevel.setValue( value );
    }
}
