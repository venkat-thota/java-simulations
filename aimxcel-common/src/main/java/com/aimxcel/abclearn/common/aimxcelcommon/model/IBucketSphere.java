 
package com.aimxcel.abclearn.common.aimxcelcommon.model;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.IBucketSphere;
//import com.aimxcel.abclearn.common.aimxcelcommon.model.Listener;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;


public interface IBucketSphere<U extends IBucketSphere> {

    double getRadius();

    /*---------------------------------------------------------------------------*
    * current position
    *----------------------------------------------------------------------------*/

    Vector2D getPosition();

    void setPosition( Vector2D position );

    /*---------------------------------------------------------------------------*
    * destination (where the IBucketSphere will end up)
    *----------------------------------------------------------------------------*/

    Vector2D getDestination();

    void setDestination( Vector2D destination );

    void setPositionAndDestination( Vector2D position );

    /*---------------------------------------------------------------------------*
    * position events. should fire if the IBucketSphere moves
    *----------------------------------------------------------------------------*/

    void addPositionListener( SimpleObserver observer );

    void removePositionListener( SimpleObserver observer );

    /*---------------------------------------------------------------------------*
    * general events (grabbed, dropped, removed) that the bucket needs to know
    *----------------------------------------------------------------------------*/

    void addListener( Listener<U> listener );

    void removeListener( Listener<U> listener );

    public static interface Listener<T extends IBucketSphere> {
        void grabbedByUser( T particle );

        void droppedByUser( T particle );

        void removedFromModel( T particle );
    }

    public static class Adapter<T extends IBucketSphere> implements Listener<T> {
        public void grabbedByUser( T particle ) {
        }

        public void droppedByUser( T particle ) {
        }

        public void removedFromModel( T particle ) {
        }
    }

}
