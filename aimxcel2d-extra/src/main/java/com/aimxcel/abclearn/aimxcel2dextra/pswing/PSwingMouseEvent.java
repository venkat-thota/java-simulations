package com.aimxcel.abclearn.aimxcel2dextra.pswing;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.io.Serializable;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPickPath;


public class PSwingMouseEvent extends MouseEvent implements Serializable, PSwingEvent {
    private static final long serialVersionUID = 1L;
    private final int id;
    private final transient PInputEvent event;

    /**
     * Constructs a new PMouse event from a Java MouseEvent.
     * 
     * @param id The event type (MOUSE_PRESSED, MOUSE_RELEASED, MOUSE_CLICKED,
     *            MOUSE_ENTERED, MOUSE_EXITED)
     * @param swingEvent The original swing mouse event when in MOUSE_RELEASED
     *            events.
     * @param piccoloEvent used to query about the event's Core context
     */
    protected PSwingMouseEvent(final int id, final MouseEvent swingEvent, final PInputEvent piccoloEvent) {
        super((Component) swingEvent.getSource(), swingEvent.getID(), swingEvent.getWhen(), swingEvent.getModifiers(),
                swingEvent.getX(), swingEvent.getY(), swingEvent.getClickCount(), swingEvent.isPopupTrigger());
        this.id = id;
        this.event = piccoloEvent;
    }

    /**
     * Creates and returns a new PMouse event from a Java MouseEvent.
     * 
     * @param id The event type (MOUSE_PRESSED, MOUSE_RELEASED, MOUSE_CLICKED,
     *            MOUSE_ENTERED, MOUSE_EXITED, MOUSE_MOVED, MOUSE_DRAGGED)
     * @param swingEvent The original swing mouse event when in
     *            MOUSE_DRAGGED and MOUSE_RELEASED events.
     * @param pEvent used to query about the event's Core2d context
     * 
     * @return the constructed PSwingEvent
     */
    public static PSwingEvent createMouseEvent(final int id, final MouseEvent swingEvent, final PInputEvent pEvent) {
        if (pEvent == null) {
            throw new IllegalArgumentException("PInputEvent associated with PSwingEvent may not be null");
        }
        
        if (id == MouseEvent.MOUSE_MOVED || id == MouseEvent.MOUSE_DRAGGED) {
            return new PSwingMouseMotionEvent(id, swingEvent, pEvent);
        }
        else if (id == MouseEvent.MOUSE_WHEEL) {
            return new PSwingMouseWheelEvent(id, (MouseWheelEvent) swingEvent, pEvent);
        }
        else {
            return new PSwingMouseEvent(id, swingEvent, pEvent);
        }
    }

    /**
     * Returns the x,y position of the event in the local coordinate system of
     * the node the event occurred on.
     * 
     * @return a Point2D object containing the x and y coordinates local to the
     *         node.
     */
    public Point2D getLocalPoint() {
        return new Point2D.Double(getX(), getY());
    }

    /**
     * Returns the horizontal x position of the event in the local coordinate
     * system of the node the event occurred on.
     * 
     * @return x a double indicating horizontal position local to the node.
     */
    public double getLocalX() {
        return getLocalPoint().getX();
    }

    /**
     * Returns the vertical y position of the event in the local coordinate
     * system of the node the event occurred on.
     * 
     * @return y a double indicating vertical position local to the node.
     */
    public double getLocalY() {
        return getLocalPoint().getY();
    }

    /**
     * Determine the event type.
     * 
     * @return the id
     */
    public int getID() {
        return id;
    }

    /**
     * Determine the node the event originated at. If an event percolates up the
     * tree and is handled by an event listener higher up in the tree than the
     * original node that generated the event, this returns the original node.
     * For mouse drag and release events, this is the node that the original
     * matching press event went to - in other words, the event is 'grabbed' by
     * the originating node.
     * 
     * @return the node
     */
    public PNode getNode() {
        return event.getPickedNode();
    }

    /**
     * Determine the path the event took from the PCanvas down to the visual
     * component.
     * 
     * @return the path
     */
    public PPickPath getPath() {
        return event.getPath();
    }

    /**
     * Determine the node the event originated at. If an event percolates up the
     * tree and is handled by an event listener higher up in the tree than the
     * original node that generated the event, this returns the original node.
     * For mouse drag and release events, this is the node that the original
     * matching press event went to - in other words, the event is 'grabbed' by
     * the originating node.
     * 
     * @return the node
     */
    public PNode getGrabNode() {
        return event.getPickedNode();
    }

    /**
     * Return the path from the PCanvas down to the currently grabbed object.
     * 
     * @return the path
     */
    public PPickPath getGrabPath() {
        return getPath();
    }

    /**
     * Get the current node that is under the cursor. This may return a
     * different result then getGrabNode() when in a MOUSE_RELEASED or
     * MOUSE_DRAGGED event.
     * 
     * @return the current node.
     */
    public PNode getCurrentNode() {
        return event.getPickedNode();
    }

    /**
     * Get the path from the PCanvas down to the visual component currently
     * under the mouse.This may give a different result then getGrabPath()
     * durring a MOUSE_DRAGGED or MOUSE_RELEASED operation.
     * 
     * @return the current path.
     */
    public PPickPath getCurrentPath() {
        return getPath();
    }

    /**
     * Calls appropriate method on the listener based on this events ID.
     * 
     * @param listener the MouseListener or MouseMotionListener to dispatch to.
     */
    public void dispatchTo(final Object listener) {
        final MouseListener mouseListener = (MouseListener) listener;
        switch (getID()) {
            case MouseEvent.MOUSE_CLICKED:
                mouseListener.mouseClicked(this);
                break;
            case MouseEvent.MOUSE_ENTERED:
                mouseListener.mouseEntered(this);
                break;
            case MouseEvent.MOUSE_EXITED:
                mouseListener.mouseExited(this);
                break;
            case MouseEvent.MOUSE_PRESSED:
                mouseListener.mousePressed(this);
                break;
            case MouseEvent.MOUSE_RELEASED:
                mouseListener.mouseReleased(this);
                break;
            default:
                throw new RuntimeException("PMouseEvent with bad ID");
        }
    }

    /**
     * Set the souce of this event. As the event is fired up the tree the source
     * of the event will keep changing to reflect the scenegraph object that is
     * firing the event.
     * 
     * @param newSource the currently reported source of the event (will change
     *            as event is bubbled up)
     */
    public void setSource(final Object newSource) {
        source = newSource;
    }

    /**
     * Returns this PSwingMouseEvent's MouseEvent.
     * 
     * @return underlying mouse event of this PSwingMouseEvent
     */
    public MouseEvent asMouseEvent() {
        return this;
    }
}