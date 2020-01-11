// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.lwjgl.materials;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.lwjgl.GLOptions;
import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;

/**
 * Simple material, useful for LWJGL-related issues
 * NOTE: remember to put "setQueueBucket( Bucket.Transparent );" on geometry that uses this material! Otherwise transparency won't work
 */
public class ColorMaterial extends GLMaterial {
    private final Property<Color> colorProperty;

    public ColorMaterial( float red, float green, float blue ) {
        this( new Color( red, green, blue, 1f ) );
    }

    public ColorMaterial( float red, float green, float blue, float alpha ) {
        this( new Color( red, green, blue, alpha ) );
    }

    public ColorMaterial( Color color ) {
        this( new Property<Color>( color ) );
    }

    public ColorMaterial( final Property<Color> colorProperty ) {

        this.colorProperty = colorProperty;
    }

    @Override public void before( GLOptions options ) {
        super.before( options );

        LWJGLUtils.color4f( colorProperty.get() );
    }
}
