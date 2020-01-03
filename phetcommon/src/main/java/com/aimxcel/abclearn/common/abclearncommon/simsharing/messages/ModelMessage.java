
package com.aimxcel.abclearn.common.abclearncommon.simsharing.messages;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingMessage;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IMessageType;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IModelAction;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IModelComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IModelComponentType;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterSet;

/**
 * Message sent about something the model did (without direct user interaction, though it may be a response to a user action.)
 *
 * @author Sam Reid
 */
public class ModelMessage extends SimSharingMessage<IModelComponent, IModelComponentType, IModelAction> {
    public ModelMessage( IMessageType messageType, IModelComponent component, IModelComponentType componentType, IModelAction action, ParameterSet parameters ) {
        super( messageType, component, componentType, action, parameters );
    }
}