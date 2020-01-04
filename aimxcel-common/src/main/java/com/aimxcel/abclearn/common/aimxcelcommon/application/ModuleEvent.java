
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;

public class ModuleEvent {
    private AimxcelApplication aimxcelApplication;
    private Module module;

    /**
     * Constructs a new ModuleEvent.
     *
     * @param aimxcelApplication
     * @param module
     */
    public ModuleEvent( AimxcelApplication aimxcelApplication, Module module ) {
        this.aimxcelApplication = aimxcelApplication;
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
     * Gets the AimxcelApplication associated with this ModuleEvent.
     *
     * @return the AimxcelApplication associated with this ModuleEvent.
     */
    public AimxcelApplication getAimxcelApplication() {
        return aimxcelApplication;
    }
}
