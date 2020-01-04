

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractSlider;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LogarithmicMappingStrategy;


public class LogarithmicSlider extends AbstractSlider {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_SLIDER_RESOLUTION = 1000;

    public LogarithmicSlider( double min, double max ) {
        this( min, max, DEFAULT_SLIDER_RESOLUTION );
    }

    public LogarithmicSlider( double min, double max, int resolution ) {
        super( new LogarithmicMappingStrategy( min, max, 0, resolution ) );
    }
}
