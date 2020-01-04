

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol;

import java.awt.GridBagConstraints;

import javax.swing.JComponent;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.ILayoutStrategy;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;

/**
 * HorizontalLayoutStrategy lays the components of a value control in a horizontal row.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class HorizontalLayoutStrategy implements ILayoutStrategy {

    public HorizontalLayoutStrategy() {
    }

    public void doLayout( AbstractValueControl valueControl ) {

        // Get the components that will be part of the layout
        JComponent slider = valueControl.getSlider();
        JComponent textField = valueControl.getTextField();
        JComponent valueLabel = valueControl.getValueLabel();
        JComponent unitsLabel = valueControl.getUnitsLabel();

        // Label - slider - textfield - units.
        EasyGridBagLayout layout = new EasyGridBagLayout( valueControl );
        valueControl.setLayout( layout );
        layout.addComponent( valueLabel, 0, 0 );
        layout.addFilledComponent( slider, 0, 1, GridBagConstraints.HORIZONTAL );
        layout.addFilledComponent( textField, 0, 2, GridBagConstraints.HORIZONTAL );
        layout.addComponent( unitsLabel, 0, 3 );
    }
}
