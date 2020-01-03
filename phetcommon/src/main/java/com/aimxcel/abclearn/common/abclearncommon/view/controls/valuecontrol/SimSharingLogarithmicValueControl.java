
package com.aimxcel.abclearn.common.abclearncommon.view.controls.valuecontrol;

import static com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingManager.sendUserMessage;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterKeys.value;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterSet.parameterSet;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserActions.*;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IParameterValue;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterKeys;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentTypes;

import com.aimxcel.abclearn.common.abclearncommon.view.controls.valuecontrol.ILayoutStrategy;
import com.aimxcel.abclearn.common.abclearncommon.view.controls.valuecontrol.LogarithmicValueControl;

/**
 * Logarithmic value control that sends sim-sharing messages.
 *
 * @author Sam Reid
 */
public class SimSharingLogarithmicValueControl extends LogarithmicValueControl {
    private final IUserComponent userComponent;

    public SimSharingLogarithmicValueControl( IUserComponent userComponent, double min, double max, String label, String textFieldPattern, String units ) {
        super( min, max, label, textFieldPattern, units );
        this.userComponent = userComponent;
    }

    public SimSharingLogarithmicValueControl( IUserComponent userComponent, double min, double max, String label, String textFieldPattern, String units, ILayoutStrategy layoutStrategy ) {
        super( min, max, label, textFieldPattern, units, layoutStrategy );
        this.userComponent = userComponent;
    }

    @Override protected void sliderStartDrag( double modelValue ) {
        sendUserMessage( userComponent, UserComponentTypes.slider, startDrag, parameterSet( value, modelValue ) );
        super.sliderStartDrag( modelValue );
    }

    @Override protected void sliderEndDrag( double modelValue ) {
        sendUserMessage( userComponent, UserComponentTypes.slider, endDrag, parameterSet( value, modelValue ) );
        super.sliderEndDrag( modelValue );
    }

    @Override protected void sliderDrag( double modelValue ) {
        sendUserMessage( userComponent, UserComponentTypes.slider, drag, parameterSet( value, modelValue ) );
        super.sliderDrag( modelValue );
    }

    @Override protected void textFieldCommitted( IParameterValue commitAction, double value ) {
        sendUserMessage( userComponent, UserComponentTypes.textField, textFieldCommitted, parameterSet( ParameterKeys.commitAction, commitAction ).with( ParameterKeys.value, value ) );
        super.textFieldCommitted( commitAction, value );
    }

    //TODO this should probably be a system or model message
    @Override protected void textFieldCorrected( IParameterValue errorType, String value, double correctedValue ) {
        sendUserMessage( userComponent, UserComponentTypes.textField, textFieldCorrected, parameterSet( ParameterKeys.errorType, errorType ).with( ParameterKeys.value, value ).with( ParameterKeys.correctedValue, correctedValue ) );
        super.textFieldCorrected( errorType, value, correctedValue );
    }
}
