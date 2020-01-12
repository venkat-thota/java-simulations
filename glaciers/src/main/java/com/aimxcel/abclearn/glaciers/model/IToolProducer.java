
package com.aimxcel.abclearn.glaciers.model;

import java.awt.geom.Point2D;
public interface IToolProducer {
    
    public BoreholeDrill addBoreholeDrill( Point2D position );
    
    public GlacialBudgetMeter addGlacialBudgetMeter( Point2D position );
    
    public GPSReceiver createGPSReceiver( Point2D position );
    
    public IceThicknessTool addIceThicknessTool( Point2D position );
    
    public Thermometer addThermometer( Point2D position );
    
    public TracerFlag addTracerFlag( Point2D position );
    
    /**
     * Removes a specified tool.
     * @param tool
     */
    public void removeTool( AbstractTool tool );
    
    /**
     * Removes all tools.
     */
    public void removeAllTools();
    
    /**
     * Listeners interested in when tools are added or removed should implement this interface.
     */
    public static interface IToolProducerListener {
        public void toolAdded( AbstractTool tool );
        public void toolRemoved( AbstractTool tool );
    }
    
    /**
     * Adds an IToolProducerListener, who will be notified when tools are added or removed.
     * @param listener
     */
    public void addToolProducerListener( IToolProducerListener listener );
    
    /**
     * Removes an IToolProducerListener.
     * @param listener
     */
    public void removeToolProducerListener( IToolProducerListener listener );
}
