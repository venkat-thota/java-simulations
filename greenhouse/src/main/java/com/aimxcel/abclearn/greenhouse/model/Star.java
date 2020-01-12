package com.aimxcel.abclearn.greenhouse.model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.photonabsorption.model.WavelengthConstants;

public class Star extends HorizontalPhotonEmitter {
    Disk extent;
    private double radius;
    private Point2D.Double location;

    public Star( double radius, Point2D.Double location, Rectangle2D.Double bounds ) {
        super( bounds, WavelengthConstants.SUNLIGHT_WAVELENGTH );
        this.radius = radius;
        this.location = location;
    }

    public double getRadius() {
        return radius;
    }

    public Point2D.Double getLocation() {
        return location;
    }
}
