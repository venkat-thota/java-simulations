

package com.aimxcel.abclearn.core.aimxcelcore;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;


public class CoreAimxcelApplication extends AimxcelApplication {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final AimxcelResources RESOURCES = new AimxcelResources( "aimxcel-core" );

    public CoreAimxcelApplication( AimxcelApplicationConfig config ) {
        this( config, new TabbedModulePaneCore() );
    }

    public CoreAimxcelApplication( AimxcelApplicationConfig config, TabbedModulePaneCore tabbedModulePane ) {
        super( config, tabbedModulePane );

        // Add Core-specific items to the developer menu
        getAimxcelFrame().getDeveloperMenu().add( new TabbedPanePropertiesMenuItem( getAimxcelFrame(), tabbedModulePane ) );
    }
}