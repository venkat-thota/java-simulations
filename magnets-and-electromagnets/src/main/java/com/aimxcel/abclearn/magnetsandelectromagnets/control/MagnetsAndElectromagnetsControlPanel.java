

package com.aimxcel.abclearn.magnetsandelectromagnets.control;

import com.aimxcel.abclearn.common.aimxcelcommon.view.ControlPanel;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsResources;


public class MagnetsAndElectromagnetsControlPanel extends ControlPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// a default amount of vertical space 
    private static final int DEFAULT_VERTICAL_SPACE = 8;
    
    public MagnetsAndElectromagnetsControlPanel() {
        super();
        
        // Set the control panel's minimum width.
        int width = MagnetsAndElectromagnetsResources.getInt( "ControlPanel.width", 225 );
        setMinimumWidth( width );
    }
    
    public void addDefaultVerticalSpace() {
        addVerticalSpace( MagnetsAndElectromagnetsControlPanel.DEFAULT_VERTICAL_SPACE );
    }
}
