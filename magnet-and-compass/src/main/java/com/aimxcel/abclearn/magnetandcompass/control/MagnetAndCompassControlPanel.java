

package com.aimxcel.abclearn.magnetandcompass.control;

import com.aimxcel.abclearn.common.aimxcelcommon.view.ControlPanel;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassResources;



public class MagnetAndCompassControlPanel extends ControlPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// a default amount of vertical space 
    private static final int DEFAULT_VERTICAL_SPACE = 8;
    
    public MagnetAndCompassControlPanel() {
        super();
        
        // Set the control panel's minimum width.
        int width = MagnetAndCompassResources.getInt( "ControlPanel.width", 225 );
        setMinimumWidth( width );
    }
    
    public void addDefaultVerticalSpace() {
        addVerticalSpace( MagnetAndCompassControlPanel.DEFAULT_VERTICAL_SPACE );
    }
}
