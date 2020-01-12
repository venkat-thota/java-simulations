package org.poly2tri.triangulation;

public interface TriangulationProcessListener
{
    public void triangulationEvent( TriangulationProcessEvent e, Triangulatable unit );
}
