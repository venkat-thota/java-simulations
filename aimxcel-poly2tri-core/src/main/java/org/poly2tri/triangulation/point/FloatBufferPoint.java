package org.poly2tri.triangulation.point;

import java.nio.FloatBuffer;

import org.poly2tri.triangulation.TriangulationPoint;


public class FloatBufferPoint extends TriangulationPoint
{
    private final FloatBuffer _fb;
    private final int _ix,_iy,_iz;
    
    public FloatBufferPoint( FloatBuffer fb, int index )
    {
        _fb = fb;
        _ix = index;
        _iy = index+1;
        _iz = index+2;
    }
    
    public final double getX()
    {
        return _fb.get( _ix );
    }
    public final double getY()
    {
        return _fb.get( _iy );
    }
    public final double getZ()
    {
        return _fb.get( _iz );
    }
    
    public final float getXf()
    {
        return _fb.get( _ix );
    }
    public final float getYf()
    {
        return _fb.get( _iy );
    }
    public final float getZf()
    {
        return _fb.get( _iz );
    }

    @Override
    public void set( double x, double y, double z )
    {
        _fb.put( _ix, (float)x );
        _fb.put( _iy, (float)y );
        _fb.put( _iz, (float)z );
    }
    
    public static TriangulationPoint[] toPoints( FloatBuffer fb )
    {
        FloatBufferPoint[] points = new FloatBufferPoint[fb.limit()/3];
        for( int i=0,j=0; i<points.length; i++, j+=3 )
        {
            points[i] = new FloatBufferPoint(fb, j);
        }        
        return points;
    }
}
