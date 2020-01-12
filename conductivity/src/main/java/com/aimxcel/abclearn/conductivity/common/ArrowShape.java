
package com.aimxcel.abclearn.conductivity.common;

import java.awt.geom.GeneralPath;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.AbstractVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;

public class ArrowShape {

    public ArrowShape( AbstractVector2D aimxcelvector, AbstractVector2D aimxcelvector1, double d, double d1, double d2 ) {
        direction = aimxcelvector1.minus( aimxcelvector ).normalized();
        double d3 = aimxcelvector1.minus( aimxcelvector ).magnitude();
        if ( d3 < d ) {
            throw new RuntimeException( "Head too big." );
        }
        else {
            norm = direction.getPerpendicularVector();
            tipLocation = aimxcelvector1;
            Vector2D aimxcelvector2 = getPoint( -1D * d, -d1 / 2D );
            Vector2D aimxcelvector3 = getPoint( -1D * d, d1 / 2D );
            Vector2D aimxcelvector4 = getPoint( -1D * d, -d2 / 2D );
            Vector2D aimxcelvector5 = getPoint( -1D * d, d2 / 2D );
            Vector2D aimxcelvector6 = getPoint( -1D * d3, -d2 / 2D );
            Vector2D aimxcelvector7 = getPoint( -1D * d3, d2 / 2D );
            DoubleGeneralPath doublegeneralpath = new DoubleGeneralPath( aimxcelvector1.getX(), aimxcelvector1.getY() );
            doublegeneralpath.lineTo( aimxcelvector2 );
            doublegeneralpath.lineTo( aimxcelvector4 );
            doublegeneralpath.lineTo( aimxcelvector6 );
            doublegeneralpath.lineTo( aimxcelvector7 );
            doublegeneralpath.lineTo( aimxcelvector5 );
            doublegeneralpath.lineTo( aimxcelvector3 );
            doublegeneralpath.lineTo( aimxcelvector1.getX(), aimxcelvector1.getY() );
            arrowPath = doublegeneralpath.getGeneralPath();
            return;
        }
    }

    private Vector2D getPoint( double d, double d1 ) {
        Vector2D aimxcelvector = direction.times( d ).plus( norm.times( d1 ) );
        return tipLocation.plus( aimxcelvector );
    }

    public GeneralPath getArrowPath() {
        return arrowPath;
    }

    GeneralPath arrowPath;
    AbstractVector2D tipLocation;
    private Vector2D direction;
    private Vector2D norm;
}
