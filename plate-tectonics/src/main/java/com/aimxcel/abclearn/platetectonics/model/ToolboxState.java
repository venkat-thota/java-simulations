// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.platetectonics.model;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;

/**
 * Represents a toolbox state (whether certain tools are inside or not).
 * <p/>
 * NOTE: all properties should be modified in the LWJGL thread!
 */
public class ToolboxState {
    public final Property<Boolean> rulerInToolbox = new Property<Boolean>( true );
    public final Property<Boolean> thermometerInToolbox = new Property<Boolean>( true );
    public final Property<Boolean> densitySensorInToolbox = new Property<Boolean>( true );
}