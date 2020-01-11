// Copyright 2002-2011, University of Colorado

/**
 * Class: ScatterEvent
 * Package: edu.colorado.phet.greenhouse
 * Author: Another Guy
 * Date: Oct 17, 2003
 */
package com.aimxcel.abclearn.greenhouse.model;

import java.awt.geom.Point2D;
import java.util.Observable;

import com.aimxcel.abclearn.greenhouse.BaseGreenhouseModule;
import com.aimxcel.abclearn.greenhouse.GreenhouseConfig;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;

public class ScatterEvent extends Observable implements ModelElement {
    Point2D.Double location;
    private double radius;
    private BaseGreenhouseModule module;

    public ScatterEvent( Photon photon, BaseGreenhouseModule module ) {
        this.module = module;
        this.location = new Point2D.Double( photon.getLocation().x, photon.getLocation().y );
        this.radius = GreenhouseConfig.photonRadius * 1.5;
    }

    public Point2D.Double getLocation() {
        return location;
    }

    public double getRadius() {
        return radius;
    }

    public void stepInTime( double dt ) {
        this.radius -= .04;//t*radiusShrinkSpeed;
        if ( radius <= 0 ) {
            module.removeScatterEvent( this );
        }
        setChanged();
        notifyObservers();
    }
}
