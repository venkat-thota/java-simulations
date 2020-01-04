
package com.aimxcel.abclearn.core.aimxcelcore.nodes.toolbox;

import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.util.PDimension;


public class DragEvent {
    public final PInputEvent event;
    public final PDimension delta;

    public DragEvent( PInputEvent event, PDimension delta ) {
        this.event = event;
        this.delta = delta;
    }
}
