

package com.aimxcel.abclearn.common.aimxcelcommon.math;

import java.awt.geom.Point2D;


public class PolygonUtils {

    /* not intended for instantiation */
    private PolygonUtils() {
    }

    
    public static double getArea( Point2D[] p ) {
        double a = 0;
        for ( int i = 0; i < p.length; i++ ) {
            int j = ( i + 1 ) % p.length;
            a += ( p[i].getX() * p[j].getY() );
            a -= ( p[j].getX() * p[i].getY() );
        }
        a *= 0.5;
        return a;
    }

   
    public static Point2D getCentroid( Point2D[] p ) {
        double cx = 0;
        double cy = 0;
        for ( int i = 0; i < p.length; i++ ) {
            int j = ( i + 1 ) % p.length;
            double n = ( ( p[i].getX() * p[j].getY() ) - ( p[j].getX() * p[i].getY() ) );
            cx += ( p[i].getX() + p[j].getX() ) * n;
            cy += ( p[i].getY() + p[j].getY() ) * n;
        }
        double a = getArea( p );
        double f = 1 / ( a * 6d );
        cx *= f;
        cy *= f;
        return new Point2D.Double( cx, cy );
    }
}
