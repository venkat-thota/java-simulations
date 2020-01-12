
package com.aimxcel.abclearn.glaciers.model;

import com.aimxcel.abclearn.glaciers.GlaciersConstants;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
public class GlaciersClock extends ConstantDtClock {

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    public GlaciersClock( ) {
        super( 1000 / GlaciersConstants.CLOCK_FRAME_RATE_RANGE.getDefault(), GlaciersConstants.CLOCK_DT );
    }
    
    
    public void setFrameRate( int frameRate ) {
        setDelay( 1000 / frameRate );
    }
   
    public int getFrameRate() {
        return 1000 / getDelay();
    }
}
