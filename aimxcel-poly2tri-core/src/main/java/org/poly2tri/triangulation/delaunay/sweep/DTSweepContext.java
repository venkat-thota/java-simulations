package org.poly2tri.triangulation.delaunay.sweep;

import java.util.Collections;

import org.poly2tri.triangulation.Triangulatable;
import org.poly2tri.triangulation.TriangulationAlgorithm;
import org.poly2tri.triangulation.TriangulationConstraint;
import org.poly2tri.triangulation.TriangulationContext;
import org.poly2tri.triangulation.TriangulationPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;
import org.poly2tri.triangulation.point.TPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DTSweepContext extends TriangulationContext<DTSweepDebugContext>
{
    private final static Logger logger = LoggerFactory.getLogger( DTSweepContext.class );

    // Inital triangle factor, seed triangle will extend 30% of 
    // PointSet width to both left and right.
    private final float ALPHA = 0.3f;

    /** Advancing front **/
    protected AdvancingFront aFront;
    /** head point used with advancing front */
    private TriangulationPoint _head;
    /** tail point used with advancing front */
    private TriangulationPoint _tail;
    protected Basin basin = new Basin();
    protected EdgeEvent edgeEvent = new EdgeEvent();
    
    private DTSweepPointComparator _comparator = new DTSweepPointComparator();
    
    public DTSweepContext()
    {
        clear();
    }
        
    public void isDebugEnabled( boolean b )
    {
        if( b )
        {
            if( _debug == null )
            {
                _debug = new DTSweepDebugContext(this);
            }
        }
        _debugEnabled  = b;
    }

    public void removeFromList( DelaunayTriangle triangle )
    {
        _triList.remove( triangle );
        // TODO: remove all neighbor pointers to this triangle
//        for( int i=0; i<3; i++ )
//        {
//            if( triangle.neighbors[i] != null )
//            {
//                triangle.neighbors[i].clearNeighbor( triangle );
//            }
//        }
//        triangle.clearNeighbors();
    }

    public void meshClean( DelaunayTriangle triangle )
    {
        meshCleanReq( triangle );
    }

    private void meshCleanReq( DelaunayTriangle triangle )
    {
        if( triangle != null && !triangle.isInterior() )
        {
            triangle.isInterior( true );
            _triUnit.addTriangle( triangle );
            for( int i = 0; i < 3; i++ )
            {
                if( !triangle.cEdge[i] )
                {
                    meshCleanReq( triangle.neighbors[i] );
                }
            }
        }
    }

    public void clear()
    {
        super.clear();
        _triList.clear();
    }

    public AdvancingFront getAdvancingFront()
    {
        return aFront;
    }

    public void setHead( TriangulationPoint p1 ) { _head = p1; }
    public TriangulationPoint getHead() { return _head; }

    public void setTail( TriangulationPoint p1 ) { _tail = p1; }
    public TriangulationPoint getTail() { return _tail; }

    public void addNode( AdvancingFrontNode node )
    {
//        System.out.println( "add:" + node.key + ":" + System.identityHashCode(node.key));
//        m_nodeTree.put( node.getKey(), node );
        aFront.addNode( node );
    }

    public void removeNode( AdvancingFrontNode node )
    {
//        System.out.println( "remove:" + node.key + ":" + System.identityHashCode(node.key));
//        m_nodeTree.delete( node.getKey() );
        aFront.removeNode( node );
    }

    public AdvancingFrontNode locateNode( TriangulationPoint point )
    {
        return aFront.locateNode( point );
    }

    public void createAdvancingFront()
    {
        AdvancingFrontNode head,tail,middle;
        // Initial triangle
        DelaunayTriangle iTriangle = new DelaunayTriangle( _points.get(0), 
                                                           getTail(), 
                                                           getHead() );
        addToList( iTriangle );
        
        head = new AdvancingFrontNode( iTriangle.points[1] );
        head.triangle = iTriangle;
        middle = new AdvancingFrontNode( iTriangle.points[0] );
        middle.triangle = iTriangle;
        tail = new AdvancingFrontNode( iTriangle.points[2] );

        aFront = new AdvancingFront( head, tail ); 
        aFront.addNode( middle );
        
        // TODO: I think it would be more intuitive if head is middles next and not previous
        //       so swap head and tail
        aFront.head.next = middle;
        middle.next = aFront.tail;
        middle.prev = aFront.head;
        aFront.tail.prev = middle;
    }
    
    class Basin
    {
        AdvancingFrontNode leftNode;
        AdvancingFrontNode bottomNode;
        AdvancingFrontNode rightNode;
        public double width;
        public boolean leftHighest;        
    }
    
    class EdgeEvent
    {
        DTSweepConstraint constrainedEdge;
        public boolean right;
    }

    /**
     * Try to map a node to all sides of this triangle that don't have 
     * a neighbor.
     * 
     * @param t
     */
    public void mapTriangleToNodes( DelaunayTriangle t )
    {
        AdvancingFrontNode n;
        for( int i=0; i<3; i++ )
        {
            if( t.neighbors[i] == null )
            {
                n = aFront.locatePoint( t.pointCW( t.points[i] ) );
                if( n != null )
                {
                    n.triangle = t;
                }
            }            
        }        
    }

    @Override
    public void prepareTriangulation( Triangulatable t )
    {
        super.prepareTriangulation( t );

        double xmax, xmin;
        double ymax, ymin;

        xmax = xmin = _points.get(0).getX();
        ymax = ymin = _points.get(0).getY();
        // Calculate bounds. Should be combined with the sorting
        for( TriangulationPoint p : _points )
        {
            if( p.getX() > xmax )
                xmax = p.getX();
            if( p.getX() < xmin )
                xmin = p.getX();
            if( p.getY() > ymax )
                ymax = p.getY();
            if( p.getY() < ymin )
                ymin = p.getY();
        }

        double deltaX = ALPHA * ( xmax - xmin );
        double deltaY = ALPHA * ( ymax - ymin );
        TPoint p1 = new TPoint( xmax + deltaX, ymin - deltaY );
        TPoint p2 = new TPoint( xmin - deltaX, ymin - deltaY );

        setHead( p1 );
        setTail( p2 );

//        long time = System.nanoTime();
        // Sort the points along y-axis
        Collections.sort( _points, _comparator );
//        logger.info( "Triangulation setup [{}ms]", ( System.nanoTime() - time ) / 1e6 );
    }


    public void finalizeTriangulation()
    {
        _triUnit.addTriangles( _triList );
        _triList.clear();
    }

    @Override
    public TriangulationConstraint newConstraint( TriangulationPoint a, TriangulationPoint b )
    {
        return new DTSweepConstraint( a, b );        
    }

    @Override
    public TriangulationAlgorithm algorithm()
    {
        return TriangulationAlgorithm.DTSweep;
    }
}
