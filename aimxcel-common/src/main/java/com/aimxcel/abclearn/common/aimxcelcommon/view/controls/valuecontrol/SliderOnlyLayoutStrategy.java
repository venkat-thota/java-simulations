

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.ILayoutStrategy;


public class SliderOnlyLayoutStrategy implements ILayoutStrategy {

    public SliderOnlyLayoutStrategy() {
    }

    public void doLayout( AbstractValueControl valueControl ) {
        valueControl.add( valueControl.getSlider() );
    }
}