package com.aimxcel.abclearn.aimxcel2dextra.pswing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;


public class PSwingMouseMotionEvent extends PSwingMouseEvent {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PMouse event from a Java MouseEvent.
     * 
     * @param id The event type (MOUSE_MOVED, MOUSE_DRAGGED)
     * @param swingEvent The original Java mouse event when in MOUSE_DRAGGED events
     * @param piccoloEvent Piccolo2d event to use when querying about the event's
     *            piccolo2d context
     */
    protected PSwingMouseMotionEvent(final int id, final MouseEvent swingEvent, final PInputEvent piccoloEvent) {
        super(id, swingEvent, piccoloEvent);
    }

    /**
     * Calls appropriate method on the listener based on this events ID.
     * 
     * @param listener the target for dispatch.
     */
    public void dispatchTo(final Object listener) {
        final MouseMotionListener mouseMotionListener = (MouseMotionListener) listener;
        switch (getID()) {
            case MouseEvent.MOUSE_DRAGGED:
                mouseMotionListener.mouseDragged(this);
                break;
            case MouseEvent.MOUSE_MOVED:
                mouseMotionListener.mouseMoved(this);
                break;
            default:
                throw new RuntimeException("PMouseMotionEvent with bad ID");
        }
    }

}