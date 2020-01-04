
package com.aimxcel.abclearn.common.aimxcelcommon.view.clock;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.clock.SimSpeedControl;
//import com.aimxcel.abclearn.common.abclearncommon.view.clock.TimeSpeederLabel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.SliderOnlyLayoutStrategy;


public class SimSpeedControl extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final LinearValueControl linearSlider;

    public SimSpeedControl( double min, double max, final ConstantDtClock defaultClock ) {
        this( min, max, defaultClock, AimxcelCommonResources.getString( "Common.sim.speed" ) );
    }

    public SimSpeedControl( double min, double max, final ConstantDtClock defaultClock, String title ) {
        this( min, max, defaultClock, title, Color.BLACK );
    }

    public SimSpeedControl( double min, double max, final ConstantDtClock defaultClock, String title, final Color textColor ) {
        this( min, max, defaultClock, title, new Property<Color>( textColor ) );//just create a constant Property<Color>
    }

    public SimSpeedControl( double min, double max, final ConstantDtClock defaultClock, String title, final ObservableProperty<Color> textColor ) {
        // title
        final JLabel titleLabel = new TimeSpeederLabel( title, textColor );

        // slider
        linearSlider = new LinearValueControl( min, max, "", "", "", new SliderOnlyLayoutStrategy() );
        Hashtable<Double, JLabel> table = new Hashtable<Double, JLabel>();
        table.put( new Double( min ), new TimeSpeederLabel( AimxcelCommonResources.getString( "Common.time.slow" ), textColor ) );
        table.put( new Double( max ), new TimeSpeederLabel( AimxcelCommonResources.getString( "Common.time.fast" ), textColor ) );
        linearSlider.setTickLabels( table );
        defaultClock.addConstantDtClockListener( new ConstantDtClock.ConstantDtClockAdapter() {
            @Override
            public void dtChanged( ConstantDtClock.ConstantDtClockEvent event ) {
                update( defaultClock );
            }
        } );
        update( defaultClock );

        // layout, title centered above slider
        setLayout( new GridBagLayout() );
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;
        add( titleLabel, c );
        add( linearSlider, c );

        //As of 6-25-2011, automatically add a change listener to set the clock dt when the slider is dragged, see #2798
        linearSlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                defaultClock.setDt( getValue() );
            }
        } );
    }

    private void update( ConstantDtClock defaultClock ) {
        linearSlider.setValue( defaultClock.getTimingStrategy().getSimulationTimeChangeForPausedClock() );
    }

    public LinearValueControl getLinearSlider() {
        return linearSlider;
    }

    public double getValue() {
        return linearSlider.getValue();
    }

    public double getMin() {
        return linearSlider.getMinimum();
    }

    public double getMax() {
        return linearSlider.getMaximum();
    }

    public void setValue( double dt ) {
        linearSlider.setValue( dt );
    }

    private static class TimeSpeederLabel extends JLabel {
        public TimeSpeederLabel( String text, ObservableProperty<Color> textColor ) {
            super( text );
            textColor.addObserver( new VoidFunction1<Color>() {
                public void apply( Color color ) {
                    setForeground( color );
                }
            } );
        }
    }

    public static void main( String[] args ) {
        JFrame testFrame = new JFrame();
        testFrame.add( new SimSpeedControl( 0, 100, new ConstantDtClock( 10, 10 ) ) );
        testFrame.pack();
        testFrame.setVisible( true );
    }
}
