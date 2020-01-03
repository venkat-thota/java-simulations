
package com.aimxcel.abclearn.common.abclearncommon.application;

import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;

public class ModuleEvent {
    private AbcLearnApplication abcLearnApplication;
    private Module module;

    /**
     * Constructs a new ModuleEvent.
     *
     * @param abcLearnApplication
     * @param module
     */
    public ModuleEvent( AbcLearnApplication abcLearnApplication, Module module ) {
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
    public AbcLearnApplication getAbcLearnApplication() {
        return abcLearnApplication;
    }
}
