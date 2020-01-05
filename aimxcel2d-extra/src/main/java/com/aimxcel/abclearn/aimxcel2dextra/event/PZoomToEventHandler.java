package com.aimxcel.abclearn.aimxcel2dextra.event;

import java.awt.event.InputEvent;

import com.aimxcel.abclearn.aimxcel2dcore.PCamera;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEventFilter;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;


public class PZoomToEventHandler extends PBasicInputEventHandler {
    private static final int ZOOM_SPEED = 500;

    /**
     * Constructs a PZoomToEventHandler that only recognizes BUTTON1 events.
     */
    public PZoomToEventHandler() {
        setEventFilter(new PInputEventFilter(InputEvent.BUTTON1_MASK));
    }

    /**
     * Zooms the camera's view to the pressed node when button 1 is pressed.
     * 
     * @param event event representing the mouse press
     */
    public void mousePressed(final PInputEvent event) {
        zoomTo(event);
    }

    /**
     * Zooms the camera to the picked node of the event.
     * @param event Event from which to extract the zoom target
     */
    protected void zoomTo(final PInputEvent event) {
        PBounds zoomToBounds;
        final PNode picked = event.getPickedNode();

        if (picked instanceof PCamera) {
            final PCamera c = (PCamera) picked;
            zoomToBounds = c.getUnionOfLayerFullBounds();
        }
        else {
            zoomToBounds = picked.getGlobalFullBounds();
        }

        event.getCamera().animateViewToCenterBounds(zoomToBounds, true, ZOOM_SPEED);
    }
}
