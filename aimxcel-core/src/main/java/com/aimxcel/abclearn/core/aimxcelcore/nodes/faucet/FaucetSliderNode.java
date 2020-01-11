
package com.aimxcel.abclearn.core.aimxcelcore.nodes.faucet;

import java.awt.Color;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.slider.HSliderNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.slider.VSliderNode;

import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;


public class FaucetSliderNode extends HSliderNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FaucetSliderNode( IUserComponent userComponent, final ObservableProperty<Boolean> enabled, double maxFlowRate, final Property<Double> flowRate, final boolean snapToZeroWhenReleased ) {
        this( userComponent, enabled, VSliderNode.DEFAULT_TRACK_LENGTH, VSliderNode.DEFAULT_TRACK_THICKNESS, VSliderNode.DEFAULT_KNOB_WIDTH, maxFlowRate, flowRate, snapToZeroWhenReleased );
    }

    /**
     * Creates a slider control to be shown on the faucet to control the flow.
     *
     * @param userComponent           sim-sharing user component
     * @param enabled                 property to indicate if the slider is enabled
     * @param sliderTrackLength       length, in pixels, of slider track
     * @param sliderTrackThickness    thickness, in pixels, of slider track
     * @param knobWidth               width of the slider knob
     * @param maxFlowRate             the maximum flow rate
     * @param flowRate                the flow rate property that is attached to the slider
     * @param snapToZeroWhenReleased  does the knob snap back to zero when the user releases it?
     */
    public FaucetSliderNode( IUserComponent userComponent, final ObservableProperty<Boolean> enabled, double sliderTrackLength, double sliderTrackThickness,
                             double knobWidth, double maxFlowRate, final Property<Double> flowRate, final boolean snapToZeroWhenReleased ) {
        super( userComponent, 0, maxFlowRate, sliderTrackThickness, sliderTrackLength, knobWidth, flowRate, enabled );

        // Sets the flow to zero.
        final VoidFunction0 snapToZero = new VoidFunction0() {
            public void apply() {
                flowRate.set( 0.0 );
            }
        };

        // Sets the flow to zero when the user releases the slider thumb.
        if ( snapToZeroWhenReleased ) {
            addInputEventListener( new PBasicInputEventHandler() {
                @Override public void mouseReleased( PInputEvent event ) {
                    snapToZero.apply();
                }
            } );
        }

        // When the faucet is disabled, snap the slider back to zero.
        enabled.addObserver( new VoidFunction1<Boolean>() {
            public void apply( Boolean enabled ) {
                if ( !enabled ) {
                    snapToZero.apply();
                }
            }
        } );
        setTrackFillPaint( Color.white );
    }
}