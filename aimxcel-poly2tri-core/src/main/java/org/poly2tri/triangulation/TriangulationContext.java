package org.poly2tri.triangulation;

import java.util.ArrayList;
import java.util.List;

import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

public abstract class TriangulationContext<A extends TriangulationDebugContext>
{
    protected A _debug;
    protected boolean _debugEnabled = false;
    
    protected ArrayList<DelaunayTriangle> _triList = new ArrayList<DelaunayTriangle>();

    protected ArrayList<TriangulationPoint> _points = new ArrayList<TriangulationPoint>(200);
    protected TriangulationMode _triangulationMode;
    protected Triangulatable _triUnit;

    private boolean _terminated = false;
    private boolean _waitUntilNotified;

    private int _stepTime = -1;
    private int _stepCount = 0;
    public int getStepCount() { return _stepCount; }

    public void done()
    {
        _stepCount++;
    }

    public abstract TriangulationAlgorithm algorithm();
    
    public void prepareTriangulation( Triangulatable t )
    {
        _triUnit = t;
        _triangulationMode = t.getTriangulationMode();
        t.prepareTriangulation( this );
    }
    
    public abstract TriangulationConstraint newConstraint( TriangulationPoint a, TriangulationPoint b );
    
    public void addToList( DelaunayTriangle triangle )
    {
        _triList.add( triangle );
    }
        
    public List<DelaunayTriangle> getTriangles()
    {
        return _triList;
    }

    public Triangulatable getTriangulatable()
    {
        return _triUnit;
    }
    
    public List<TriangulationPoint> getPoints()
    {
        return _points;
    }

    public synchronized void update(String message)
    {
        if( _debugEnabled )
        {
            try
            {
                synchronized( this )
                {
                    _stepCount++;
                    if( _stepTime > 0 )
                    {
                        wait( (int)_stepTime );
                        /** Can we resume execution or are we expected to wait? */ 
                        if( _waitUntilNotified )
                        {
                            wait();
                        }
                    }
                    else
                    {
                        wait();
                    }
                    // We have been notified
                    _waitUntilNotified = false;
                }
            }
            catch( InterruptedException e )
            {
                update("Triangulation was interrupted");
            }
        }
        if( _terminated )
        {
            throw new RuntimeException( "Triangulation process terminated before completion");
        }
    }
    
    public void clear()
    {
        _points.clear();
        _terminated = false;
        if( _debug != null )
        {
            _debug.clear();
        }
        _stepCount=0;
    }

    public TriangulationMode getTriangulationMode()
    {
        return _triangulationMode;
    }
    
    public synchronized void waitUntilNotified(boolean b)
    {
        _waitUntilNotified = b;
    }

    public void terminateTriangulation()
    {
        _terminated=true;
    }

    public boolean isDebugEnabled()
    {
        return _debugEnabled;
    }
    
    public abstract void isDebugEnabled( boolean b );

    public A getDebugContext()
    {
        return _debug;
    }
    
    public void addPoints( List<TriangulationPoint> points )
    {
        _points.addAll( points );
    }
}
