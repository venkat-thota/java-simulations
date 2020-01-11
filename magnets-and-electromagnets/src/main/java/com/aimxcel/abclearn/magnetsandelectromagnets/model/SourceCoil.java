

package com.aimxcel.abclearn.magnetsandelectromagnets.model;


public class SourceCoil extends AbstractCoil {
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    /**
     * Sole constructor.
     */
    public SourceCoil() {
        super();
        
        // pack the loops close together
        setLoopSpacing( getWireWidth() );
    }
    
    //----------------------------------------------------------------------------
    // AbstractCoil overrides
    //----------------------------------------------------------------------------
    
    /**
     * If the wire width is changed, also change the loop spacing so
     * that the loops remain packed close together.
     * 
     * @param wireWidth
     */
    public void setWireWidth( double wireWidth ) {
        super.setWireWidth( wireWidth );
        setLoopSpacing( getWireWidth() );
    }
}
