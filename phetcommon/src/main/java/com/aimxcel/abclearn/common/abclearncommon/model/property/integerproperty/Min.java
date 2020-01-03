
package com.aimxcel.abclearn.common.abclearncommon.model.property.integerproperty;

import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.util.function.Function0;

/**
 * The min of two properties.
 *
 * @author Sam Reid
 */
public class Min extends CompositeIntegerProperty {
    public Min( final ObservableProperty<Integer> a, final ObservableProperty<Integer> b ) {
        super( new Function0<Integer>() {
            public Integer apply() {
                return Math.min( a.get(), b.get() );
            }
        }, a, b );
    }
}