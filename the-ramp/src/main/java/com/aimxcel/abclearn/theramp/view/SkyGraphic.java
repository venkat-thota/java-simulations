package com.aimxcel.abclearn.theramp.view;

import java.awt.*;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;


public class SkyGraphic extends PNode {
    private RampPanel rampPanel;
    private RampWorld rampWorld;
    private PPath aimxcelShapeGraphic;
    public static final Color lightBlue = new Color( 165, 220, 252 );

    public SkyGraphic( RampPanel rampPanel, RampWorld rampWorld ) {
        this.rampPanel = rampPanel;
        this.rampWorld = rampWorld;
        aimxcelShapeGraphic = new PPath();
        aimxcelShapeGraphic.setPaint( lightBlue );
        addChild( aimxcelShapeGraphic );
        update();
    }

    private void update() {
        aimxcelShapeGraphic.setPathTo( createShape() );
    }

    private Shape createShape() {
        int dw = 10000;
        int dh = 10000;
        Rectangle skyRect = new Rectangle( -dw, -dh, 1000 + dw * 2, rampWorld.getRampBaseY() + dh );
        return skyRect;
    }
}
