

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractValueControl;

/**
 * ILayoutStrategy is the interface implemented by all classes that
 * handle layout of an AbstractValueControl.  See DefaultLayoutStrategy
 * for an example of how to write a layout strategy.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public interface ILayoutStrategy {

    public void doLayout( AbstractValueControl valueControl );
}
