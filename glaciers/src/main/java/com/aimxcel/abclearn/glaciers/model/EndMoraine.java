
package com.aimxcel.abclearn.glaciers.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import com.aimxcel.abclearn.glaciers.model.Glacier.GlacierAdapter;
import com.aimxcel.abclearn.glaciers.model.Glacier.GlacierListener;

public class EndMoraine {
    
    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------
    
    private static final boolean DEBUG_OUTPUT_PRUNING = false;
    
    private static final double Z_OVERLAP_THRESHOLD = 1; // meters, see overlaps()
    private static final double MORAINE_LENGTH = 10; // meters
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    private final Glacier _glacier;
    private final GlacierListener _glacierListener;
    private final ArrayList _debrisList;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    public EndMoraine( Glacier glacier ) {
        
        _glacier = glacier;
        
        _glacierListener = new GlacierAdapter() {
            // when the terminus is moving, the debris is no longer part of the end moraine
            public void steadyStateChanged() {
                if ( !_glacier.isSteadyState() ) {
                    removeAllDebris();
                }
            }
        };
        _glacier.addGlacierListener( _glacierListener );
        
        _debrisList = new ArrayList();
    }
    
    public void cleanup() {
        _glacier.removeGlacierListener( _glacierListener );
    }
    
    //----------------------------------------------------------------------------
    // Setters and getters
    //----------------------------------------------------------------------------
    
    /**
     * The end moraine's position is at the glaicer's terminus.
     * @return Point2D
     */
    public Point2D getPositionReference() {
        return _glacier.getTerminusPositionReference();
    }
    
    //----------------------------------------------------------------------------
    // Debris management
    //----------------------------------------------------------------------------
    
    /**
     * Adds debris to the moraine.
     * Clients should take care to add debris only when it hits the valley floor.
     * If the glacier is not in a steady state, no end moraine can build up, and this call is a no-op.
     * 
     * @param debris
     */
    public void addDebris( Debris debris ) {

        if ( _glacier.isSteadyState() ) {
            
            if ( !debris.isOnValleyFloor() ) {
                System.out.println( "WARNING - EndMoraine.addDebris: ignoring attempt to add debris that is not on the valley floor" );
            }
            else if ( debris.getX() > _glacier.getTerminusX() + MORAINE_LENGTH ) {
                // As the glacier recedes, some debris will hit the valley floor far enough 
                // downvalley to be considered past the end moraine. Ignore any such debris.
            }
            else {
                if ( overlaps( debris, _debrisList ) ) {
                    pruneDebris( debris );
                }
                else {
                    _debrisList.add( debris );
                }
            }
        }
    }
    
    /**
     * Removes all debris from the moraine.
     * This has no effect on the Debris objects, it simply removes them from the moraine.
     */
    public void removeAllDebris() {
        _debrisList.clear();
    }
    
    /*
     * Prunes debris. The easiest way to do this is to tell the debris to delete itself.
     * Anyone who implements DebrisListener will be notified of the deletion.
     */
    private static void pruneDebris( Debris debris ) {
        if ( DEBUG_OUTPUT_PRUNING ) {
            System.out.println( "EndMoraine.pruneDebris: pruning debris at " + debris.getPositionReference() );
        }
        debris.deleteSelf();
    }
    
    //----------------------------------------------------------------------------
    // utilities
    //----------------------------------------------------------------------------
    
    /*
     * Checks to see if a specific debris overlaps with any of the other debris in the moraine.
     * 
     * For the sake of efficiency, we assume that all of the debris in the moraine have similar
     * x positions (at or near the terminus) and elevations (on the valley floor).  So we only 
     * need to check their z positions (distance across the valley floor) for overlap.
     */
    private static boolean overlaps( Debris debris, ArrayList debrisList ) {
        boolean overlaps = false;
        if ( debrisList.size() > 0 ) {
            Iterator i = debrisList.iterator();
            Debris otherDebris = null;
            while ( i.hasNext() && !overlaps ) {
                otherDebris = (Debris) i.next();
                if ( Math.abs( otherDebris.getZ() - debris.getZ() ) <= Z_OVERLAP_THRESHOLD ) {
                    overlaps = true;
                }
            }
        }
        return overlaps;
    }
}
