package edu.colorado.phet.theramp.view;

import java.awt.*;



public class RightBarrierGraphic extends BarrierGraphic {
    public RightBarrierGraphic( Component component, RampPanel rampPanel, SurfaceGraphic surfaceGraphic ) {
        super( component, rampPanel, surfaceGraphic );
    }

    protected int getOffsetX() {
        return imageGraphic.getImage().getWidth( getRampPanel() ) / 2;
    }

    protected double getBarrierPosition() {
        return getRampGraphic().getSurface().getLength();
    }
}
