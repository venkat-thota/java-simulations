package org.poly2tri.triangulation;

import java.util.List;

import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

public interface Triangulatable
{
    public void prepareTriangulation( TriangulationContext<?> tcx );
    
    public List<DelaunayTriangle> getTriangles();
    public List<TriangulationPoint> getPoints();
    public void addTriangle( DelaunayTriangle t );
    public void addTriangles( List<DelaunayTriangle> list );
    public void clearTriangulation();
    
    public TriangulationMode getTriangulationMode();
}
