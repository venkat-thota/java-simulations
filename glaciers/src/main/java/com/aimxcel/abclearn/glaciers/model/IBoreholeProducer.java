package com.aimxcel.abclearn.glaciers.model;

import java.awt.geom.Point2D;

public interface IBoreholeProducer {

   
    public Borehole addBorehole( Point2D position );
    
   
    public void removeBorehole( Borehole borehole );
    
    /**
     * Removes all boreholes.
     */
    public void removeAllBoreholes();
    
    
    public interface IBoreholeProducerListener {
        public void boreholeAdded( Borehole borehole );
        public void boreholeRemoved( Borehole borehole );
    }
    
    /**
     * Adds an IBoreholeProducerListener.
     * @param listener
     */
    public void addBoreholeProducerListener( IBoreholeProducerListener listener );
    
    /**
     * Removes an IBoreholeProducerListener.
     * @param listener
     */
    public void removeBoreholeProducerListener( IBoreholeProducerListener listener );
}
