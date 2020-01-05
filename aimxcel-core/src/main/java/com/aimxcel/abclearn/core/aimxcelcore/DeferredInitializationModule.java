
package com.aimxcel.abclearn.core.aimxcelcore;

import javax.swing.JPanel;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.view.ControlPanel;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.CoreClockControlPanel;


public abstract class DeferredInitializationModule extends CoreModule {
    private boolean initialized;

    /**
     * Constructor
     *
     * @param name
     * @param clock
     */
    public DeferredInitializationModule( String name, IClock clock ) {
        this( name, clock, false );
    }

    /**
     * Constructor
     *
     * @param name
     * @param clock
     * @param startsPaused
     */
    public DeferredInitializationModule( String name, IClock clock, boolean startsPaused ) {
        super( name, clock, startsPaused );

        // Provide dummy panels for the ModuleManager to reference until we are
        // properly initialized
        setSimulationPanel( new JPanel() );
        setControlPanel( new ControlPanel() );
        setClockControlPanel( new CoreClockControlPanel( clock ) );
    }

    /**
     * Extends superclass behavior by calling init() the first time this method is
     * invoked.
     */
    public void activate() {
        if ( !initialized ) {
            init();
            initialized = true;
        }
        super.activate();
    }

    /**
     * Abstract init() method.
     * <p/>
     * This method is called the first time activate() is called on the module.
     * <p/>
     * Implementations of this in concrete subclasses are where any
     * defered initialization should be done.
     */
    protected abstract void init();

}



