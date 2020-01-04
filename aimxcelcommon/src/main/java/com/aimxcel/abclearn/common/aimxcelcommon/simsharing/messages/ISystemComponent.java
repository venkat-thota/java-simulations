
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;

/**
 * Marker interface for identifying objects that can be acted on by the system.
 * The marker interface makes it easy for us to make sure that we don't have typos in string copies, and use auto-complete for development.
 * <p/>
 * Extend IUserComponent here since the system sometimes acts on user items, like automatically closing a dialog
 *
 * @author Sam Reid
 */
public interface ISystemComponent extends IUserComponent {
}
