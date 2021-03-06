
package com.aimxcel.abclearn.common.aimxcelcommon.model.property.integerproperty;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;


public class DividedBy extends CompositeIntegerProperty {
    public DividedBy( final ObservableProperty<Integer> numerator, final ObservableProperty<Integer> denominator ) {
        super( new Function0<Integer>() {
            public Integer apply() {
                if ( numerator.get() == 0 ) {
                    return 0;
                }
                else {
                    return numerator.get() / denominator.get();
                }
            }
        }, numerator, denominator );
    }
}