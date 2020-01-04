
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingMessage;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IMessageType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserAction;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponentType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;

/**
 * Message class for events performed by the user.
 *
 * @author Sam Reid
 */
public class UserMessage extends SimSharingMessage<IUserComponent, IUserComponentType, IUserAction> {
    public UserMessage( IMessageType messageType, IUserComponent userComponent, IUserComponentType userComponentType, IUserAction action, ParameterSet parameters ) {
        super( messageType, userComponent, userComponentType, action, parameters );
    }
}
