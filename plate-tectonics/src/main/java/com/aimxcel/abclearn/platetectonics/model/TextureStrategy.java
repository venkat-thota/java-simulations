package com.aimxcel.abclearn.platetectonics.model;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;


public class TextureStrategy {
    private final float frontScale;

    public TextureStrategy( float frontScale ) {
        this.frontScale = frontScale;
    }

    public float getFrontScale() {
        return frontScale;
    }

    public float getTopScale() {
        return frontScale * 0.25f;
    }

    public Vector2F mapTop( Vector2F position ) {
        return position.times( getTopScale() );
    }

    public Vector2F mapTopDelta( Vector2F vector ) {
        return vector.times( getTopScale() );
    }

    public Vector2F mapFront( Vector2F position ) {
        return position.times( getFrontScale() );
    }

    public Vector2F mapFrontDelta( Vector2F vector ) {
        return vector.times( getFrontScale() );
    }
}
