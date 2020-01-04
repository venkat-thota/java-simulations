
package com.aimxcel.abclearn.core.aimxcelcore.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import edu.umd.cs.piccolo.activities.PActivity;

public class PanZoomWorldKeyHandler implements KeyListener {
    private AimxcelPCanvas aimxcelPCanvas;
    private int translateDX = 7;
    private double zoomScale = 1.1;
    private PanEvent panRight;
    private PanEvent panLeft;
    private PanEvent panUp;
    private PanEvent panDown;
    private ZoomEvent zoomIn;
    private ZoomEvent zoomOut;
    private static final int right = KeyEvent.VK_NUMPAD6;
    private static final int left = KeyEvent.VK_NUMPAD4;
    private static final int up = KeyEvent.VK_NUMPAD8;
    private static final int down = KeyEvent.VK_NUMPAD5;

    public PanZoomWorldKeyHandler( AimxcelPCanvas aimxcelPCanvas ) {
        this.aimxcelPCanvas = aimxcelPCanvas;
        panRight = new PanEvent( -translateDX, 0 );
        panLeft = new PanEvent( translateDX, 0 );
        panUp = new PanEvent( 0, translateDX );
        panDown = new PanEvent( 0, -translateDX );
        zoomIn = new ZoomEvent( zoomScale );
        zoomOut = new ZoomEvent( 1.0 / zoomScale );
    }

    private class PanZoomActivity extends PActivity {
        public PanZoomActivity() {
            super( -1 );
        }

        public void start() {
            if ( !aimxcelPCanvas.getRoot().getActivityScheduler().getActivitiesReference().contains( this ) ) {
                aimxcelPCanvas.getRoot().addActivity( this );
            }
        }

        public void stop() {
            while ( aimxcelPCanvas.getRoot().getActivityScheduler().getActivitiesReference().contains( this ) ) {
                aimxcelPCanvas.removeActivity( this );
            }
        }
    }

    private class PanEvent extends PanZoomActivity {
        private int dx;
        private int dy;

        public PanEvent( int dx, int dy ) {
            this.dx = dx;
            this.dy = dy;
        }

        protected void activityStep( long elapsedTime ) {
            super.activityStep( elapsedTime );
            translateWorld( dx, dy );
        }

    }

    private class ZoomEvent extends PanZoomActivity {
        private double scale;

        public ZoomEvent( double scale ) {
            this.scale = scale;
        }

        protected void activityStep( long elapsedTime ) {
            super.activityStep( elapsedTime );
            zoomWorld( scale );
        }
    }

    public void keyPressed( KeyEvent e ) {
        if ( e.isShiftDown() ) {
            switch( e.getKeyCode() ) {
                case KeyEvent.VK_UP:
                    zoomIn.start();
                    break;
                case KeyEvent.VK_DOWN:
                    zoomOut.start();
                    break;
                default:
                    break;
            }
        }
//        else if( true) {
        switch( e.getKeyCode() ) {
            case right:
                panRight.start();
                break;
            case left:
                panLeft.start();
                break;
            case up:
                panUp.start();
                break;
            case down:
                panDown.start();
                break;
            case KeyEvent.VK_PAGE_UP:
                zoomIn.start();
                break;
            case KeyEvent.VK_PAGE_DOWN:
                zoomOut.start();
                break;
            default:
                break;
//            }
        }
    }

    private void zoomWorld( double scale ) {
        //todo zooming is disabled
        Point2D point = aimxcelPCanvas.getCamera().getBounds().getCenter2D();
        aimxcelPCanvas.getCamera().localToGlobal( point );
        aimxcelPCanvas.getAimxcelRootNode().globalToWorld( point );
        aimxcelPCanvas.getAimxcelRootNode().scaleWorldAboutPoint( scale, point );
    }

    private void translateWorld( int dx, int dy ) {
        aimxcelPCanvas.getAimxcelRootNode().translateWorld( dx, dy );
    }

    public void keyReleased( KeyEvent e ) {
        switch( e.getKeyCode() ) {
            case right:
                panRight.stop();
                break;
            case left:
                panLeft.stop();
                break;
            case up:
                panUp.stop();
                zoomIn.stop();
                break;
            case down:
                panDown.stop();
                zoomOut.stop();
                break;
            case KeyEvent.VK_PAGE_UP:
                zoomIn.stop();
                break;
            case KeyEvent.VK_PAGE_DOWN:
                zoomOut.stop();
                break;
            default:
                break;
        }
    }

    public void keyTyped( KeyEvent e ) {
    }
}