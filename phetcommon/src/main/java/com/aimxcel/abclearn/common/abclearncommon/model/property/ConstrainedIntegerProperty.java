

package com.aimxcel.abclearn.common.abclearncommon.model.property;

import com.aimxcel.abclearn.common.abclearncommon.util.IntegerRange;

import com.aimxcel.abclearn.common.abclearncommon.model.property.ConstrainedProperty;

/**
 * Integer property that is constrained to a range.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class ConstrainedIntegerProperty extends ConstrainedProperty<Integer> {

    private final int min, max;

    public ConstrainedIntegerProperty( IntegerRange range ) {
        this( range.getMin(), range.getMax(), range.getDefault() );
    }

    public ConstrainedIntegerProperty( int min, int max, int value ) {
        super( value );
        this.min = min;
        this.max = max;
        if ( !isValid( value ) ) {
            handleInvalidValue( value );
        }
    }

    @Override
    protected boolean isValid( Integer value ) {
        return ( value >= min && value <= max );
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
