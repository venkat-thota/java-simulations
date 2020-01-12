package org.poly2tri.geometry.polygon;

import java.util.ArrayList;
import java.util.List;

public class PolygonSet
{
    protected ArrayList<Polygon> _polygons = new ArrayList<Polygon>();
    
    public PolygonSet()
    {
    }

    public PolygonSet( Polygon poly )
    {
        _polygons.add( poly );
    }

    public void add( Polygon p )
    {
        _polygons.add( p );
    }
    
    public List<Polygon> getPolygons()
    {
        return _polygons;
    }
}
