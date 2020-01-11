
package com.aimxcel.abclearn.aimxcelgraphics.view;

import static com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig.DEFAULT_FRAME_SETUP;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;


public class ApparatusPanel3 extends ApparatusPanel2 {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int referenceWidth;
    private final int referenceHeight;

    //Construct an ApparatusPanel3 with the specified reference size
    public ApparatusPanel3( IClock clock, int referenceWidth, int referenceHeight ) {
        super( clock );
        this.referenceWidth = referenceWidth;
        this.referenceHeight = referenceHeight;
    }

    @Override public void setReferenceSize() {
        //Note: the next line is assuming the sim uses the DEFAULT_FRAME_SETUP.  If it is an ApparatusPanel3 application using a different frame size, this mismatch could cause problems
        if ( DEFAULT_FRAME_SETUP.isShrunken() ) {
            //Use the reference sizes set in the constructor instead of trying to automatically determine them, since that causes problems on low-res screens, see #2860
            setReferenceSize( referenceWidth, referenceHeight );
        }
        else {
            //If the resolution was high enough, allow the application to choose the optimal rendering size to reduce artifacts
            super.setReferenceSize();
        }
    }
}