package org.poly2tri.triangulation;

import java.util.ArrayList;

import org.poly2tri.geometry.primitives.Point;
import org.poly2tri.triangulation.delaunay.sweep.DTSweepConstraint;


public abstract class TriangulationPoint extends Point
{
    // List of edges this point constitutes an upper ending point (CDT)
    private ArrayList<DTSweepConstraint> edges; 
    
    @Override
    public String toString()
    {
        return "[" + getX() + "," + getY() + "]";
    }
    
    public abstract double getX();
    public abstract double getY();
    public abstract double getZ();

    public abstract float getXf();
    public abstract float getYf();
    public abstract float getZf();
    
    public abstract void set( double x, double y, double z );
    
    public ArrayList<DTSweepConstraint> getEdges()
    {
        return edges;
    }

    public void addEdge( DTSweepConstraint e )
    {
        if( edges == null )
        {
            edges = new ArrayList<DTSweepConstraint>();
        }
        edges.add( e );
    }

    public boolean hasEdges()
    {
        return edges != null;
    }

    /**
     * @param p - edge destination point
     * @return the edge from this point to given point
     */
    public DTSweepConstraint getEdge( TriangulationPoint p )
    {
        for( DTSweepConstraint c : edges )
        {
            if( c.p == p )
            {
                return c;
            }
        }
        return null;
    }
    
    public boolean equals(Object obj) 
    {
        if( obj instanceof TriangulationPoint ) 
        {
            TriangulationPoint p = (TriangulationPoint)obj;
            return getX() == p.getX() && getY() == p.getY();
        }
        return super.equals( obj );
    }

    public int hashCode()
    {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

}
