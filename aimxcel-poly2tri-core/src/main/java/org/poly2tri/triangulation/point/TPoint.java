package org.poly2tri.triangulation.point;

import org.poly2tri.triangulation.TriangulationPoint;

public class TPoint extends TriangulationPoint
{
    private double _x;
    private double _y;
    private double _z;
    
    public TPoint( double x, double y )
    {
        this( x, y, 0 );
    }

    public TPoint( double x, double y, double z )
    {
        _x = x;
        _y = y;
        _z = z;
    }

    public double getX() { return _x; }
    public double getY() { return _y; }
    public double getZ() { return _z;  }

    public float getXf() { return (float)_x; }
    public float getYf() { return (float)_y; }
    public float getZf() { return (float)_z;  }

    @Override
    public void set( double x, double y, double z )
    {
        _x = x;
        _y = y;
        _z = z;
    }

}
