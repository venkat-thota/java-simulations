
package com.aimxcel.abclearn.photonabsorption.model.atoms;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObservable;
public abstract class Atom extends SimpleObservable {

    //----------------------------------------------------------------------------
    // Instance Data
    //----------------------------------------------------------------------------

    private final Point2D position;
    private final Color representationColor;
    private final double radius;
    private final double mass;

    //----------------------------------------------------------------------------
    // Constructor(s)
    //----------------------------------------------------------------------------

    public Atom( Color representationColor, double radius, double mass, Point2D position ) {
        super();
        this.representationColor = representationColor;
        this.radius = radius;
        this.mass = mass;
        this.position = position;
    }

    public Atom( Color representationColor, double radius, double mass ) {
        this( representationColor, radius, mass, new Point2D.Double( 0, 0 ) );
    }

    //------------------------------------------------------------------------
    // Methods
    //------------------------------------------------------------------------

    public Point2D getPositionRef() {
        return position;
    }

    public void setPosition( Point2D position ) {
        if ( this.position != position ) {
            this.position.setLocation( position );
            notifyObservers();
        }
    }

    public void setPosition( double x, double y ) {
        if ( this.position.getX() != x || this.position.getY() != y ) {
            this.position.setLocation( x, y );
            notifyObservers();
        }
    }

    public Color getRepresentationColor() {
        return representationColor;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public Rectangle2D getBoundingRect() {
        return new Rectangle2D.Double( position.getX() - radius, position.getY() - radius, radius * 2, radius * 2 );
    }
}
