
package edu.colorado.phet.common.piccolophet;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.ModuleEvent;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.view.ITabbedModulePane;
import com.aimxcel.abclearn.common.abclearncommon.view.ModulePanel;

/**
 * An on-screen container for the modules in an application.  It is only used for applications
 * that have more than one module.
 * <p/>
 * This class depends on AbcLearnTabbedPane and Piccolo.
 *
 * @author Sam Reid
 * @author Ron LeMaster
 */
public class TabbedModulePanePiccolo extends AbcLearnTabbedPane implements ITabbedModulePane {
    private Module current;
    private AbcLearnApplication application;

    public TabbedModulePanePiccolo() {
        this( true );
    }

    public TabbedModulePanePiccolo( boolean logoVisible ) {
        this.setLogoVisible( logoVisible );
    }


    public void init( final AbcLearnApplication application, final Module[] modules ) {
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
