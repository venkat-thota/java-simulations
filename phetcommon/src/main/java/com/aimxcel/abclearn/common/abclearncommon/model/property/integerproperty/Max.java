
package com.aimxcel.abclearn.common.abclearncommon.model.property.integerproperty;

import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.model.property.Property;
import com.aimxcel.abclearn.common.abclearncommon.util.function.Function0;

/**
 * The max of two properties.
 *
 * @author Sam Reid
 */
public class Max extends CompositeIntegerProperty {
    public Max( final ObservableProperty<Integer> a, int b ) {
        this( a, new Property<Integer>( b ) );
    }

    public Max( final ObservableProperty<Integer> a, final ObservableProperty<Integer> b ) {
        super( new Function0<Integer>() {
            public Integer apply() {
                return Math.max( a.get(), b.get() );
            }
        }, a, b );
    }
}