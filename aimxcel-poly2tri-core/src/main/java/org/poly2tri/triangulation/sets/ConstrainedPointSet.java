package org.poly2tri.triangulation.sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.poly2tri.triangulation.TriangulationContext;
import org.poly2tri.triangulation.TriangulationMode;
import org.poly2tri.triangulation.TriangulationPoint;

public class ConstrainedPointSet extends PointSet
{
    int[] _index;
    List<TriangulationPoint> _constrainedPointList = null;

    public ConstrainedPointSet( List<TriangulationPoint> points, int[] index )
    {
        super( points );
        _index = index;  
    }

    /**
     * 
     * @param points - A list of all points in PointSet
     * @param constraints - Pairs of two points defining a constraint, all points <b>must</b> be part of given PointSet!
     */
    public ConstrainedPointSet( List<TriangulationPoint> points, List<TriangulationPoint> constraints )
    {
        super( points );
        _constrainedPointList = new ArrayList<TriangulationPoint>();
        _constrainedPointList.addAll(constraints);  
    }

    @Override
    public TriangulationMode getTriangulationMode()
    {
        return TriangulationMode.CONSTRAINED;
    }

    public int[] getEdgeIndex()
    {
        return _index;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void prepareTriangulation( TriangulationContext tcx )
    {
        super.prepareTriangulation( tcx );
        if( _constrainedPointList != null )
        {
        	TriangulationPoint p1,p2;
        	Iterator iterator = _constrainedPointList.iterator();
    		while(iterator.hasNext())
    		{
    			p1 = (TriangulationPoint)iterator.next();
    			p2 = (TriangulationPoint)iterator.next();
    			tcx.newConstraint(p1,p2);
    		}
        }
        else
        {
	        for( int i = 0; i < _index.length; i+=2 )
	        {
	            // XXX: must change!!
	            tcx.newConstraint( _points.get( _index[i] ), _points.get( _index[i+1] ) );
	        }
        }
    }

    /**
     * TODO: TO BE IMPLEMENTED!
     * Peforms a validation on given input<br>
     * 1. Check's if there any constraint edges are crossing or collinear<br>
     * 2. 
     * @return
     */
    public boolean isValid()
    {
        return true;
    }
}
