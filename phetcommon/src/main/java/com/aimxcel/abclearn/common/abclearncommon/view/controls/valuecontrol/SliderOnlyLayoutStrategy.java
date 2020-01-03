

package com.aimxcel.abclearn.common.abclearncommon.view.controls.valuecontrol;

import com.aimxcel.abclearn.common.abclearncommon.view.controls.valuecontrol.AbstractValueControl;
import com.aimxcel.abclearn.common.abclearncommon.view.controls.valuecontrol.ILayoutStrategy;

/**
 * Layout strategy for a value control that displays only the slider.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class SliderOnlyLayoutStrategy implements ILayoutStrategy {

    public SliderOnlyLayoutStrategy() {
    }

    public void doLayout( AbstractValueControl valueControl ) {
        valueControl.add( valueControl.getSlider() );
    }
}