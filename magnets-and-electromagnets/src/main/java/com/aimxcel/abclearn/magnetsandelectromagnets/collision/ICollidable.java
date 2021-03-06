

package com.aimxcel.abclearn.magnetsandelectromagnets.collision;

import java.awt.Shape;


public interface ICollidable {

    /**
     * Gets the CollisionDetector associated with the object.
     * 
     * @return the CollisionDetector
     */
    public CollisionDetector getCollisionDetector();
    
    /**
     * Gets the bounds that define the collidable area of the object.
     * If an object is invisible, the bounds returned should be null.
     * 
     * @return an array of Shapes, possibly null
     */
    public Shape[] getCollisionBounds();
}
