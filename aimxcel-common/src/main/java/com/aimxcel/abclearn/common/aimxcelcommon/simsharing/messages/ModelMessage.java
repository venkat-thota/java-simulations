
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingMessage;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IMessageType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IModelAction;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IModelComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IModelComponentType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;


public class ModelMessage extends SimSharingMessage<IModelComponent, IModelComponentType, IModelAction> {
    public ModelMessage( IMessageType messageType, IModelComponent component, IModelComponentType componentType, IModelAction action, ParameterSet parameters ) {
        super( messageType, component, componentType, action, parameters );
    }
}