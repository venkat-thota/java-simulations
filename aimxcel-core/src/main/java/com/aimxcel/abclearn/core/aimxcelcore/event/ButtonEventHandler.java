

package com.aimxcel.abclearn.core.aimxcelcore.event;

import java.util.ArrayList;

import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;

public class ButtonEventHandler extends PBasicInputEventHandler {

    private boolean mousePressed; // true if the mouse is pressed
    private boolean mouseInside; // true if the mouse is inside the node
    private ArrayList listeners; // list of ButtonEventListener

    public ButtonEventHandler() {
        super();
        mousePressed = false;
        mouseInside = false;
        listeners = new ArrayList();
    }

    public void mouseEntered( PInputEvent event ) {
        mouseInside = true;
        notifyFocus( true );
        if ( mousePressed ) {
            notifyArmed( true );
        }
    }

    public void mouseExited( PInputEvent event ) {
        mouseInside = false;
        notifyFocus( false );
        if ( mousePressed ) {
            notifyArmed( false );
        }
    }

    public void mousePressed( PInputEvent event ) {
        mousePressed = true;
        notifyArmed( true );
    }

    public void mouseReleased( PInputEvent event ) {
        mousePressed = false;
        notifyArmed( false );
        if ( mouseInside ) {
            notifyFire();
        }
    }

    public interface ButtonEventListener {
        /**
         * Indicates whether the node has focus.
         * A node has focus whenever the cursor
         * is inside the node's bounds.
         *
         * @param focus
         */
        public void setFocus( boolean focus );

        /**
         * Indicates whether the node is armed.
         * The node is armed when the mouse is pressed
         * and the cursor is inside the node's bounds.
         *
         * @param armed
         */
        public void setArmed( boolean armed );

        /**
         * Indicates that the node has been fired.
         * The node is fired when the mouse is release
         * and the cursor is inside the node's bounds.
         */
        public void fire();
    }

    public static class ButtonEventAdapter implements ButtonEventListener {
        public void setFocus( boolean focus ) {
        }

        public void setArmed( boolean armed ) {
        }

        public void fire() {
        }
    }

    public void addButtonEventListener( ButtonEventListener listener ) {
        listeners.add( listener );
    }

    public void removeButtonEventListener( ButtonEventListener listener ) {
        listeners.remove( listener );
    }

    private void notifyFocus( boolean focus ) {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (ButtonEventListener) listeners.get( i ) ).setFocus( focus );
        }
    }

    private void notifyArmed( boolean armed ) {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (ButtonEventListener) listeners.get( i ) ).setArmed( armed );
        }
    }

    private void notifyFire() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (ButtonEventListener) listeners.get( i ) ).fire();
        }
    }
}
