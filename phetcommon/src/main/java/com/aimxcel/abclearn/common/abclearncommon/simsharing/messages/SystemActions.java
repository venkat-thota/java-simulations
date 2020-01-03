
package com.aimxcel.abclearn.common.abclearncommon.simsharing.messages;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ISystemAction;

/**
 * Enum for actions performed by the system.
 *
 * @author Sam Reid
 */
public enum SystemActions implements ISystemAction {
    started, stopped, sentEvent, exited, shown,
    ipAddressLookup,
    mountainTimeLookup,
    closed,
    windowClosing, windowClosed, windowOpened,
    resized,
    activated, deactivated,
    iconified, deiconified
}
