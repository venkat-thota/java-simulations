package org.poly2tri.triangulation.delaunay.sweep;

import org.poly2tri.triangulation.TriangulationPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;



public class AdvancingFrontNode
{
    protected AdvancingFrontNode next = null;
    protected AdvancingFrontNode prev = null;

    protected final Double key; // XXX: BST
    protected final double value;
    protected final TriangulationPoint point;
    protected DelaunayTriangle triangle;
    
    public AdvancingFrontNode( TriangulationPoint point )
    {
        this.point = point;
        value = point.getX();
        key = Double.valueOf( value ); // XXX: BST
    }    

    public AdvancingFrontNode getNext()
    {
        return next;
    }

    public AdvancingFrontNode getPrevious()
    {
        return prev;
    }

    public TriangulationPoint getPoint()
    {
        return point;
    }
    
    public DelaunayTriangle getTriangle()
    {
        return triangle;
    }

    public boolean hasNext()
    {
        return next != null;
    }

    public boolean hasPrevious()
    {
        return prev != null;
    }    
}
