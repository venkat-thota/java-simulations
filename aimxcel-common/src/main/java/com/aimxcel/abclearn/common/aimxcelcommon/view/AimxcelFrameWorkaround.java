
 
package com.aimxcel.abclearn.common.aimxcelcommon.view;

import java.awt.HeadlessException;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;


public class AimxcelFrameWorkaround extends AimxcelFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AimxcelFrameWorkaround( AimxcelApplication application ) throws HeadlessException {
        super( application );
    }

    public void repaint( long tm, int x, int y, int width, int height ) {
        super.repaint( tm, x, y, width, height ); // in case other important stuff happens here.
        update( getGraphics() );
    }
}
