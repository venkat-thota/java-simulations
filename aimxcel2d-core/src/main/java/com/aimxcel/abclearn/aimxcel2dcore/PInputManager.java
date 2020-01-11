package com.aimxcel.abclearn.aimxcel2dcore;

import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEventListener;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPickPath;
public class PInputManager extends PBasicInputEventHandler implements PRoot.InputSource {

    /** Records the last known mouse position on the canvas. */
    private final Point2D lastCanvasPosition;

    /** Records the current known mouse position on the canvas. */
    private final Point2D currentCanvasPosition;

    /** The next InputEvent that needs to be processed. */
    private InputEvent nextInput;

    /** The type of the next InputEvent that needs to be processed. */
    private int nextType;

    /** The Input Source the next event to process came from. */
    private PCamera nextInputSource;

    /** The current mouse focus. */
    private PPickPath mouseFocus;

    /** The previous mouse focus. */
    private PPickPath previousMouseFocus;

    /** Tracks where the mouse is right now on the canvas. */
    private PPickPath mouseOver;

    /** Tracks the previous location of the mouse on the canvas. */
    private PPickPath previousMouseOver;

    /** Tracks the input event listener that should receive keyboard events. */
    private PInputEventListener keyboardFocus;

    /** Tracks the number mouse buttons currently pressed. */
    private int buttonsPressed;

    /**
     * Creates a PInputManager and sets positions (last, current) to the origin
     * (0,0).
     */
    public PInputManager() {        
        lastCanvasPosition = new Point2D.Double();
        currentCanvasPosition = new Point2D.Double();
    }

    /**
     * Return the node that currently has the keyboard focus. This node receives
     * the key events.
     * 
     * @return the current keyboard focus
     */
    public PInputEventListener getKeyboardFocus() {
        return keyboardFocus;
    }

    /**
     * Set the node that should receive key events.
     * 
     * @param eventHandler sets the keyboard event focus, may be null
     */
    public void setKeyboardFocus(final PInputEventListener eventHandler) {
        final PInputEvent focusEvent = new PInputEvent(this, null);

        if (keyboardFocus != null) {
            dispatchEventToListener(focusEvent, FocusEvent.FOCUS_LOST, keyboardFocus);
        }

        keyboardFocus = eventHandler;

        if (keyboardFocus != null) {
            dispatchEventToListener(focusEvent, FocusEvent.FOCUS_GAINED, keyboardFocus);
        }
    }

    /**
     * Return the current Pick Path under the mouse focus. This will return the
     * path that received the current mouse pressed event, or null if the mouse
     * is not pressed. The mouse focus gets mouse dragged events even what the
     * mouse is not over the mouse focus.
     * 
     * @return the current Pick Path under the mouse focus
     */
    public PPickPath getMouseFocus() {
        return mouseFocus;
    }

    /**
     * Sets the current Pick Path under the mouse focus. The mouse focus gets
     * mouse dragged events even when the mouse is not over the mouse focus.
     * 
     * @param path the new mouse focus
     */
    public void setMouseFocus(final PPickPath path) {
        previousMouseFocus = mouseFocus;
        mouseFocus = path;
    }

    /**
     * Return the node the the mouse is currently over.
     * 
     * @return the path over which the mouse currently is
     */
    public PPickPath getMouseOver() {
        return mouseOver;
    }

    /**
     * Records the path which is directly below the mouse.
     * 
     * @param path path over which the mouse has been moved
     */
    public void setMouseOver(final PPickPath path) {
        mouseOver = path;
    }

    /**
     * Returns the position on the Canvas of the last event.
     * 
     * @return position of last canvas event
     */
    public Point2D getLastCanvasPosition() {
        return lastCanvasPosition;
    }

    /**
     * Returns the position of the current canvas event.
     * 
     * @return position of current canvas event
     */
    public Point2D getCurrentCanvasPosition() {
        return currentCanvasPosition;
    }

    // ****************************************************************
    // Event Handling - Methods for handling events
    // 
    // The dispatch manager updates the focus nodes based on the
    // incoming events, and dispatches those events to the appropriate
    // focus nodes.
    // ****************************************************************

    /** {@inheritDoc} */
    public void keyPressed(final PInputEvent event) {
        dispatchEventToListener(event, KeyEvent.KEY_PRESSED, keyboardFocus);
    }

    /** {@inheritDoc} */
    public void keyReleased(final PInputEvent event) {
        dispatchEventToListener(event, KeyEvent.KEY_RELEASED, keyboardFocus);
    }

    /** {@inheritDoc} */
    public void keyTyped(final PInputEvent event) {
        dispatchEventToListener(event, KeyEvent.KEY_TYPED, keyboardFocus);
    }

    /** {@inheritDoc} */
    public void mouseClicked(final PInputEvent event) {
        dispatchEventToListener(event, MouseEvent.MOUSE_CLICKED, previousMouseFocus);
    }

    /** {@inheritDoc} */
    public void mouseWheelRotated(final PInputEvent event) {
        setMouseFocus(getMouseOver());
        dispatchEventToListener(event, MouseWheelEvent.WHEEL_UNIT_SCROLL, mouseOver);
    }

    /** {@inheritDoc} */
    public void mouseWheelRotatedByBlock(final PInputEvent event) {
        setMouseFocus(getMouseOver());
        dispatchEventToListener(event, MouseWheelEvent.WHEEL_BLOCK_SCROLL, mouseOver);
    }

    /** {@inheritDoc} */
    public void mouseDragged(final PInputEvent event) {
        checkForMouseEnteredAndExited(event);
        dispatchEventToListener(event, MouseEvent.MOUSE_DRAGGED, mouseFocus);
    }

    /** {@inheritDoc} */
    public void mouseEntered(final PInputEvent event) {
        dispatchEventToListener(event, MouseEvent.MOUSE_ENTERED, mouseOver);
    }

    /** {@inheritDoc} */
    public void mouseExited(final PInputEvent event) {
        dispatchEventToListener(event, MouseEvent.MOUSE_EXITED, previousMouseOver);
    }

    /** {@inheritDoc} */
    public void mouseMoved(final PInputEvent event) {
        checkForMouseEnteredAndExited(event);
        dispatchEventToListener(event, MouseEvent.MOUSE_MOVED, mouseOver);
    }

    /** {@inheritDoc} */
    public void mousePressed(final PInputEvent event) {
        if (buttonsPressed == 0) {
            setMouseFocus(getMouseOver());
        }
        buttonsPressed++;
        dispatchEventToListener(event, MouseEvent.MOUSE_PRESSED, mouseFocus);
        if (buttonsPressed < 1 || buttonsPressed > 3) {
            System.err.println("invalid pressedCount on mouse pressed: " + buttonsPressed);
        }
    }

    /** {@inheritDoc} */
    public void mouseReleased(final PInputEvent event) {
        buttonsPressed--;
        checkForMouseEnteredAndExited(event);
        dispatchEventToListener(event, MouseEvent.MOUSE_RELEASED, mouseFocus);
        if (buttonsPressed == 0) {
            setMouseFocus(null);
        }
        if (buttonsPressed < 0 || buttonsPressed > 2) {
            System.err.println("invalid pressedCount on mouse released: " + buttonsPressed);
        }
    }

    /**
     * Fires events whenever the mouse moves from PNode to PNode.
     * 
     * @param event to check to see if the top node has changed.
     */
    protected void checkForMouseEnteredAndExited(final PInputEvent event) {
        final PNode currentNode = getPickedNode(mouseOver);
        final PNode previousNode = getPickedNode(previousMouseOver);

        if (currentNode != previousNode) {
            dispatchEventToListener(event, MouseEvent.MOUSE_EXITED, previousMouseOver);
            dispatchEventToListener(event, MouseEvent.MOUSE_ENTERED, mouseOver);
            previousMouseOver = mouseOver;
        }
    }

    /**
     * Returns picked node on pickPath if pickPath is not null, or null.
     * 
     * @param pickPath from which to extract picked node
     * 
     * @return the picked node or null if pickPath is null
     */
    private PNode getPickedNode(final PPickPath pickPath) {
        if (pickPath == null) {
            return null;
        }
        else {
            return pickPath.getPickedNode();
        }
    }

    // ****************************************************************
    // Event Dispatch.
    // ****************************************************************
    /** {@inheritDoc} */
    public void processInput() {
        if (nextInput == null) {
            return;
        }

        final PInputEvent e = new PInputEvent(this, nextInput);

        Point2D newCurrentCanvasPosition = null;
        Point2D newLastCanvasPosition = null;

        if (e.isMouseEvent()) {
            if (e.isMouseEnteredOrMouseExited()) {
                final PPickPath aPickPath = nextInputSource.pick(((MouseEvent) nextInput).getX(),
                        ((MouseEvent) nextInput).getY(), 1);
                setMouseOver(aPickPath);
                previousMouseOver = aPickPath;
                newCurrentCanvasPosition = (Point2D) currentCanvasPosition.clone();
                newLastCanvasPosition = (Point2D) lastCanvasPosition.clone();
            }
            else {
                lastCanvasPosition.setLocation(currentCanvasPosition);
                currentCanvasPosition.setLocation(((MouseEvent) nextInput).getX(), ((MouseEvent) nextInput).getY());
                final PPickPath aPickPath = nextInputSource.pick(currentCanvasPosition.getX(), currentCanvasPosition
                        .getY(), 1);
                setMouseOver(aPickPath);
            }
        }

        nextInput = null;
        nextInputSource = null;

        processEvent(e, nextType);

        if (newCurrentCanvasPosition != null && newLastCanvasPosition != null) {
            currentCanvasPosition.setLocation(newCurrentCanvasPosition);
            lastCanvasPosition.setLocation(newLastCanvasPosition);
        }
    }

    /**
     * Flags the given event as needing to be processed.
     * 
     * @param event the event to be processed
     * @param type type of event to be processed
     * @param camera camera from which the event was dispatched
     */
    public void processEventFromCamera(final InputEvent event, final int type, final PCamera camera) {
        // queue input
        nextInput = event;
        nextType = type;
        nextInputSource = camera;

        // tell root to process queued inputs
        camera.getRoot().processInputs();
    }

    /**
     * Dispatches the given event to the listener, or does nothing if listener
     * is null.
     * 
     * @param event event to be dispatched
     * @param type type of event to dispatch
     * @param listener target of dispatch
     */
    private void dispatchEventToListener(final PInputEvent event, final int type, final PInputEventListener listener) {
        if (listener != null) {
            // clear the handled bit since the same event object is used to send
            // multiple events such as mouseEntered/mouseExited and mouseMove.
            event.setHandled(false);
            listener.processEvent(event, type);
        }
    }
}
