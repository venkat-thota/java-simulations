
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponentType;

/**
 * User component types.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public enum UserComponentTypes implements IUserComponentType {
    unknown, // TODO look for these occurrences and replace with something sensible
    button, checkBox, menuItem, radioButton, spinner, checkBoxMenuItem, icon, menu, tab, sprite, popupMenuItem,

    //Looks like a button, but stays in or out and acts like a radio button
    toggleButton,
    slider, jmolViewer, comboBox, popup, dialog, popupCheckBoxMenuItem, textField
}
