package com.aimxcel.abclearn.theramp.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;


public class RampTickSetGraphic extends PNode {
    private SurfaceGraphic surfaceGraphic;
    private ArrayList tickGraphics = new ArrayList();

    public RampTickSetGraphic( SurfaceGraphic surfaceGraphic ) {
        super();
        this.surfaceGraphic = surfaceGraphic;
        for ( int i = 0; i <= surfaceGraphic.getSurface().getLength(); i++ ) {
            double x = i;
            addTickGraphic( x );
        }
            }

    private void addTickGraphic( double x ) {
        RampTickSetGraphic.TickGraphic tickGraphic = new TickGraphic( x );
        tickGraphics.add( tickGraphic );
        addChild( tickGraphic );
    }

    public void update() {
        for ( int i = 0; i < tickGraphics.size(); i++ ) {
            TickGraphic tickGraphic = (TickGraphic) tickGraphics.get( i );
            tickGraphic.update();
        }
    }

    public class TickGraphic extends PNode {
        private double x;
        private PPath aimxcelShapeGraphic;

        public TickGraphic( double x ) {
            super();
            this.x = x;
            aimxcelShapeGraphic = new PPath( new Line2D.Double( 0, 0, 0, 7 ) );
            aimxcelShapeGraphic.setStroke( new BasicStroke( 2 ) );
            aimxcelShapeGraphic.setPaint( Color.black );
            addChild( aimxcelShapeGraphic );
            update();
        }

        public void update() {
            Point2D loc = surfaceGraphic.getSurface().getLocation( x );

            setTransform( new AffineTransform() );
            setOffset( surfaceGraphic.getViewLocation( loc ) );
            rotate( surfaceGraphic.getViewAngle() );
        }
    }
}
