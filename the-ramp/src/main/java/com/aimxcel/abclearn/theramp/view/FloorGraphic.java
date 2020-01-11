package com.aimxcel.abclearn.theramp.view;

import com.aimxcel.abclearn.theramp.model.Surface;



public class FloorGraphic extends SurfaceGraphic {
    public FloorGraphic( RampPanel rampPanel, Surface ground ) {
        super( rampPanel, ground );
        getSurfaceGraphic().setVisible( false );
        getAngleGraphic().setVisible( false );
        getHeightReadoutGraphic().setVisible( false );
        setPickable( false );
        setChildrenPickable( false );
    }
}
