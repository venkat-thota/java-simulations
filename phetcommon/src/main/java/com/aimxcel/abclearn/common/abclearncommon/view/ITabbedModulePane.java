

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package com.aimxcel.abclearn.common.abclearncommon.view;

import javax.swing.JComponent;

import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.ModuleEvent;
import com.aimxcel.abclearn.common.abclearncommon.application.ModuleObserver;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;

import com.aimxcel.abclearn.common.abclearncommon.view.ModulePanel;

/**
 * ITabbedModulePane
 * <p/>
 * The interface for tabbed panes that contain and switch between module views
 *
 * @author Ron LeMaster
 * @version $Revision$
 */
public interface ITabbedModulePane extends ModuleObserver {
    public void init( final AbcLearnApplication application, final Module[] modules );

    public void addTab( Module module );

    public void moduleAdded( ModuleEvent event );

    public void removeTab( Module module );

    public void activeModuleChanged( ModuleEvent event );

    public int getTabCount();

    public void moduleRemoved( ModuleEvent event );

    public ModulePanel getModulePanel( int i );

    public JComponent getComponent();

    public void setLogoVisible( boolean logoVisible );

    public boolean getLogoVisible();
}
