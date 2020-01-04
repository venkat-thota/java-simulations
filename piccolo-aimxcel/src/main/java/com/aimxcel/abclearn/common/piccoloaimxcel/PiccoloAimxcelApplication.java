

package com.aimxcel.abclearn.common.piccoloaimxcel;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;

/**
 * PiccoloAimxcelApplication
 * <p/>
 * Allows for the use of components and graphics that require Piccolo support.
 * Piccolo-dependent items that can be specified:
 * <ul>
 * <li>AimxcelTabbedPane is used in Module instances. (JTabbedPane can be specified in the constructor, if
 * desired.)
 * </ul>
 *
 * @author Ron LeMaster
 */
public class PiccoloAimxcelApplication extends AimxcelApplication {
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