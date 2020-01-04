
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.And;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.CompositeBooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

public class And extends CompositeBooleanProperty {
    public And( final ObservableProperty<Boolean>... terms ) {
        super( new Function0<Boolean>() {
            public Boolean apply() {
                //Test to see whether all args are true
                for ( ObservableProperty<Boolean> term : terms ) {
                    //Short circuit if any argument is false and return early for performance reasons
                    if ( !term.get() ) {
                        return false;
                    }
                }
                return true;
            }
        }, terms );
    }

    public static And and( ObservableProperty<Boolean> a, final ObservableProperty<Boolean> b ) {
        return new And( a, b );
    }
}