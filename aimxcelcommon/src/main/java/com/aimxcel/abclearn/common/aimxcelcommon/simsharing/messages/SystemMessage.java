
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingMessage;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IMessageType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ISystemAction;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ISystemComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ISystemComponentType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;

/**
 * Message for events performed automatically by the system, like startup.
 *
 * @author Sam Reid
 */
public class SystemMessage extends SimSharingMessage<ISystemComponent, ISystemComponentType, ISystemAction> {
    public SystemMessage( IMessageType messageType, ISystemComponent component, ISystemComponentType componentType, ISystemAction systemAction, ParameterSet parameters ) {
        super( messageType, component, componentType, systemAction, parameters );
    }
}
