
package com.aimxcel.abclearn.common.abclearncommon.simsharing.messages;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserAction;

/**
 * Enum for actions performed by the user
 *
 * @author Sam Reid
 */
public enum UserActions implements IUserAction {
    activated, changed, closed, deactivated, deiconified, drag, endDrag,
    iconified, moved, pressed, released, resized, startDrag,
    windowOpened, windowClosed, windowClosing, popupTriggered,
    focusLost, focusGained, selected, textFieldCommitted, textFieldCorrected,
    enterPressed, keyPressed, buttonPressed, mousePressed,
    trackClicked, upArrowPressed, downArrowPressed
}
