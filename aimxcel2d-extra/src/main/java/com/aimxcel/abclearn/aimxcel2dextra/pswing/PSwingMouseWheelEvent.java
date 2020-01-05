package com.aimxcel.abclearn.aimxcel2dextra.pswing;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPickPath;


public class PSwingMouseWheelEvent extends MouseWheelEvent implements PSwingEvent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int id;
    private final PInputEvent event;

    /**
     * Constructs a new PMouseWheel event from a Java MouseWheelEvent.
     * 
     * @param id The event type (MOUSE_WHEEL)
     * @param swingEvent The original swing mouse wheel event.
     * @param piccoloEvent Piccolo2D event for use when querying about the
     *            event's piccolo2d context
     */
    protected PSwingMouseWheelEvent(final int id, final MouseWheelEvent swingEvent, final PInputEvent piccoloEvent) {
        super((Component) swingEvent.getSource(), swingEvent.getID(), swingEvent.getWhen(), swingEvent.getModifiers(),
                swingEvent.getX(), swingEvent.getY(), swingEvent.getClickCount(), swingEvent.isPopupTrigger(),
                swingEvent.getScrollType(), swingEvent.getScrollAmount(), swingEvent.getWheelRotation());
        this.id = id;
        this.event = piccoloEvent;
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
     * @param listener the target for dispatch.
     */
    public void dispatchTo(final Object listener) {
        final MouseWheelListener mouseWheelListener = (MouseWheelListener) listener;
        switch (getID()) {
            case MouseEvent.MOUSE_WHEEL:
                mouseWheelListener.mouseWheelMoved(this);
                break;
            default:
                throw new RuntimeException("PMouseWheelEvent with bad ID");
        }
    }

    /**
     * Set the souce of this event. As the event is fired up the tree the source
     * of the event will keep changing to reflect the scenegraph object that is
     * firing the event.
     * 
     * @param newSource the current source of the event to report
     */
    public void setSource(final Object newSource) {
        source = newSource;
    }

    /**
     * Returns this event as a mouse event. This reduces the need to cast
     * instances of this interface when they are known to all extend MouseEvent.
     * 
     * @return this object casted to a MouseEvent
     */
    public MouseEvent asMouseEvent() {
        return this;
    }
}