
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.CompositeBooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Not;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

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