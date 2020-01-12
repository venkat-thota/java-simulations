
package com.aimxcel.abclearn.greenhouse;

import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;

public class GreenhouseDefaults {

    /* Not intended for instantiation */
    private GreenhouseDefaults() {}
    
    // Clock
    public static final int CLOCK_FRAME_RATE = 25; // fps, frames per second (wall time)
    
    // Model-view transform for intermediate coordinates.
    public static final PDimension INTERMEDIATE_RENDERING_SIZE = new PDimension( 786, 786 );
}
