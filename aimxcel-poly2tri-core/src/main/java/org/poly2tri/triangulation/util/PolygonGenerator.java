package org.poly2tri.triangulation.util;

import org.poly2tri.geometry.polygon.Polygon;
import org.poly2tri.geometry.polygon.PolygonPoint;

public class PolygonGenerator
{
    private static final double PI_2 = 2.0*Math.PI;
    
    public static Polygon RandomCircleSweep( double scale, int vertexCount )
    {
        PolygonPoint point;
        PolygonPoint[] points;
        double radius = scale/4;

        points = new PolygonPoint[vertexCount];
        for(int i=0; i<vertexCount; i++)
        {
            do
            {
                if( i%250 == 0 )
                {
                    radius += scale/2*(0.5 - Math.random());
                }
                else if( i%50 == 0 )
                {
                    radius += scale/5*(0.5 - Math.random());
                }
                else
                {
                    radius += 25*scale/vertexCount*(0.5 - Math.random());                                        
                }
                radius = radius > scale/2 ? scale/2 : radius;
                radius = radius < scale/10 ? scale/10 : radius;
            } while( radius < scale/10 || radius > scale/2 ); 
            point = new PolygonPoint( radius*Math.cos( (PI_2*i)/vertexCount ), 
                                      radius*Math.sin( (PI_2*i)/vertexCount ) );
            points[i] = point;
        }            
        return new Polygon( points );
    }

    public static Polygon RandomCircleSweep2( double scale, int vertexCount )
    {
        PolygonPoint point;
        PolygonPoint[] points;
        double radius = scale/4;

        points = new PolygonPoint[vertexCount];
        for(int i=0; i<vertexCount; i++)
        {
            do
            {
                radius += scale/5*(0.5 - Math.random());                                        
                radius = radius > scale/2 ? scale/2 : radius;
                radius = radius < scale/10 ? scale/10 : radius;
            } while( radius < scale/10 || radius > scale/2 ); 
            point = new PolygonPoint( radius*Math.cos( (PI_2*i)/vertexCount ), 
                                      radius*Math.sin( (PI_2*i)/vertexCount ) );
            points[i] = point;
        }            
        return new Polygon( points );
    }
}
