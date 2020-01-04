
package com.aimxcel.abclearn.common.aimxcelcommon.model.property.integerproperty;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;


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