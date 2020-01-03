
package com.aimxcel.abclearn.common.abclearncommon.model.property;

import com.aimxcel.abclearn.common.abclearncommon.util.function.Function0;

import com.aimxcel.abclearn.common.abclearncommon.model.property.CompositeBooleanProperty;
import com.aimxcel.abclearn.common.abclearncommon.model.property.Not;
import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;

/**
 * Provides the negation of an ObservableProperty<Boolean>, but unlike Not, its value cannot be set (only observed)
 *
 * @author Sam Reid
 */
public class Not extends CompositeBooleanProperty {

    public Not( final ObservableProperty<Boolean> parent ) {
        super( new Function0<Boolean>() {
            public Boolean apply() {
                return !parent.get();
            }
        }, parent );
    }

    public static Not not( ObservableProperty<Boolean> p ) {
        return new Not( p );
    }
}