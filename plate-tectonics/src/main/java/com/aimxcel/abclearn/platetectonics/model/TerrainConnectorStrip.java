package com.aimxcel.abclearn.platetectonics.model;

import static com.aimxcel.abclearn.platetectonics.util.Side.RIGHT;

import java.util.ArrayList;
import java.util.List;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;


public class TerrainConnectorStrip extends Terrain {
    private final Terrain left;
    private final Terrain right;
    private final int samples;
    private int leftXIndex;
    private final int rightXIndex = 0;

    public TerrainConnectorStrip( Terrain left, Terrain right, int samples, float minZ, float maxZ ) {
        super( left.getNumRows(), minZ, maxZ );
        this.left = left;
        this.right = right;
        this.samples = samples;

        // left and right need to have the same number of samples here
        assert left.getNumRows() == right.getNumRows();

        update();
    }

    public void update() {
        leftXIndex = left.getNumColumns() - 1;

        // get the X-data correct from each side
        final Float leftX = left.xPositions.get( leftXIndex );
        final Float rightX = right.xPositions.get( rightXIndex );

        // TODO: fix low performance
        while ( getNumColumns() > 0 ) {
            removeColumn( RIGHT );
        }
        for ( int xIndex = 0; xIndex < samples; xIndex++ ) {
            final float ratio = ( (float) xIndex ) / ( (float) ( samples - 1 ) );
            addColumn( RIGHT, leftX + ratio * ( rightX - leftX ), new ArrayList<TerrainSample>() {{
                for ( int zIndex = 0; zIndex < left.getNumRows(); zIndex++ ) {
                    float rowRatio = ( (float) zIndex ) / ( (float) ( left.getNumRows() - 1 ) );
                    final float leftElevation = left.getSample( leftXIndex, zIndex ).getElevation();
                    final float rightElevation = right.getSample( rightXIndex, zIndex ).getElevation();

                    // TODO: do we need to change the texture coordinates?
                    final float elevation = leftElevation + ratio * ( rightElevation - leftElevation );
                    add( new TerrainSample( elevation, new Vector2F( ( ratio ) / 2, rowRatio * 100 ) ) );
                }
            }} );
        }

        // position the connector to the Z positions of the TOP terrain, so that the lower part can be shown with a cross-section
        // "equals" since it's still boxed :P
        if ( !left.zPositions.get( 0 ).equals( right.zPositions.get( 0 ) ) ) {
            List<Float> topZPositions = left.getSample( leftXIndex, 0 ).getElevation() > right.getSample( rightXIndex, 0 ).getElevation()
                                        ? left.zPositions : right.zPositions;
            for ( int i = 0; i < left.getNumRows(); i++ ) {
                zPositions.set( i, topZPositions.get( i ) );
            }
        }
        columnsModified.updateListeners();
        elevationChanged.updateListeners();
    }

    // don't display water over the connectors
    @Override public boolean hasWater() {
        return false;
    }
}
