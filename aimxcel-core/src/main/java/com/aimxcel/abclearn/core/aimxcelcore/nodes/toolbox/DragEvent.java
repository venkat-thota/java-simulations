
package com.aimxcel.abclearn.core.aimxcelcore.nodes.toolbox;

import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;


public class DragEvent {
    public final PInputEvent event;
    public final PDimension delta;

    public DragEvent( PInputEvent event, PDimension delta ) {
        this.event = event;
        this.delta = delta;
    }
}
