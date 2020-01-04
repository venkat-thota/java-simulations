 
package com.aimxcel.abclearn.common.aimxcelcommon.math.vector;

import java.io.Serializable;

import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.AbstractVector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;

import static java.lang.Math.sqrt;


public abstract class AbstractVector3F implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract float getX();

    public abstract float getY();

    public abstract float getZ();

    public float magnitude() { return (float) sqrt( magnitudeSquared() ); }

    // the magnitude squared, which is equal to this.dot( this )
    public float magnitudeSquared() { return getX() * getX() + getY() * getY() + getZ() * getZ(); }

    public float dot( AbstractVector3F v ) {
        return getX() * v.getX() + getY() * v.getY() + getZ() * v.getZ();
    }

   
    public float distance( AbstractVector3F v ) {
        float dx = this.getX() - v.getX();
        float dy = this.getY() - v.getY();
        float dz = this.getZ() - v.getZ();
        return (float) sqrt( dx * dx + dy * dy + dz * dz );
    }

    // cross-product
    public Vector3F cross( AbstractVector3F v ) {
        return new Vector3F(
                getY() * v.getZ() - getZ() * v.getY(),
                getZ() * v.getX() - getX() * v.getZ(),
                getX() * v.getY() - getY() * v.getX()
        );
    }

    // The angle between this vector and "v", in radians
    public float angleBetween( AbstractVector3F v ) {
        return (float) Math.acos( MathUtil.clamp( -1, normalized().dot( v.normalized() ), 1 ) );
    }

    // The angle between this vector and "v", in degrees
    public float angleBetweenInDegrees( AbstractVector3F v ) { return (float) ( angleBetween( v ) * 180 / Math.PI ); }

    public Vector3F normalized() {
        float magnitude = magnitude();
        if ( magnitude == 0 ) {
            throw new UnsupportedOperationException( "Cannot normalize a zero-magnitude vector." );
        }
        return new Vector3F( getX() / magnitude, getY() / magnitude, getZ() / magnitude );
    }

    public Vector3F getInstanceOfMagnitude( float magnitude ) { return times( magnitude / magnitude() ); }

    public Vector3F times( float scalar ) { return new Vector3F( getX() * scalar, getY() * scalar, getZ() * scalar ); }

    // component-wise multiplication
    public Vector3F componentTimes( AbstractVector3F v ) { return new Vector3F( getX() * v.getX(), getY() * v.getY(), getZ() * v.getZ() ); }

    public Vector3F plus( AbstractVector3F v ) { return new Vector3F( getX() + v.getX(), getY() + v.getY(), getZ() + v.getZ() ); }

    public Vector3F plus( float x, float y, float z ) { return new Vector3F( getX() + x, getY() + y, getZ() + z ); }

    public Vector3F minus( AbstractVector3F v ) { return new Vector3F( getX() - v.getX(), getY() - v.getY(), getZ() - v.getZ() ); }

    public Vector3F minus( float x, float y, float z ) { return new Vector3F( getX() - x, getY() - y, getZ() - z ); }

    public Vector3F negated() { return new Vector3F( -getX(), -getY(), -getZ() ); }

    public Vector3D to3D() { return new Vector3D( getX(), getY(), getZ() );}
}