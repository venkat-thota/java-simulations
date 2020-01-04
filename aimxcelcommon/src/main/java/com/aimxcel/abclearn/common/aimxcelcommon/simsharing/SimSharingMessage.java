
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager.DELIMITER;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IMessageType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;

/**
 * Sim-sharing message.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 * @author Sam Reid
 */
public class SimSharingMessage<T, U, V> {

    enum MessageType implements IMessageType {user, system, model}

    public final IMessageType messageType;
    public final T component;
    public final U componentType;
    public final V action;
    public final ParameterSet parameters;
    public final long time = System.currentTimeMillis();

    public SimSharingMessage( IMessageType messageType, T component, U componentType, V action, final ParameterSet parameters ) {
        this.messageType = messageType;
        this.component = component;
        this.componentType = componentType;
        this.action = action;
        this.parameters = parameters;
    }

    public String toString() {
        return time + DELIMITER + messageType + DELIMITER + component + DELIMITER + componentType + DELIMITER + action + DELIMITER + parameters.toString( DELIMITER );
    }
}