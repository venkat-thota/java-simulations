 
package com.aimxcel.abclearn.common.aimxcelcommon.math.vector;

import java.io.Serializable;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.AbstractVector4F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector4D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector4F;

import static java.lang.Math.sqrt;

/**
 * Abstract base class for Vector4F and MutableVector4F
 */
public abstract class AbstractVector4F implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract float getX();

    public abstract float getY();

    public abstract float getZ();

    public abstract float getW();

    public float magnitude() { return (float) sqrt( magnitudeSquared() ); }

    // the magnitude squared, which is equal to this.dot( this )
    public float magnitudeSquared() { return getX() * getX() + getY() * getY() + getZ() * getZ() + getW() * getW(); }

    public float dot( AbstractVector4F v ) {
        return getX() * v.getX() + getY() * v.getY() + getZ() * v.getZ() + getW() * v.getW();
    }

   
    public float distance( AbstractVector4F v ) {
        float dx = this.getX() - v.getX();
        float dy = this.getY() - v.getY();
        float dz = this.getZ() - v.getZ();
        float dw = this.getW() - v.getW();
        return (float) sqrt( dx * dx + dy * dy + dz * dz + dw * dw );
    }

    public Vector4F normalized() {
        float magnitude = magnitude();
        if ( magnitude == 0 ) {
            throw new UnsupportedOperationException( "Cannot normalize a zero-magnitude vector." );
        }
        return new Vector4F( getX() / magnitude, getY() / magnitude, getZ() / magnitude, getW() / magnitude );
    }

    public Vector4F getInstanceOfMagnitude( float magnitude ) { return times( magnitude / magnitude() ); }

    public Vector4F times( float scalar ) { return new Vector4F( getX() * scalar, getY() * scalar, getZ() * scalar, getW() * scalar ); }

    // component-wise multiplication
    public Vector4F componentTimes( AbstractVector4F v ) {
        return new Vector4F( getX() * v.getX(), getY() * v.getY(), getZ() * v.getZ(), getW() * v.getW() );
    }

    public Vector4F plus( AbstractVector4F v ) { return new Vector4F( getX() + v.getX(), getY() + v.getY(), getZ() + v.getZ(), getW() + v.getW() ); }

    public Vector4F plus( float x, float y, float z, float w ) { return new Vector4F( getX() + x, getY() + y, getZ() + z, getW() + w ); }

    public Vector4F minus( AbstractVector4F v ) { return new Vector4F( getX() - v.getX(), getY() - v.getY(), getZ() - v.getZ(), getW() - v.getW() ); }

    public Vector4F minus( float x, float y, float z, float w ) { return new Vector4F( getX() - x, getY() - y, getZ() - z, getW() - w ); }

    public Vector4F negated() { return new Vector4F( -getX(), -getY(), -getZ(), -getW() ); }

    // ignore the w component
    public Vector3F to3F() { return new Vector3F( getX(), getY(), getZ() ); }

    public Vector4D to4D() { return new Vector4D( getX(), getY(), getZ(), getW() ); }
}