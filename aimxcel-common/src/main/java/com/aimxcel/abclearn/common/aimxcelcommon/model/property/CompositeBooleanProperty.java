
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.And;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.CompositeProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Or;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;


public class CompositeBooleanProperty extends CompositeProperty<Boolean> {
    public CompositeBooleanProperty( Function0<Boolean> function, ObservableProperty<?>... properties ) {
        super( function, properties );
    }

     public Or or( ObservableProperty<Boolean> p ) {
        return new Or( this, p );
    }

    public And and( ObservableProperty<Boolean> p ) {
        return new And( this, p );
    }
}