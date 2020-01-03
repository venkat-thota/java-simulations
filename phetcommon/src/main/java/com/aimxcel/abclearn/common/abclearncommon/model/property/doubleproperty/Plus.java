
package com.aimxcel.abclearn.common.abclearncommon.model.property.doubleproperty;

import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.util.function.Function0;

/**
 * This ObservableProperty computes the sum of its arguments
 *
 * @author Sam Reid
 */
public class Plus extends CompositeDoubleProperty {
    public Plus( final ObservableProperty<Double>... terms ) {
        super( new Function0<Double>() {
            public Double apply() {
                double sum = 0.0;
                for ( ObservableProperty<Double> term : terms ) {
                    sum = sum + term.get();
                }
                return sum;
            }
        }, terms );
    }
}