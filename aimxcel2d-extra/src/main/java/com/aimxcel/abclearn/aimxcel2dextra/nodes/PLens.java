package com.aimxcel.abclearn.aimxcel2dextra.nodes;

import java.awt.Color;
import java.awt.Paint;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.aimxcel.abclearn.aimxcel2dcore.PCamera;
import com.aimxcel.abclearn.aimxcel2dcore.PLayer;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PDragEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;


public class PLens extends PNode {

    private static final long serialVersionUID = 1L;
    private final PPath dragBar;
    private final PCamera camera;
    private final transient PDragEventHandler lensDragger;

    /** The height of the drag bar. */
    public static double LENS_DRAGBAR_HEIGHT = 20;

    /** Default paint to use for the drag bar. */
    public static Paint DEFAULT_DRAGBAR_PAINT = Color.DARK_GRAY;

    /** Default paint to use when drawing the background of the lens. */
    public static Paint DEFAULT_LENS_PAINT = Color.LIGHT_GRAY;

    /**
     * Constructs the default PLens.
     */
    public PLens() {
        // Drag bar gets resized to fit the available space, so any rectangle
        // will do here
        dragBar = PPath.createRectangle(0, 0, 1, 1);
        dragBar.setPaint(DEFAULT_DRAGBAR_PAINT);
        // This forces drag events to percolate up to PLens object
        dragBar.setPickable(false);
        addChild(dragBar);

        camera = new PCamera();
        camera.setPaint(DEFAULT_LENS_PAINT);
        addChild(camera);

        // create an event handler to drag the lens around. Note that this event
        // handler consumes events in case another conflicting event handler has
        // been installed higher up in the heirarchy.
        lensDragger = new PDragEventHandler();
        lensDragger.getEventFilter().setMarksAcceptedEventsAsHandled(true);
        addInputEventListener(lensDragger);

        // When this PLens is dragged around adjust the cameras view transform.
        addPropertyChangeListener(PNode.PROPERTY_TRANSFORM, new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent evt) {
                camera.setViewTransform(getInverseTransform());
            }
        });
    }

    /**
     * Creates the default PLens and attaches the given layer to it.
     * 
     * @param layer layer to attach to this PLens
     */
    public PLens(final PLayer layer) {
        this();
        addLayer(0, layer);
    }

    /**
     * Returns the camera on which this lens is appearing.
     * 
     * @return camera on which lens is appearing
     */
    public PCamera getCamera() {
        return camera;
    }

    /**
     * Returns the drag bar for this lens.
     * 
     * @return this lens' drag bar
     */
    public PPath getDragBar() {
        return dragBar;
    }

    /**
     * Returns the event handler that this lens uses for its drag bar.
     * 
     * @return drag bar's drag event handler
     */
    public PDragEventHandler getLensDraggerHandler() {
        return lensDragger;
    }

    /**
     * Adds the layer to the camera.
     * 
     * @param index index at which to add the layer to the camera
     * @param layer layer to add to the camera
     */
    public void addLayer(final int index, final PLayer layer) {
        camera.addLayer(index, layer);
    }

    /**
     * Removes the provided layer from the camera.
     * 
     * @param layer layer to be removed
     */
    public void removeLayer(final PLayer layer) {
        camera.removeLayer(layer);
    }

    /**
     * When the lens is resized this method gives us a chance to layout the
     * lenses camera child appropriately.
     */
    protected void layoutChildren() {
        dragBar.setPathToRectangle((float) getX(), (float) getY(), (float) getWidth(), (float) LENS_DRAGBAR_HEIGHT);
        camera.setBounds(getX(), getY() + LENS_DRAGBAR_HEIGHT, getWidth(), getHeight() - LENS_DRAGBAR_HEIGHT);
    }
}
