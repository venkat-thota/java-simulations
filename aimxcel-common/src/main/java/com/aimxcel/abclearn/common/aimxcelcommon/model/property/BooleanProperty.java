
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.And;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Or;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;


public class BooleanProperty extends Property<Boolean> {
    public BooleanProperty( Boolean value ) {
        super( value );
    }

    public And and( ObservableProperty<Boolean> p ) {
        return new And( this, p );
    }

    public Or or( ObservableProperty<Boolean> p ) {
        return new Or( this, p );
    }

    //Negates the value, thus toggling it
    public void toggle() {
        set( !get() );
    }
}
