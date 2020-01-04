
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;

public class ModuleEvent {
    private AimxcelApplication abcLearnApplication;
    private Module module;

    /**
     * Constructs a new ModuleEvent.
     *
     * @param abcLearnApplication
     * @param module
     */
    public ModuleEvent( AimxcelApplication abcLearnApplication, Module module ) {
        this.abcLearnApplication = abcLearnApplication;
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
    public AimxcelApplication getAbcLearnApplication() {
        return abcLearnApplication;
    }
}
