
 package com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics;

import java.awt.Component;
import java.awt.Point;


public class CompositeAimxcelGraphic extends GraphicLayerSet {

    public CompositeAimxcelGraphic( Component component ) {
        super( component );
    }

    /**
     * Tells if this object contains a specified point, for purposes of
     * event handling.
     *
     * @param p The specified point.
     * @return The AimxcelGraphic responsible for handling the event.
     */
    protected AimxcelGraphic getHandler( Point p ) {
        if ( getIgnoreMouse() == false && contains( p.x, p.y ) ) {
            return this;
        }
        else {
            return null;
        }
    }

    //---------------------------------------------------------
    // For Java Beans conformance
    //---------------------------------------------------------

    public CompositeAimxcelGraphic() {
    }
}