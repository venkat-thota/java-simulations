
package com.aimxcel.abclearn.aimxcel2dextra.util;

import java.awt.geom.Point2D;
import java.io.Serializable;


public abstract class PLocator implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor provided for subclasses. Does nothing by itself.
     */
    public PLocator() {
    }

    /**
     * Locates the point this locator is responsible for finding, and stores it
     * in dstPoints. Should dstPoints be null, it will create a new point and
     * return it.
     * 
     * @param dstPoint output parameter to store the located point
     * @return the located point
     */
    public Point2D locatePoint(final Point2D dstPoint) {
        Point2D result;
        if (dstPoint == null) {
            result = new Point2D.Double();
        }
        else {
            result = dstPoint;
        }
        result.setLocation(locateX(), locateY());
        return result;
    }

    /**
     * Locates the X component of the position this locator finds.
     * 
     * @return x component of located point
     */
    public abstract double locateX();

    /**
     * Locates the Y component of the position this locator finds.
     * 
     * @return y component of located point
     */
    public abstract double locateY();
}
