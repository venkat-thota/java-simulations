

package com.aimxcel.abclearn.core.aimxcelcore;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class PiccoloAimxcelApplication extends AimxcelApplication {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final AimxcelResources RESOURCES = new AimxcelResources( "piccolo-aimxcel" );

    public PiccoloAimxcelApplication( AimxcelApplicationConfig config ) {
        this( config, new TabbedModulePanePiccolo() );
    }

    public PiccoloAimxcelApplication( AimxcelApplicationConfig config, TabbedModulePanePiccolo tabbedModulePane ) {
        super( config, tabbedModulePane );

        // Add Piccolo-specific items to the developer menu
        getAimxcelFrame().getDeveloperMenu().add( new TabbedPanePropertiesMenuItem( getAimxcelFrame(), tabbedModulePane ) );
    }
}