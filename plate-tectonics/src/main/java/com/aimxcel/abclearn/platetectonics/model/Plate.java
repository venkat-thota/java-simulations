package com.aimxcel.abclearn.platetectonics.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aimxcel.abclearn.platetectonics.model.regions.Region;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.ValueNotifier;
import com.aimxcel.abclearn.common.aimxcelcommon.util.ObservableList;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;

import static com.aimxcel.abclearn.platetectonics.util.Side.RIGHT;
import static com.aimxcel.abclearn.common.aimxcelcommon.util.FunctionalUtils.flatten;
import static com.aimxcel.abclearn.common.aimxcelcommon.util.FunctionalUtils.map;
import static com.aimxcel.abclearn.common.aimxcelcommon.util.FunctionalUtils.unique;

public class Plate {

    // relevant regions for each plate
    private Region crust;
    private Region lithosphere;
    private Region mantle;

    // the surface terrain above
    private Terrain terrain;

    // fired when the plate is permanently removed from the model, so we can detach the necessary listeners
    public final ValueNotifier<Plate> disposed = new ValueNotifier<Plate>( this );

    public final ObservableList<Region> regions = new ObservableList<Region>();

    public void addCrust( Region crust ) {
        assert this.crust == null;

        this.crust = crust;
        regions.add( crust );
    }

    public void addLithosphere( Region lithosphere ) {
        assert this.lithosphere == null;

        this.lithosphere = lithosphere;
        regions.add( lithosphere );
    }

    public void addMantle( Region mantle ) {
        assert this.mantle == null;

        this.mantle = mantle;
        regions.add( mantle );
    }

    public void addTerrain( final TextureStrategy textureStrategy, final int depthSamples, float minZ, float maxZ ) {
        final Region currentTopRegion = crust == null ? mantle : crust;
        terrain = new Terrain( depthSamples, minZ, maxZ ) {{
            for ( int xIndex = 0; xIndex < currentTopRegion.getTopBoundary().samples.size(); xIndex++ ) {
                final float x = currentTopRegion.getTopBoundary().samples.get( xIndex ).getPosition().x;
                final int finalXIndex = xIndex;
                addColumn( RIGHT, x, new ArrayList<TerrainSample>() {{
                    for ( int zIndex = 0; zIndex < depthSamples; zIndex++ ) {
                        final float z = zPositions.get( zIndex );
                        // elevation to be fixed later
                        add( new TerrainSample( currentTopRegion.getTopBoundary().samples.get( finalXIndex ).getPosition().y, textureStrategy.mapTop( new Vector2F( x, z ) ) ) );
                    }
                }} );
            }
        }};
    }

    // shifts the entire plate by a Z offset
    public void shiftZ( float zOffset ) {
        for ( Sample sample : getSamples() ) {
            sample.setPosition( sample.getPosition().plus( new Vector3F( 0, 0, zOffset ) ) );
        }
        getTerrain().shiftZ( zOffset );
    }

    public List<Sample> getSamples() {
        return unique( flatten( map( regions, new Function1<Region, Collection<? extends Sample>>() {
            public Collection<? extends Sample> apply( Region region ) {
                return region.getSamples();
            }
        } ) ) );
    }

    public Region getCrust() {
        return crust;
    }

    public Region getMantle() {
        return mantle;
    }

    public Region getLithosphere() {
        return lithosphere;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
