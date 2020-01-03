
package com.aimxcel.abclearn.common.abclearncommon.model.property.integerproperty;

import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.util.function.Function0;

/**
 * This ObservableProperty computes the sum of its arguments
 *
 * @author Sam Reid
 */
public class Plus extends CompositeIntegerProperty {
    public Plus( final ObservableProperty<Integer>... terms ) {
        super( new Function0<Integer>() {
            public Integer apply() {
                int sum = 0;
                for ( ObservableProperty<Integer> term : terms ) {
                    sum = sum + term.get();
                }
                return sum;
            }
        }, terms );
    }
}