
package com.aimxcel.abclearn.common.aimxcelcommon.model.property.integerproperty;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.CompositeBooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

public class LessThanOrEqualTo extends CompositeBooleanProperty {
    public LessThanOrEqualTo( final ObservableProperty<Integer> a, final ObservableProperty<Integer> b ) {
        super( new Function0<Boolean>() {
            public Boolean apply() {
                return a.get() <= b.get();
            }
        }, a, b );
    }
}