 
package com.aimxcel.abclearn.common.aimxcelcommon.math.vector;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.AbstractVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;


public @EqualsAndHashCode(callSuper = false) @ToString class MutableVector2D extends AbstractVector2D {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double x;
    private double y;

    public MutableVector2D() {}

    public MutableVector2D( AbstractVector2D v ) { this( v.getX(), v.getY() ); }

    public MutableVector2D( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    public MutableVector2D( Point2D p ) { this( p.getX(), p.getY() ); }

    public MutableVector2D( Point2D src, Point2D dst ) {
        this.x = dst.getX() - src.getX();
        this.y = dst.getY() - src.getY();
    }

    public MutableVector2D add( AbstractVector2D v ) {
        setX( getX() + v.getX() );
        setY( getY() + v.getY() );
        return this;
    }

    public MutableVector2D normalize() {
        double magnitude = magnitude();
        if ( magnitude == 0 ) {
            throw new UnsupportedOperationException( "Cannot normalize a zero-magnitude vector." );
        }
        return scale( 1.0 / magnitude );
    }

    public MutableVector2D scale( double scale ) {
        setX( getX() * scale );
        setY( getY() * scale );
        return this;
    }

    public void setX( double x ) { this.x = x; }

    public void setY( double y ) { this.y = y; }

    // The reason that this seemingly redundant override exists is to make
    // this method public.
    public void setComponents( double x, double y ) {
        setX( x );
        setY( y );
    }

    public void setValue( AbstractVector2D value ) { setComponents( value.getX(), value.getY() ); }

    public void setMagnitudeAndAngle( double magnitude, double angle ) {
        setComponents( Math.cos( angle ) * magnitude, Math.sin( angle ) * magnitude );
    }

    public void setMagnitude( double magnitude ) { setMagnitudeAndAngle( magnitude, getAngle() ); }

    public void setAngle( double angle ) { setMagnitudeAndAngle( magnitude(), angle ); }

    public MutableVector2D subtract( AbstractVector2D v ) {
        setX( getX() - v.getX() );
        setY( getY() - v.getY() );
        return this;
    }

    public MutableVector2D rotate( double theta ) {
        double r = magnitude();
        double alpha = getAngle();
        double gamma = alpha + theta;
        double xPrime = r * Math.cos( gamma );
        double yPrime = r * Math.sin( gamma );
        this.setComponents( xPrime, yPrime );
        return this;
    }

    public MutableVector2D negate() {
        setComponents( -getX(), -getY() );
        return this;
    }

    @Override public double getY() { return y; }

    @Override public double getX() { return x; }

    public static Vector2D createPolar( final double magnitude, final double angle ) { return Vector2D.createPolar( magnitude, angle ); }

    public static void main( String[] args ) {
        MutableVector2D v = new MutableVector2D( 0, 0 );
        System.out.println( "v = " + v );
        System.out.println( "v.hashCode() = " + v.hashCode() );
        MutableVector2D b = new MutableVector2D( 1, 2 );
        MutableVector2D c = new MutableVector2D( 0, 0 );
        System.out.println( "v.equals( b ) = " + v.equals( b ) + " (should be false)" );
        System.out.println( "v.equals( c ) = " + v.equals( c ) + " (should be true)" );
    }
}