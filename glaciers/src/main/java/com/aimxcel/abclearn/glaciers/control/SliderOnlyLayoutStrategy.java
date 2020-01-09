// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.glaciers.control;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.ILayoutStrategy;

/**
 * SliderOnlyLayoutStrategy is a layout strategy for a value control that shows only the slider.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SliderOnlyLayoutStrategy implements ILayoutStrategy {

    public SliderOnlyLayoutStrategy() {}

    public void doLayout( AbstractValueControl valueControl ) {
        valueControl.add( valueControl.getSlider() );
    }
}
