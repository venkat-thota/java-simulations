
package com.aimxcel.abclearn.common.abclearncommon.simsharing.components;

import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterKeys.enabled;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterKeys.interactive;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterSet.parameterSet;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserActions.drag;
import static com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentTypes.slider;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterKeys;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.ParameterSet;

/**
 * TODO: Not done yet, needs to be implemented.
 *
 * @author Sam Reid
 */
public class SimSharingJSlider extends JSlider {
    private final IUserComponent userComponent;

    public SimSharingJSlider( IUserComponent userComponent ) {
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJSlider( IUserComponent userComponent, int orientation ) {
        super( orientation );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJSlider( IUserComponent userComponent, int min, int max ) {
        super( min, max );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJSlider( IUserComponent userComponent, int min, int max, int value ) {
        super( min, max, value );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJSlider( IUserComponent userComponent, int orientation, int min, int max, int value ) {
        super( orientation, min, max, value );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    public SimSharingJSlider( IUserComponent userComponent, BoundedRangeModel brm ) {
        super( brm );
        this.userComponent = userComponent;
        enableMouseEvents();
    }

    //Make sure processMouseEvent gets called even if no listeners registered.  See http://www.dickbaldwin.com/java/Java102.htm#essential_ingredients_for_extending_exis
    private void enableMouseEvents() {
        enableEvents( AWTEvent.MOUSE_EVENT_MASK );
    }

    //When mouse is pressed, send a simsharing event if the component is disabled.  Safer to override than add listener, since the listener could be removed with removeAllListeners().
    //Only works if enableEvents has been called.  See #3218
    @Override protected void processMouseEvent( MouseEvent e ) {
        if ( e.getID() == MouseEvent.MOUSE_PRESSED && !isEnabled() ) {
            sendUserMessage( parameterSet( ParameterKeys.value, getValue() ).with( enabled, isEnabled() ).with( interactive, isEnabled() ) );
        }
        super.processMouseEvent( e );
    }

    //TODO: add messages for startDrag, endDrag actions (via a MouseListener?)

    @Override protected void fireStateChanged() {
        sendUserMessage( parameterSet( ParameterKeys.value, getValue() ) );
        super.fireStateChanged();
    }

    private void sendUserMessage( ParameterSet parameterSet ) {
        SimSharingManager.sendUserMessage( userComponent, slider, drag, parameterSet );
    }
}