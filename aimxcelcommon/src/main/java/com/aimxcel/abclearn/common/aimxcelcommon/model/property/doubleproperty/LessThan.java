
package com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.CompositeBooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

/**
 * ObservableProperty that is true if the specified ObservableProperty is greater than another specified value.
 *
 * @author Sam Reid
 */
public class LessThan extends CompositeBooleanProperty {
    public LessThan( final ObservableProperty<Double> a, final double b ) {
        super( new Function0<Boolean>() {
            public Boolean apply() {
                return a.get() < b;
            }
        }, a );
    }
}