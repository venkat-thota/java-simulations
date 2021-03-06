
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ISystemAction;

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
