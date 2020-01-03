

package edu.colorado.phet.common.piccolophet;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnResources;

/**
 * PiccoloAbcLearnApplication
 * <p/>
 * Allows for the use of components and graphics that require Piccolo support.
 * Piccolo-dependent items that can be specified:
 * <ul>
 * <li>AbcLearnTabbedPane is used in Module instances. (JTabbedPane can be specified in the constructor, if
 * desired.)
 * </ul>
 *
 * @author Ron LeMaster
 */
public class PiccoloAbcLearnApplication extends AbcLearnApplication {
    public static final AbcLearnResources RESOURCES = new AbcLearnResources( "piccolo-phet" );

    public PiccoloAbcLearnApplication( AbcLearnApplicationConfig config ) {
        this( config, new TabbedModulePanePiccolo() );
    }

    public PiccoloAbcLearnApplication( AbcLearnApplicationConfig config, TabbedModulePanePiccolo tabbedModulePane ) {
        super( config, tabbedModulePane );

        // Add Piccolo-specific items to the developer menu
        getAbcLearnFrame().getDeveloperMenu().add( new TabbedPanePropertiesMenuItem( getAbcLearnFrame(), tabbedModulePane ) );
    }
}