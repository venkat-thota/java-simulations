
package com.aimxcel.abclearn.core.aimxcelcore.nodes.slider;

import java.awt.Paint;
import java.util.ArrayList;
import java.util.Collections;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import edu.umd.cs.piccolo.PNode;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.*;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet.parameterSet;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.endDrag;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.startDrag;

public abstract class SliderNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IUserComponent userComponent;
    public final double min;
    public final double max;
    public final SettableProperty<Double> value;
    private final ArrayList<Double> dragValues = new ArrayList<Double>(); // accumulation of values during dragging

    public SliderNode( IUserComponent userComponent, double min, double max, SettableProperty<Double> value ) {
        this.userComponent = userComponent;
        this.min = min;
        this.max = max;
        this.value = value;
    }

    protected void dragStarted() {
        SimSharingManager.sendUserMessage( userComponent, UserComponentTypes.slider, startDrag, parameterSet( ParameterKeys.value, value.get() ) );
        dragValues.clear();
        dragValues.add( value.get() );
    }

    protected void dragged() {
        dragValues.add( value.get() );
    }

    protected void dragEnded() {
        dragValues.add( value.get() );
        SimSharingManager.sendUserMessage( userComponent, UserComponentTypes.slider, endDrag,
                                           parameterSet( ParameterKeys.value, value.get() ).
                                                   with( numberDragEvents, dragValues.size() ).
                                                   with( minValue, Collections.min( dragValues ) ).
                                                   with( maxValue, Collections.max( dragValues ) ).
                                                   with( averageValue, average( dragValues ) ) );
        dragValues.clear();
    }

    private static double average( ArrayList<Double> v ) {
        double sum = 0;
        for ( Double entry : v ) {
            sum += entry;
        }
        return sum / v.size();
    }

    public abstract void setTrackFillPaint( final Paint paint );
}