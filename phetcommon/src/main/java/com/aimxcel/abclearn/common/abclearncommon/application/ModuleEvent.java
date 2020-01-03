

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package com.aimxcel.abclearn.common.abclearncommon.application;

import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;

/**
 * An event class sent to ModuleObservers.
 *
 * @author Ron LeMaster
 * @version $Revision$
 */
public class ModuleEvent {
    private AbcLearnApplication phetApplication;
    private Module module;

    /**
     * Constructs a new ModuleEvent.
     *
     * @param phetApplication
     * @param module
     */
    public ModuleEvent( AbcLearnApplication phetApplication, Module module ) {
        this.phetApplication = phetApplication;
        this.module = module;
    }

    /**
     * Gets the module associated with this ModuleEvent.
     *
     * @return the module associated with this ModuleEvent.
     */
    public Module getModule() {
        return module;
    }

    /**
     * Gets the AbcLearnApplication associated with this ModuleEvent.
     *
     * @return the AbcLearnApplication associated with this ModuleEvent.
     */
    public AbcLearnApplication getAbcLearnApplication() {
        return phetApplication;
    }
}
