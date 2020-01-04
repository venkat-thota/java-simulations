
package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.CompositeProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function0;

/**
 * IfElse<T> is an ObservableProperty that uses a Property<Boolean> condition to simulation an if/else block.
 *
 * @author Sam Reid
 */
public class IfElse<T> extends CompositeProperty<T> {
    public IfElse( final Property<Boolean> condition, final T yes, final T no ) {
        super( new Function0<T>() {
            public T apply() {
                return condition.get() ? yes : no;
            }
        }, condition );
    }
}