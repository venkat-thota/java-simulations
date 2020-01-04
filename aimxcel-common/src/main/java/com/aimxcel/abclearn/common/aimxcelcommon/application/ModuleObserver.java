
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import java.util.EventListener;

import com.aimxcel.abclearn.common.aimxcelcommon.application.ModuleEvent;

public interface ModuleObserver extends EventListener {
    /**
     * Invoked when a Module is added to a AimxcelApplication.
     *
     * @param event
     */
    public void moduleAdded( ModuleEvent event );

    /**
     * Invoked when the active Module changed..
     *
     * @param event
     */
    public void activeModuleChanged( ModuleEvent event );

    /**
     * Invoked when a Module is removed from the AimxcelApplication.
     *
     * @param event
     */
    public void moduleRemoved( ModuleEvent event );
}
