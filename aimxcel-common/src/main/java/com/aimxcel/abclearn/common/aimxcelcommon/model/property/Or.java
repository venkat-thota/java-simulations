
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import java.util.List;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.CompositeBooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;


public class Or extends CompositeBooleanProperty {
    public Or( final ObservableProperty<Boolean>... terms ) {
        super( new Function0<Boolean>() {
            public Boolean apply() {
                for ( ObservableProperty<Boolean> term : terms ) {
                    //Short circuit for improved performance, returning true as soon as any term evaluates to true
                    if ( term.get() ) {
                        return true;
                    }
                }
                return false;
            }
        }, terms );
    }

    public static Boolean or( List<Property<Boolean>> p ) {
        for ( Property<Boolean> booleanProperty : p ) {
            if ( booleanProperty.get() ) { return true; }
        }
        return false;
    }
}
