

package com.aimxcel.abclearn.core.aimxcelcore.event;

import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;

import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;


public class DragNotificationHandler extends PBasicInputEventHandler {

    private Object eventSource;
    private EventListenerList listenerList;

    public DragNotificationHandler() {
        this( null );
    }

    public DragNotificationHandler( Object eventSource ) {
        super();
        listenerList = new EventListenerList();
        this.eventSource = eventSource;
        if ( eventSource == null ) {
            this.eventSource = this;
        }
    }

    public void mousePressed( PInputEvent event ) {
        fireDragBegin( new DragNotificationEvent( eventSource ) );
    }

    public void mouseDragged( PInputEvent event ) {
        fireDragged( new DragNotificationEvent( eventSource ) );
    }

    public void mouseReleased( PInputEvent event ) {
        fireDragEnd( new DragNotificationEvent( eventSource ) );
    }

    public void addDragNotificationListener( DragNotificationListener listener ) {
        listenerList.add( DragNotificationListener.class, listener );
    }

    public void removeDragNotificationListener( DragNotificationListener listener ) {
        listenerList.remove( DragNotificationListener.class, listener );
    }

    private void fireDragBegin( DragNotificationEvent event ) {
        Object[] listeners = listenerList.getListenerList();
        for ( int i = 0; i < listeners.length; i += 2 ) {
            if ( listeners[i] == DragNotificationListener.class ) {
                ( (DragNotificationListener) listeners[i + 1] ).dragBegin( event );
            }
        }
    }

    private void fireDragged( DragNotificationEvent event ) {
        Object[] listeners = listenerList.getListenerList();
        for ( int i = 0; i < listeners.length; i += 2 ) {
            if ( listeners[i] == DragNotificationListener.class ) {
                ( (DragNotificationListener) listeners[i + 1] ).dragged( event );
            }
        }
    }

    private void fireDragEnd( DragNotificationEvent event ) {
        Object[] listeners = listenerList.getListenerList();
        for ( int i = 0; i < listeners.length; i += 2 ) {
            if ( listeners[i] == DragNotificationListener.class ) {
                ( (DragNotificationListener) listeners[i + 1] ).dragEnd( event );
            }
        }
    }

    public static class DragNotificationEvent extends EventObject {
        public DragNotificationEvent( Object source ) {
            super( source );
        }
    }

    public interface DragNotificationListener extends EventListener {
        public void dragBegin( DragNotificationEvent event );

        public void dragged( DragNotificationEvent event );

        public void dragEnd( DragNotificationEvent event );
    }

    public static class DragNotificationAdapter implements DragNotificationListener {
        public void dragBegin( DragNotificationEvent event ) {
        }

        public void dragEnd( DragNotificationEvent event ) {
        }

        public void dragged( DragNotificationEvent event ) {
        }
    }
}
