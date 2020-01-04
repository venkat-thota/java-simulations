
package com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

/**
 * The product of two properties.
 *
 * @author Sam Reid
 */
public class Times extends CompositeDoubleProperty {
    public Times( final ObservableProperty<Double> a, final ObservableProperty<Double> b ) {
        super( new Function0<Double>() {
            public Double apply() {
                return a.get() * b.get();
            }
        }, a, b );
    }
}