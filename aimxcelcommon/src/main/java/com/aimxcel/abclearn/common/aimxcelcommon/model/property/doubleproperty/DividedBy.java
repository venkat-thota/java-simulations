
package com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

/**
 * Observable property that computes the quotient of its arguments, but redefines 0/0 to be 0 instead of infinity which is
 * usually the appropriate behavior in physical scenarios.
 *
 * @author Sam Reid
 */
public class DividedBy extends CompositeDoubleProperty {
    public DividedBy( final ObservableProperty<Double> numerator, final ObservableProperty<Double> denominator ) {
        super( new Function0<Double>() {
            public Double apply() {
                if ( numerator.get() == 0 ) {
                    return 0.0;
                }
                else {
                    return numerator.get() / denominator.get();
                }
            }
        }, numerator, denominator );
    }
}