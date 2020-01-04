
 package com.aimxcel.abclearn.common.aimxcelcommon.view;

import javax.swing.JComponent;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ModuleEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ModuleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ModulePanel;


public interface ITabbedModulePane extends ModuleObserver {
    public void init( final AimxcelApplication application, final Module[] modules );

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
