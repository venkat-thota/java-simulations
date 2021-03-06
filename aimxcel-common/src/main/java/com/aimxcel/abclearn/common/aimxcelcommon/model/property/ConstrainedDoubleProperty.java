

package com.aimxcel.abclearn.common.aimxcelcommon.model.property;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ConstrainedProperty;


public class ConstrainedDoubleProperty extends ConstrainedProperty<Double> {

    private final double min, max;

    public ConstrainedDoubleProperty( double min, double max, double value ) {
        super( value );
        this.min = min;
        this.max = max;
        if ( !isValid( value ) ) {
            handleInvalidValue( value );
        }
    }

    @Override
    protected boolean isValid( Double value ) {
        return ( value >= min && value <= max );
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
