package org.poly2tri;

import org.poly2tri.geometry.polygon.Polygon;
import org.poly2tri.geometry.polygon.PolygonSet;
import org.poly2tri.triangulation.Triangulatable;
import org.poly2tri.triangulation.TriangulationAlgorithm;
import org.poly2tri.triangulation.TriangulationContext;
import org.poly2tri.triangulation.TriangulationMode;
import org.poly2tri.triangulation.TriangulationProcess;
import org.poly2tri.triangulation.delaunay.sweep.DTSweep;
import org.poly2tri.triangulation.delaunay.sweep.DTSweepContext;
import org.poly2tri.triangulation.sets.ConstrainedPointSet;
import org.poly2tri.triangulation.sets.PointSet;
import org.poly2tri.triangulation.util.PolygonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Poly2Tri
{
    private final static Logger logger = LoggerFactory.getLogger( Poly2Tri.class );

    private static final TriangulationAlgorithm _defaultAlgorithm = TriangulationAlgorithm.DTSweep;
    
    public static void triangulate( PolygonSet ps )
    {
        TriangulationContext<?> tcx = createContext( _defaultAlgorithm );
        for( Polygon p : ps.getPolygons() )
        {
            tcx.prepareTriangulation( p );
            triangulate( tcx );            
            tcx.clear();
        }
    }

    public static void triangulate( Polygon p )
    {
        triangulate( _defaultAlgorithm, p );            
    }

    public static void triangulate( ConstrainedPointSet cps )
    {
        triangulate( _defaultAlgorithm, cps );        
    }

    public static void triangulate( PointSet ps )
    {
        triangulate( _defaultAlgorithm, ps );                
    }

    public static TriangulationContext<?> createContext( TriangulationAlgorithm algorithm )
    {
        switch( algorithm )
        {
            case DTSweep:
            default:
                return new DTSweepContext();
        }
    }

    public static void triangulate( TriangulationAlgorithm algorithm,
                                    Triangulatable t )
    {
        TriangulationContext<?> tcx;
        
//        long time = System.nanoTime();
        tcx = createContext( algorithm );
        tcx.prepareTriangulation( t );
        triangulate( tcx );
//        logger.info( "Triangulation of {} points [{}ms]", tcx.getPoints().size(), ( System.nanoTime() - time ) / 1e6 );
    }
    
    public static void triangulate( TriangulationContext<?> tcx )
    {
        switch( tcx.algorithm() )
        {
            case DTSweep:
            default:
               DTSweep.triangulate( (DTSweepContext)tcx );
        }        
    }
    
    /**
     * Will do a warmup run to let the JVM optimize the triangulation code 
     */
    public static void warmup()
    {        
        /*
         * After a method is run 10000 times, the Hotspot compiler will compile
         * it into native code. Periodically, the Hotspot compiler may recompile
         * the method. After an unspecified amount of time, then the compilation
         * system should become quiet.
         */
        Polygon poly = PolygonGenerator.RandomCircleSweep2( 50, 50000 );
        TriangulationProcess process = new TriangulationProcess();
        process.triangulate( poly );
    }
}
