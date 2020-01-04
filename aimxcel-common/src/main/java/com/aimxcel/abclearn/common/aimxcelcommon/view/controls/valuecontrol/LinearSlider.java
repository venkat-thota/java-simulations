

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractSlider;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearMappingStrategy;


public class LinearSlider extends AbstractSlider {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_SLIDER_RESOLUTION = 1000;

    public LinearSlider( double min, double max ) {
        this( min, max, DEFAULT_SLIDER_RESOLUTION );
    }

    public LinearSlider( double min, double max, int resolution ) {
        super( new LinearMappingStrategy( min, max, 0, resolution ) );
    }

    public LinearSlider( double min, double max, double value, int resolution ) {
        super( new LinearMappingStrategy( min, max, 0, resolution ) );
        setModelValue( value );
    }
}
