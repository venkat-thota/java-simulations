
package com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty;

import java.util.ArrayList;
import java.util.Arrays;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;


public class DoubleProperty extends Property<Double> {
    public DoubleProperty( Double value ) {
        super( value );
    }

    public Plus plus( ObservableProperty<Double>... b ) {
        ArrayList<ObservableProperty<Double>> all = new ArrayList<ObservableProperty<Double>>();
        all.add( this );
        all.addAll( Arrays.asList( b ) );
        return new Plus( all.toArray( new ObservableProperty[0] ) );
    }

    //Increments the DoubleProperty by the specified amount, notifying observers if the change was nonzero
    public void add( double v ) {
        set( get() + v );
    }

    //The following methods are used for composing ObservableProperty<Double> instances.
    //These methods are copied in CompositeDoubleProperty (not sure how to factor them out without using traits or implicits)
    public DividedBy dividedBy( ObservableProperty<Double> volume ) {
        return new DividedBy( this, volume );
    }

    public GreaterThan greaterThan( double value ) {
        return new GreaterThan( this, value );
    }

    public Times times( double b ) {
        return new Times( this, new Property<Double>( b ) );
    }

    public ObservableProperty<Double> minus( CompositeDoubleProperty b ) {
        return new Minus( this, b );
    }

    public ObservableProperty<Boolean> lessThan( double b ) {
        return new LessThan( this, b );
    }

    public ObservableProperty<Boolean> greaterThanOrEqualTo( double b ) {
        return new GreaterThanOrEqualTo( this, b );
    }
}
