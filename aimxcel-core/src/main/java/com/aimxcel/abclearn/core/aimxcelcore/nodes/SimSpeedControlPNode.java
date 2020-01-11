
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.clock.SimSpeedControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class SimSpeedControlPNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 public SimSpeedControlPNode( double min, final Property<Double> dt, double max, final double maxPosX, final ObservableProperty<Color> labelColors ) {
        //SimSpeedControl requires a ConstantDtClock, so we create a dummy one that we can use to pass our Property<Double> dt through
        final ConstantDtClock clock = new ConstantDtClock( 30, dt.get() ) {{
            dt.addObserver( new VoidFunction1<Double>() {
                public void apply( Double aDouble ) {
                    setDt( dt.get() );
                }
            } );
            addConstantDtClockListener( new ConstantDtClockAdapter() {
                @Override public void dtChanged( ConstantDtClockEvent event ) {
                    dt.set( event.getClock().getDt() );
                }
            } );
        }};
        final SimSpeedControl simSpeedControl = new SimSpeedControl( min, max, clock, AimxcelCommonResources.getString( "Common.sim.speed" ), labelColors ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            SwingUtils.setBackgroundDeep( this, new Color( 0, 0, 0, 0 ) );
        }};
        addChild( new PSwing( simSpeedControl ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( maxPosX - getFullBoundsReference().width, 0 );
        }} );
    }
}