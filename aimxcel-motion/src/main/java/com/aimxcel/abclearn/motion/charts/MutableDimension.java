
package com.aimxcel.abclearn.motion.charts;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;


public class MutableDimension extends SimpleObservable {
    private double width;
    private double height;

    public MutableDimension( double width, double height ) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth( double width ) {
        this.width = width;
        notifyObservers();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight( double height ) {
        this.height = height;
        notifyObservers();
    }

    public void setDimension( double width, double height ) {
        this.width = width;
        this.height = height;
        notifyObservers();
    }
}
