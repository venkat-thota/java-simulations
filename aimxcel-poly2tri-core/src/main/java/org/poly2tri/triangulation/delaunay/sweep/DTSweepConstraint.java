package org.poly2tri.triangulation.delaunay.sweep;

import org.poly2tri.triangulation.TriangulationConstraint;
import org.poly2tri.triangulation.TriangulationPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DTSweepConstraint extends TriangulationConstraint
{
    private final static Logger logger = LoggerFactory.getLogger( DTSweepConstraint.class );

    public TriangulationPoint p;
    public TriangulationPoint q;
    
   
    public DTSweepConstraint( TriangulationPoint p1, TriangulationPoint p2 )
//        throws DuplicatePointException
    {
        p = p1;
        q = p2;
        if( p1.getY() > p2.getY() )
        {
            q = p1;
            p = p2;
        }
        else if( p1.getY() == p2.getY() )
        {
            if( p1.getX() > p2.getX() )
            {
                q = p1;
                p = p2;
            }
            else if( p1.getX() == p2.getX() )
            {
                logger.info( "Failed to create constraint {}={}", p1, p2 );
//                throw new DuplicatePointException( p1 + "=" + p2 );
//                return;
            }
        }
        q.addEdge(this);
    }

//    public TPoint intersect( TPoint a, TPoint b )
//    {
//        double pqx,pqy,bax,bay,t;
//        
//        pqx = p.getX()-q.getX();
//        pqy = p.getY()-q.getY();
//        t = pqy*(a.getX()-q.getX()) - pqx*(a.getY()-q.getY() );
//        t /= pqx*(b.getY()-a.getY()) - pqy*(b.getX()-a.getX());
//        bax = t*(b.getX()-a.getX()) + a.getX();
//        bay = t*(b.getY()-a.getY()) + a.getY();
//        return new TPoint( bax, bay );
//    }

    public TriangulationPoint getP()
    {
        return p;
    }

    public TriangulationPoint getQ()
    {
        return q;
    }
}
