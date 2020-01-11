
package com.aimxcel.abclearn.core.aimxcelcore.simsharing;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponentType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.interactive;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet.parameterSet;


public class NonInteractiveEventHandler extends PBasicInputEventHandler {

    private final IUserComponent userComponent;
    private final IUserComponentType componentType;

    // Convenience method, since most such components are sprites.
    public NonInteractiveEventHandler( IUserComponent userComponent ) {
        this( userComponent, UserComponentTypes.sprite );
    }

    public NonInteractiveEventHandler( IUserComponent userComponent, IUserComponentType componentType ) {
        this.userComponent = userComponent;
        this.componentType = componentType;
    }

    @Override public void mousePressed( PInputEvent event ) {
        SimSharingManager.sendUserMessage( userComponent, componentType, UserActions.pressed, parameterSet( interactive, false ) );
        super.mousePressed( event );
    }
}