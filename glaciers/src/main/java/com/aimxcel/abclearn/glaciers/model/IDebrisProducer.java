
package com.aimxcel.abclearn.glaciers.model;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Point3D;

public interface IDebrisProducer {

    /**
     * Adds debris as a specified position.
     * @param position 3D position (meters)
     * @return
     */
    public Debris addDebris( Point3D position );
    
    /**
     * Removes the specified debris.
     * @param debris
     */
    public void removeDebris( Debris debris );
    
    /**
     * Removes all debris.
     */
    public void removeAllDebris();
    
    /**
     * IDebrisProducerListener is the interface implemented by all parties
     * that are interested in debris creation and deletion.
     */
    public interface IDebrisProducerListener {
        public void debrisAdded( Debris debris );
        public void debrisRemoved( Debris debris );
    }
    
    /**
     * Adds an IDebrisProducerListener.
     * @param listener
     */
    public void addDebrisProducerListener( IDebrisProducerListener listener );
    
    /**
     * Removes an IDebrisProducerListener.
     * @param listener
     */
    public void removeDebrisProducerListener( IDebrisProducerListener listener );
}
