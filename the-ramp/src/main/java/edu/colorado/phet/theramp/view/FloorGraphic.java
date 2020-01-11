package edu.colorado.phet.theramp.view;

import edu.colorado.phet.theramp.model.Surface;



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
