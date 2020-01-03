
package com.aimxcel.abclearn.common.abclearncommon.model.property.doubleproperty;

import java.util.ArrayList;
import java.util.Arrays;

import com.aimxcel.abclearn.common.abclearncommon.model.property.CompositeProperty;
import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.model.property.Property;
import com.aimxcel.abclearn.common.abclearncommon.util.function.Function0;

/**
 * This class should be used as the parent class when implementing property composites that yield a Double value, such as Plus, Times, etc.
 * It also provides convenience method for composing further properties.
 *
 * @author Sam Reid
 */
public class CompositeDoubleProperty extends CompositeProperty<Double> {
    public CompositeDoubleProperty( Function0<Double> function, ObservableProperty<?>... properties ) {
        super( function, properties );
    }

    //The following methods are used for composing ObservableProperty<Double> instances.
    //These methods are copied in DoubleProperty (not sure how to factor them out without using traits or implicits)
    public Plus plus( ObservableProperty<Double>... b ) {
        ArrayList<ObservableProperty<Double>> all = new ArrayList<ObservableProperty<Double>>();
        all.add( this );
        all.addAll( Arrays.asList( b ) );
        return new Plus( all.toArray( new ObservableProperty[0] ) );
    }

    public DividedBy dividedBy( ObservableProperty<Double> volume ) {
        return new DividedBy( this, volume );
    }

    public GreaterThan greaterThan( double value ) {
        return new GreaterThan( this, value );
    }

    public Times times( double b ) {
        return new Times( this, new Property<Double>( b ) );
    }

    public GreaterThanOrEqualTo greaterThanOrEqualTo( double b ) {
        return new GreaterThanOrEqualTo( this, b );
    }

    public LessThan lessThan( double b ) {
        return new LessThan( this, b );
    }

    public Max max( CompositeDoubleProperty b ) {
        return new Max( this, b );
    }
}