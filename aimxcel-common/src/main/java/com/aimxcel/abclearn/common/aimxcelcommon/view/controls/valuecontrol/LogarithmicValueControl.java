

package com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.AbstractValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.DefaultLayoutStrategy;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.ILayoutStrategy;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LogarithmicSlider;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LogarithmicValueControl;


public class LogarithmicValueControl extends AbstractValueControl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor that provides a default layout for the control.
     *
     * @param min
     * @param max
     * @param label
     * @param textFieldPattern
     * @param units
     */
    public LogarithmicValueControl( double min, double max, String label, String textFieldPattern, String units ) {
        this( min, max, label, textFieldPattern, units, new DefaultLayoutStrategy() );
    }

    /**
     * Constructor that allows you to specify a layout for the control.
     *
     * @param min
     * @param max
     * @param label
     * @param textFieldPattern
     * @param units
     * @param layoutStrategy
     */
    public LogarithmicValueControl( double min, double max, String label, String textFieldPattern, String units, ILayoutStrategy layoutStrategy ) {
        super( new LogarithmicSlider( min, max ), label, textFieldPattern, units, layoutStrategy );
    }

    // test
    public static void main( String[] args ) {
        double min = 1;
        double max = 1E20;
        String label = "IQ:";
        String textFieldPattern = "0E0";
        String units = "brain cells";
        LogarithmicValueControl control = new LogarithmicValueControl( min, max, label, textFieldPattern, units );
        control.setValue( min );
        JFrame frame = new JFrame();
        frame.setContentPane( control );
        frame.pack();
        frame.setVisible( true );
    }
}
