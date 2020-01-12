package com.aimxcel.abclearn.theramp.view;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;



public class AngleGraphic extends PNode {
    private SurfaceGraphic surfaceGraphic;
    private PPath aimxcelShapeGraphic;
    private HTMLNode label;

    public AngleGraphic( SurfaceGraphic surfaceGraphic ) {
        super();
        this.surfaceGraphic = surfaceGraphic;
        aimxcelShapeGraphic = new PPath( null );
        aimxcelShapeGraphic.setStroke( new BasicStroke( 2 ) );
        aimxcelShapeGraphic.setStrokePaint( Color.black );
        label = new HTMLNode( "test" );
        label.setFont( new AimxcelFont( 14 ) );
        addChild( aimxcelShapeGraphic );
        addChild( label );
        update();
    }

    public void update() {
        Point origin = surfaceGraphic.getViewLocation( 0 );

        Point twoMetersOver = getGroundLocationView( 5 );

        int squareWidth = ( twoMetersOver.x - origin.x ) * 2;

        Rectangle2D ellipseBounds = new Rectangle2D.Double();
        ellipseBounds.setFrameFromCenter( origin, new Point2D.Double( origin.x + squareWidth / 2, origin.y + squareWidth / 2 ) );

        double extent = surfaceGraphic.getSurface().getAngle() * 180 / Math.PI;
        extent = Math.max( extent, 0.00001 );
        Arc2D.Double arc = new Arc2D.Double( ellipseBounds, 0, extent, Arc2D.OPEN );
        aimxcelShapeGraphic.setPathTo( arc );

        label.setOffset( arc.getBounds().getMaxX(), arc.getBounds().getY() + arc.getBounds().getHeight() / 2 + 20 );
        label.setHTML( "" + getAngleMessage() );
    }

    private String getAngleMessage() {
        double angle = surfaceGraphic.getSurface().getAngle() * 180 / Math.PI;
        String text = "<html>" + new DecimalFormat( "0.0" ).format( angle ) + "<sup>o</sup></html>";
        return text;
    }

    private Point getGroundLocationView( double dist ) {
        Point2D modelOrigion = surfaceGraphic.getSurface().getOrigin();
        Point2D.Double pt = new Point2D.Double( modelOrigion.getX() + dist, modelOrigion.getY() );
        return surfaceGraphic.getViewLocation( pt );
    }
}
