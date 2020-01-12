package com.aimxcel.abclearn.platetectonics.model;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;


public class TerrainSample {
    private float elevation;
    private Vector2F textureCoordinates;
    private float randomElevationOffset = 0;

    public TerrainSample( float elevation, Vector2F textureCoordinates ) {
        this.elevation = elevation;
        this.textureCoordinates = textureCoordinates;
    }

    public void shiftWithTexture( Vector2F offset, TextureStrategy textureStrategy ) {
        setElevation( getElevation() + offset.y );
        setTextureCoordinates( getTextureCoordinates().plus( textureStrategy.mapTopDelta( offset ) ) );
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation( float elevation ) {
        this.elevation = elevation;
    }

    public Vector2F getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates( Vector2F textureCoordinates ) {
        this.textureCoordinates = textureCoordinates;
    }

    public float getRandomElevationOffset() {
        return randomElevationOffset;
    }

    public void setRandomElevationOffset( float randomElevationOffset ) {
        this.randomElevationOffset = randomElevationOffset;
    }
}
