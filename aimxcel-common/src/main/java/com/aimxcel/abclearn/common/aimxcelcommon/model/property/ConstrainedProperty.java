

package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;


public abstract class ConstrainedProperty<T> extends Property<T> {

    public ConstrainedProperty( T value ) {
        super( value );
    }

    /**
     * Validates the value before setting it.
     *
     * @param value
     */
    @Override
    public void set( T value ) {
        if ( isValid( value ) ) {
            super.set( value );
        }
        else {
            handleInvalidValue( value );
        }
    }

    /**
     * Validates the value.
     *
     * @param value
     * @return true or false
     */
    protected abstract boolean isValid( T value );

    /**
     * Default behavior for invalid values is to throw IllegalArgumentException.
     *
     * @param value
     * @throws IllegalArgumentException
     */
    protected void handleInvalidValue( T value ) {
        throw new IllegalArgumentException( "illegal value: " + value.toString() );
    }
}
