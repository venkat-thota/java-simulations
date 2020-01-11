package com.aimxcel.abclearn.aimxcel2dcore.event;

import java.util.EventListener;

public interface PInputEventListener extends EventListener {
    /**
     * Called whenever an event is emitted. Used to notify listeners that an
     * event is available for proecessing.
     * 
     * @param event event that was emitted
     * @param type type of event
     */
    void processEvent(PInputEvent event, int type);
}
