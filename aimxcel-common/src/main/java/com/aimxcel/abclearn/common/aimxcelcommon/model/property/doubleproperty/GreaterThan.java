
package com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.CompositeBooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;


public class GreaterThan extends CompositeBooleanProperty {
    public GreaterThan( final ObservableProperty<Double> a, final double b ) {
        super( new Function0<Boolean>() {
            public Boolean apply() {
                return a.get() > b;
            }
        }, a );
    }
}