

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */

package com.aimxcel.abclearn.common.abclearncommon.application;

import java.util.EventListener;

import com.aimxcel.abclearn.common.abclearncommon.application.ModuleEvent;

/**
 * Observes additions and removals of Modules, change in the active Module.
 *
 * @author Ron LeMaster
 * @version $Revision$
 */
public interface ModuleObserver extends EventListener {
    /**
     * Invoked when a Module is added to a AbcLearnApplication.
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
     * Invoked when a Module is removed from the AbcLearnApplication.
     *
     * @param event
     */
    public void moduleRemoved( ModuleEvent event );
}
