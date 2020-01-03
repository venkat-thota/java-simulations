

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package edu.colorado.phet.common.phetgraphics.view.phetgraphics;

import java.awt.Component;
import java.awt.Point;

/**
 * This is a type of GraphicLayer set that is different from GraphicLayerSet in
 * the way it handles interaction. Whereas a GraphicLayerSet responds to the
 * getHandler( Point p ) message by delegating it to the AbcLearnGraphics it contains,
 * CompositeAbcLearnGraphic responds to the message itself.
 *
 * @author ?
 * @version $Revision$
 */
public class CompositeAbcLearnGraphic extends GraphicLayerSet {

    public CompositeAbcLearnGraphic( Component component ) {
        super( component );
    }

    /**
     * Tells if this object contains a specified point, for purposes of
     * event handling.
     *
     * @param p The specified point.
     * @return The AbcLearnGraphic responsible for handling the event.
     */
    protected AbcLearnGraphic getHandler( Point p ) {
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

    public CompositeAbcLearnGraphic() {
    }
}