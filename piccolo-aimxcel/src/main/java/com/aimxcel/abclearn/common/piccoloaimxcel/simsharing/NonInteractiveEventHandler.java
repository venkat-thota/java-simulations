
package com.aimxcel.abclearn.common.piccoloaimxcel.simsharing;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponentType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.interactive;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet.parameterSet;

/**
 * Standard event handler to use when a piccolo component is non-interactive,
 * so we can see if/when they are clicked on.
 *
 * @author Sam Reid
 */
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