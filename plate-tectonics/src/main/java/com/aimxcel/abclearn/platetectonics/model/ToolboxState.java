package com.aimxcel.abclearn.platetectonics.model;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;


public class ToolboxState {
    public final Property<Boolean> rulerInToolbox = new Property<Boolean>( true );
    public final Property<Boolean> thermometerInToolbox = new Property<Boolean>( true );
    public final Property<Boolean> densitySensorInToolbox = new Property<Boolean>( true );
}