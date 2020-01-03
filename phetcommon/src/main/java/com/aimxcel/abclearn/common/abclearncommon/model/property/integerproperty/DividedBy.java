
package com.aimxcel.abclearn.common.abclearncommon.model.property.integerproperty;

import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.util.function.Function0;

/**
 * Observable property that computes the quotient of its arguments, but redefines 0/0 to be 0 instead of infinity which is
 * usually the appropriate behavior in physical scenarios.
 *
 * @author Sam Reid
 */
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