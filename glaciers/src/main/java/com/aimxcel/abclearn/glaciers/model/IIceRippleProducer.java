
package com.aimxcel.abclearn.glaciers.model;

import java.awt.Dimension;
public interface IIceRippleProducer {

   
    public IceRipple addIceRipple( double x, Dimension size, double zOffset );
    
    /**
     * Removes the specified ripple.
     * @param ripple
     */
    public void removeIceRipple( IceRipple ripple );
    
    /**
     * Removes all ripples.
     */
    public void removeAllIceRipples();
    
    /**
     * Interface implemented by all parties that are interested in ripple creation and deletion.
     */
    public interface IIceRippleProducerListener {
        public void rippleAdded( IceRipple ripple );
        public void rippleRemoved( IceRipple ripple );
    }
    
    /**
     * Adds an IIceRippleProducerListener.
     * @param listener
     */
    public void addIceRippleProducerListener( IIceRippleProducerListener listener );
    
    /**
     * Removes an IIceRippleProducerListener.
     * @param listener
     */
    public void removeIceRippleProducerListener( IIceRippleProducerListener listener );
}
