
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.interactive;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet.parameterSet;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.pressed;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponentType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;

public class NonInteractiveEventListener extends MouseInputAdapter {

    private final IUserComponent userComponent;
    private final IUserComponentType userComponentType;

    // Convenience constructor, since many (most?) such components are sprites.
    public NonInteractiveEventListener( IUserComponent userComponent ) {
        this( userComponent, UserComponentTypes.sprite );
    }

    public NonInteractiveEventListener( IUserComponent userComponent, IUserComponentType userComponentType ) {
        this.userComponent = userComponent;
        this.userComponentType = userComponentType;
    }

    @Override public void mousePressed( MouseEvent event ) {
        SimSharingManager.sendUserMessage( userComponent, userComponentType, pressed, parameterSet( interactive, false ) );
        super.mousePressed( event );
    }
}
