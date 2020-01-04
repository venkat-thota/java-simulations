
package com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

/**
 * The max of two properties.
 *
 * @author Sam Reid
 */
public class Max extends CompositeDoubleProperty {
    public Max( final ObservableProperty<Double> a, double b ) {
        this( a, new Property<Double>( b ) );
    }

    public Max( final ObservableProperty<Double> a, final ObservableProperty<Double> b ) {
        super( new Function0<Double>() {
            public Double apply() {
                return Math.max( a.get(), b.get() );
            }
        }, a, b );
    }
}