
package com.aimxcel.abclearn.common.abclearncommon.model.property;

import com.aimxcel.abclearn.common.abclearncommon.util.function.Function0;

import com.aimxcel.abclearn.common.abclearncommon.model.property.CompositeBooleanProperty;
import com.aimxcel.abclearn.common.abclearncommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.abclearncommon.model.property.Or;

/**
 * This adapter class converts an enumeration property to a boolean property indicating
 * true if the specified property's value equals the specified value.
 * <p/>
 * Note that this class is not recommend for radio button handlers; use PropertyRadioButton.
 *
 * @param <T> the property value type
 * @author Sam Reid
 */
public class ValueEquals<T> extends CompositeBooleanProperty {
    public ValueEquals( final ObservableProperty<T> property, final T value ) {
        super( new Function0<Boolean>() {
            public Boolean apply() {
                return property.get().equals( value );
            }
        }, property );
    }

    //Return an observable boolean or conjunction between this and the specified argument
    public Or or( ObservableProperty<Boolean> b ) {
        return new Or( this, b );
    }
}