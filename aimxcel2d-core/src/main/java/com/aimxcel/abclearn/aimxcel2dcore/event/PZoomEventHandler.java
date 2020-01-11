package com.aimxcel.abclearn.aimxcel2dcore.event;

import java.awt.event.InputEvent;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.aimxcel2dcore.PCamera;

public class PZoomEventHandler extends PDragSequenceEventHandler {

    /**
     * A constant used to adjust how sensitive the zooming will be to mouse
     * movement. The larger the number, the more each delta pixel will affect zooming.
     */
    private static final double ZOOM_SENSITIVITY = 0.001;
    private double minScale = 0;
    private double maxScale = Double.MAX_VALUE;
    private Point2D viewZoomPoint;

    /**
     * Creates a new zoom handler.
     */
    public PZoomEventHandler() {
        super();
        setEventFilter(new PInputEventFilter(InputEvent.BUTTON3_MASK));
    }

    // ****************************************************************
    // Zooming
    // ****************************************************************

    /**
     * Returns the minimum view magnification factor that this event handler is
     * bound by. The default is 0.
     * 
     * @return the minimum camera view scale
     */
    public double getMinScale() {
        return minScale;
    }

    /**
     * Sets the minimum view magnification factor that this event handler is
     * bound by. The camera is left at its current scale even if
     * <code>minScale</code> is larger than the current scale.
     * 
     * @param minScale the minimum scale, must not be negative.
     */
    public void setMinScale(final double minScale) {
        this.minScale = minScale;
    }

    /**
     * Returns the maximum view magnification factor that this event handler is
     * bound by. The default is Double.MAX_VALUE.
     * 
     * @return the maximum camera view scale
     */
    public double getMaxScale() {
        return maxScale;
    }

    /**
     * Sets the maximum view magnification factor that this event handler is
     * bound by. The camera is left at its current scale even if
     * <code>maxScale</code> is smaller than the current scale. Use
     * Double.MAX_VALUE to specify the largest possible scale.
     * 
     * @param maxScale the maximum scale, must not be negative.
     */
    public void setMaxScale(final double maxScale) {
        this.maxScale = maxScale;
    }

    /**
     * Records the start point of the zoom. Used when calculating the delta for
     * zoom speed.
     * 
     * @param event event responsible for starting the zoom interaction
     */
    protected void dragActivityFirstStep(final PInputEvent event) {
        viewZoomPoint = event.getPosition();
        super.dragActivityFirstStep(event);
    }

    /**
     * Updates the current zoom periodically, regardless of whether the mouse
     * has moved recently.
     * 
     * @param event contains information about the current state of the mouse
     */
    protected void dragActivityStep(final PInputEvent event) {
        final PCamera camera = event.getCamera();
        final double dx = event.getCanvasPosition().getX() - getMousePressedCanvasPoint().getX();
        double scaleDelta = 1.0 + ZOOM_SENSITIVITY * dx;

        final double currentScale = camera.getViewScale();
        final double newScale = currentScale * scaleDelta;

        if (newScale < minScale) {
            scaleDelta = minScale / currentScale;
        }
        if (maxScale > 0 && newScale > maxScale) {
            scaleDelta = maxScale / currentScale;
        }

        camera.scaleViewAboutPoint(scaleDelta, viewZoomPoint.getX(), viewZoomPoint.getY());
    }
}
