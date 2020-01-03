
package com.aimxcel.abclearn.common.abclearncommon.model.property;

import com.aimxcel.abclearn.common.abclearncommon.model.property.And;
import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.model.property.Or;
import com.aimxcel.abclearn.common.abclearncommon.model.property.Property;

/**
 * This can be used to represent a Boolean value in a MVC style pattern.  It remembers its default value and can be reset.
 *
 * @author Sam Reid
 */
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
