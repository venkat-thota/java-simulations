
package com.aimxcel.abclearn.glaciers.control;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.glaciers.GlaciersConstants;
import com.aimxcel.abclearn.glaciers.GlaciersStrings;
import com.aimxcel.abclearn.glaciers.model.GlaciersClock;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock.ConstantDtClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock.ConstantDtClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock.ConstantDtClockListener;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.CoreClockControlPanel;

public class GlaciersClockControlPanel extends CoreClockControlPanel {
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final GlaciersClock _clock;
    private final ConstantDtClockListener _clockListener;
    private final LinearValueControl _frameRateControl;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    /**
     * Constructor.
     * 
     * @param clock
     */
    public GlaciersClockControlPanel( GlaciersClock clock ) {
        super( clock );
        
        // Clock
        _clock = clock;
        _clockListener = new ConstantDtClockAdapter() {
            public void delayChanged( ConstantDtClockEvent event ) {
                // clock frame rate changed, update the speed slider
                _frameRateControl.setValue( _clock.getFrameRate() );
            }
        };
        _clock.addConstantDtClockListener( _clockListener );
        
        // common clock controls
        setRewindButtonVisible( false );
        setTimeDisplayVisible( true );
        setUnits( GlaciersStrings.UNITS_YEARS );
        setTimeFormat( GlaciersConstants.CLOCK_DISPLAY_FORMAT );
        setTimeColumns( GlaciersConstants.CLOCK_DISPLAY_COLUMNS );

        
        // Frame Rate control
        {
            double min = GlaciersConstants.CLOCK_FRAME_RATE_RANGE.getMin();
            double max = GlaciersConstants.CLOCK_FRAME_RATE_RANGE.getMax();
            String label = "";
            String textFieldPattern = "";
            String units = "";
            _frameRateControl = new LinearValueControl( min, max, label, textFieldPattern, units, new SliderOnlyLayoutStrategy() );
            _frameRateControl.setValue( _clock.getFrameRate() );
            _frameRateControl.setMinorTicksVisible( false );
            
            // Tick labels
            Hashtable labelTable = new Hashtable();
            labelTable.put( new Double( min ), new JLabel( GlaciersStrings.SLIDER_CLOCK_SLOW ) );
            labelTable.put( new Double( max ), new JLabel( GlaciersStrings.SLIDER_CLOCK_FAST ) );
            _frameRateControl.setTickLabels( labelTable );
            
            // Change font on tick labels
            Dictionary d = _frameRateControl.getSlider().getLabelTable();
            Enumeration e = d.elements();
            while ( e.hasMoreElements() ) {
                Object o = e.nextElement();
                if ( o instanceof JComponent )
                    ( (JComponent) o ).setFont( new AimxcelFont( 10 ) );
            }
            
            // Slider width
            _frameRateControl.setSliderWidth( 125 );
        }
        addBetweenTimeDisplayAndButtons( _frameRateControl );
        
        // Interactivity
        _frameRateControl.addChangeListener( new ChangeListener() { 
            public void stateChanged( ChangeEvent event ) {
                _clock.setFrameRate( (int)_frameRateControl.getValue() );
            }
        } );
    }
    
    /**
     * Call this method before releasing all references to this object.
     */
    public void cleanup() {
        cleanup();
        _clock.removeConstantDtClockListener( _clockListener );
    }
    
    /**
     * For attaching help items.
     * @return
     */
    public JComponent getFrameRateControl() {
        return _frameRateControl;
    }
}
