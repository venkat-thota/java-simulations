package com.aimxcel.abclearn.theramp.view;

import java.awt.*;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;



public class EarthGraphic extends PNode {
    private PPath phetShapeGraphic;
    private RampPanel rampPanel;
    private RampWorld rampWorld;
        public static final Color earthGreen = new Color( 150, 200, 140 );



    public EarthGraphic( RampPanel rampPanel, RampWorld rampWorld ) {
        this.rampPanel = rampPanel;
        this.rampWorld = rampWorld;
        phetShapeGraphic = new PPath( null );
        phetShapeGraphic.setPaint( earthGreen );
        addChild( phetShapeGraphic );
        update();
    }

    private void update() {
        phetShapeGraphic.setPathTo( createShape() );
    }

    private Shape createShape() {
        int y = rampWorld.getRampBaseY();
        int dw = 10000;
        return new Rectangle( -dw, y, 1000 + dw * 2, 100000 );
    }
}
