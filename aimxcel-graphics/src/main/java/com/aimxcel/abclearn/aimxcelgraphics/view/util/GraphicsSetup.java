
 package com.aimxcel.abclearn.aimxcelgraphics.view.util;

import java.awt.Graphics2D;

/**
 * Operates on a Graphics2D object to prepare for rendering.
 *
 * @author ?
 * @version $Revision$
 */
public interface GraphicsSetup {
    /**
     * Applies this setup on the specified graphics object.
     *
     * @param graphics
     */
    void setup( Graphics2D graphics );
}
