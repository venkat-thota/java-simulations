
package com.aimxcel.abclearn.common.abclearncommon.model.property.doubleproperty;

import com.aimxcel.abclearn.common.abclearncommon.model.property.CompositeBooleanProperty;
import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.util.function.Function0;

/**
 * ObservableProperty that is true if the specified ObservableProperty is greater or equal to another specified value.
 *
 * @author Sam Reid
 */
public class GreaterThanOrEqualTo extends CompositeBooleanProperty {
    public GreaterThanOrEqualTo( final ObservableProperty<Double> a, final double b ) {
        super( new Function0<Boolean>() {
            public Boolean apply() {
                return a.get() >= b;
            }
        }, a );
    }
}