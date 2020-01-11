package edu.colorado.phet.theramp.view;

import java.awt.*;


public class LeftBarrierGraphic extends BarrierGraphic {
    public LeftBarrierGraphic( Component component, RampPanel rampPanel, SurfaceGraphic surfaceGraphic ) {
        super( component, rampPanel, surfaceGraphic );
    }

    protected int getOffsetX() {
        return -imageGraphic.getImage().getWidth( null ) / 2;
    }

    protected double getBarrierPosition() {
        return 0;
    }
}
