package com.aimxcel.abclearn.batteryresistorcircuit.common.paint.gauges;

import com.aimxcel.abclearn.batteryresistorcircuit.common.paint.Painter;

public interface IGauge extends Painter {
    public void setMax( double max );

    public void setMin( double min );

    public void setValue( double value );

}
