
package com.aimxcel.abclearn.core.aimxcelcore;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ModuleEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ITabbedModulePane;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ModulePanel;


public class TabbedModulePaneCore extends AimxcelTabbedPane implements ITabbedModulePane {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Module current;
    private AimxcelApplication application;

    public TabbedModulePaneCore() {
        this( true );
    }

    public TabbedModulePaneCore( boolean logoVisible ) {
        this.setLogoVisible( logoVisible );
    }


    public void init( final AimxcelApplication application, final Module[] modules ) {
        this.application = application;
        addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                int selectedIdx = getSelectedIndex();
                if ( selectedIdx >= 0 && application.numModules() > 0 ) {
                    current = application.moduleAt( selectedIdx );
                    application.setActiveModule( selectedIdx );
                }
            }
        } );
        application.addModuleObserver( this );
        for ( int i = 0; i < modules.length; i++ ) {
            Module module = modules[i];
            addTab( module );
        }
        setOpaque( true );
    }

    public void addTab( Module module ) {
        addTab( module.getTabUserComponent(), module.getName(), module.getModulePanel() );
    }

    public void moduleAdded( ModuleEvent event ) {
    }

    public void removeTab( Module module ) {
        for ( int i = 0; i < getTabCount(); i++ ) {
            if ( getTitleAt( i ).equals( module.getName() ) && getComponent( i ).equals( module.getModulePanel() ) ) {
                removeTabAt( i );
                break;
            }
        }
    }

    public void activeModuleChanged( ModuleEvent event ) {
        if ( current != event.getModule() ) {
            int index = application.indexOf( event.getModule() );
            int numTabs = getTabCount();
            if ( index < numTabs ) {
                setSelectedIndex( index );
            }
            else {
                throw new RuntimeException( "Requested illegal tab: tab count=" + numTabs + ", requestedIndex=" + index );
            }
        }
    }

    public void moduleRemoved( ModuleEvent event ) {
    }

    public ModulePanel getModulePanel( int i ) {
        return (ModulePanel) getComponent( i );
    }

    public JComponent getComponent() {
        return this;
    }
}
