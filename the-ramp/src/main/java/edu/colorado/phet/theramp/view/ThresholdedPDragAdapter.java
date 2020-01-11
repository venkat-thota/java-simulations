package edu.colorado.phet.theramp.view;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;


public class ThresholdedPDragAdapter extends PBasicInputEventHandler {
    private Point2D lastPressLocation;
    private long lastDragTime;
    private PBasicInputEventHandler target;
    private int thresholdX = 0;
    private int thresholdY = 0;
    private long thresholdMillis = 0;
    private boolean isDragging = false;

    public ThresholdedPDragAdapter( PBasicInputEventHandler target, int thresholdX, int thresholdY, long thresholdMillis ) {
        this.target = target;
        this.thresholdX = thresholdX;
        this.thresholdY = thresholdY;
        this.thresholdMillis = thresholdMillis;
    }

    public void mouseClicked( PInputEvent e ) {
        target.mouseClicked( e );
    }

    public void mouseEntered( PInputEvent e ) {
        target.mouseEntered( e );
    }

    public void mouseExited( PInputEvent e ) {
        target.mouseExited( e );
    }

    public void mousePressed( PInputEvent e ) {
        lastPressLocation = e.getCanvasPosition();
        target.mousePressed( e );
        lastDragTime = System.currentTimeMillis();
    }

    public void mouseReleased( PInputEvent e ) {
        lastPressLocation = null;
        target.mouseReleased( e );
    }

    public void mouseDragged( PInputEvent e ) {
        if ( System.currentTimeMillis() - lastDragTime >= thresholdMillis ) {//you waited too long, now have to start dragging again.
            resetDragging();
        }
        if ( lastPressLocation == null ) {
            lastPressLocation = e.getCanvasPosition();
        }
        double dx = Math.abs( lastPressLocation.getX() - e.getCanvasPosition().getX() );
        double dy = Math.abs( lastPressLocation.getY() - e.getCanvasPosition().getY() );
        if ( dx >= thresholdX && dy >= thresholdY ) {
            isDragging = true;
        }
        if ( isDragging ) {
            target.mouseDragged( e );
        }
        lastDragTime = System.currentTimeMillis();
    }

    public void mouseMoved( PInputEvent e ) {
        target.mouseMoved( e );
    }

    public void resetDragging() {
        isDragging = false;
        lastPressLocation = null;
    }
}
