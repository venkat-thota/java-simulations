
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.And;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.CompositeProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ObservableProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Or;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

/**
 * This class should be used when implementing a composite property with type boolean (such as GreaterThan).
 * It adds convenience methods like 'or' and 'and' for further composition.
 *
 * @author Sam Reid
 */
public class CompositeBooleanProperty extends CompositeProperty<Boolean> {
    public CompositeBooleanProperty( Function0<Boolean> function, ObservableProperty<?>... properties ) {
        super( function, properties );
    }

    //Returns a property that is an 'or' conjunction of this and the provided argument, makes it possible to chain property calls such as:
    //anySolutes = molesOfSalt.greaterThan( 0 ).or( molesOfSugar.greaterThan( 0 ) );//From IntroModel in "Sugar and Salt Solutions"
    public Or or( ObservableProperty<Boolean> p ) {
        return new Or( this, p );
    }

    public And and( ObservableProperty<Boolean> p ) {
        return new And( this, p );
    }
}