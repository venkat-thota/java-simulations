


package com.aimxcel.abclearn.theramp.model;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ImageLoader;


public class RampObject {
    private String location;
    private String name;
    private double defaultScale;
    private double mass;
    private double staticFriction;
    private double kineticFriction;
    private double scale;
    private double yOffset;

    public RampObject( String location, String name, double defaultScale,
                       double mass, double staticFriction, double kineticFriction, double scale ) {
        this( location, name, defaultScale, mass, staticFriction, kineticFriction, scale, 0.0 );
    }

    public RampObject( String location, String name, double defaultScale,
                       double mass, double staticFriction, double kineticFriction, double scale, double yOffset ) {
        this.location = location;
        this.name = name;
        this.defaultScale = defaultScale;
        this.mass = mass;
        this.staticFriction = staticFriction;
        this.kineticFriction = kineticFriction;
        this.scale = scale;
        this.yOffset = yOffset;
    }


    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public double getDefaultScale() {
        return defaultScale;
    }

    public BufferedImage getImage() throws IOException {
        return ImageLoader.loadBufferedImage( location );
    }

    public String toString() {
        return getName();
    }

    public double getMass() {
        return mass;
    }

    public double getStaticFriction() {
        return staticFriction;
    }

    public double getKineticFriction() {
        return kineticFriction;
    }

    public double getScale() {
        return scale;
    }

    public double getYOffset() {
        return yOffset;
    }
}
