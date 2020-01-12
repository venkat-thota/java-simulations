package org.poly2tri.triangulation.sets;

import java.util.ArrayList;
import java.util.List;

import org.poly2tri.triangulation.Triangulatable;
import org.poly2tri.triangulation.TriangulationContext;
import org.poly2tri.triangulation.TriangulationMode;
import org.poly2tri.triangulation.TriangulationPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

public class PointSet implements Triangulatable
{
    List<TriangulationPoint> _points;
    List<DelaunayTriangle> _triangles;
    
    public PointSet( List<TriangulationPoint> points )
    {
        _points = new ArrayList<TriangulationPoint>();
        _points.addAll( points );
    }
    
    public TriangulationMode getTriangulationMode()
    {
        return TriangulationMode.UNCONSTRAINED;
    }

    public List<TriangulationPoint> getPoints()
    {
        return _points;
    }
    
    public List<DelaunayTriangle> getTriangles()
    {
        return _triangles;
    }
    
    public void addTriangle( DelaunayTriangle t )
    {
        _triangles.add( t );
    }

    public void addTriangles( List<DelaunayTriangle> list )
    {
        _triangles.addAll( list );
    }

    public void clearTriangulation()
    {
        _triangles.clear();            
    }

    public void prepareTriangulation( TriangulationContext<?> tcx )
    {
        if( _triangles == null )
        {
            _triangles = new ArrayList<DelaunayTriangle>( _points.size() );            
        }
        else
        {
            _triangles.clear();                        
        }
        tcx.addPoints( _points );
    }
}
