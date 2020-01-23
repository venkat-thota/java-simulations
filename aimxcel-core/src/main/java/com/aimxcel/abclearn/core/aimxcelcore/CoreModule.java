// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.core.aimxcelcore;

import java.awt.Component;

import javax.swing.JComponent;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.core.aimxcelcore.help.HelpPane;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.CoreClockControlPanel;



/**
 * PiccoloModule is a module specialized for use with Piccolo.
 *
 * @author Sam Reid
 */
public class CoreModule extends Module {

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private Component helpPane;
    private Component restoreGlassPane;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Constructor.
     * The module's clock is running by default.
     *
     * @param name
     * @param clock
     */
    public CoreModule( String name, IClock clock ) {
        this( name, clock, false /* startsPaused */ );
    }

    /**
     * Constructor.
     *
     * @param name
     * @param clock
     * @param startsPaused
     */
    public CoreModule( String name, IClock clock, boolean startsPaused ) {
        super( name, clock, startsPaused );
        if ( hasHelp() ) {
            helpPane = new HelpPane( AimxcelApplication.getInstance().getAimxcelFrame() );
        }
    }

    //----------------------------------------------------------------------------
    // Accessors
    //----------------------------------------------------------------------------

    /**
     * Gets the AimxcelPCanvas (aka, "play area").
     * If the simulation panel is not a AimxcelPCanvas, null is returned.
     *
     * @return AimxcelPCanvas
     */
    public AimxcelPCanvas getAimxcelPCanvas() {
        AimxcelPCanvas AimxcelPCanvas = null;
        JComponent simulationPanel = getSimulationPanel();
        if ( simulationPanel instanceof AimxcelPCanvas ) {
            AimxcelPCanvas = (AimxcelPCanvas) simulationPanel;
        }
        return AimxcelPCanvas;
    }

    /**
     * Sets the simulation panel (aka, "play area") to a AimxcelPCanvas.
     *
     * @param AimxcelPCanvas
     * @deprecated use setSimulationPanel
     */
    public void setAimxcelPCanvas( AimxcelPCanvas AimxcelPCanvas ) {
        super.setSimulationPanel( AimxcelPCanvas );
    }

    /* vestige of Aimxcelgraphics, called by Module.handleClockTick */
    protected void handleUserInput() {
    }

    //----------------------------------------------------------------------------
    // Help support
    //----------------------------------------------------------------------------

    /**
     * Sets the help pane, which will be used as the glass pane.
     * You can use any subclass of Component, but the default
     * is a HelpPane.
     *
     * @param helpPane
     */
    public void setHelpPane( Component helpPane ) {
        this.helpPane = helpPane;
        if ( isActive() ) {
            setGlassPane( helpPane );
            helpPane.setVisible( isHelpEnabled() );
        }
    }

    /**
     * Gets the help pane.
     *
     * @return the help pane
     */
    public Component getHelpPane() {
        return helpPane;
    }

    /**
     * Gets the default help pane.
     * The default help pane is a HelpPane.
     * If you have replaced this help pane with your own type
     * of pane that is not derived from HelpPane, then this
     * method will return null.
     *
     * @return the default help pane
     */
    public HelpPane getDefaultHelpPane() {
        if ( helpPane != null && helpPane instanceof HelpPane ) {
            return (HelpPane) helpPane;
        }
        else {
            return null;
        }
    }

    /**
     * Enables (makes visible) help for this module.
     *
     * @param enabled
     */
    public void setHelpEnabled( boolean enabled ) {
        super.setHelpEnabled( enabled );
        if ( helpPane != null ) {
            helpPane.setVisible( enabled );
        }
    }

    /**
     * Activates the module.
     * This does all of the stuff that the superclass does,
     * plus it sets the help pane to be the glass pane.
     */
    public void activate() {
        super.activate();
        if ( helpPane != null ) {
            setGlassPane( helpPane );
            helpPane.setVisible( isHelpEnabled() );
        }
    }

    /**
     * Deactivates the module.
     * This does all of the stuff that the superclass does,
     * plus it restores the glass pane.
     */
    public void deactivate() {
        if ( helpPane != null ) {
            setGlassPane( restoreGlassPane );
            helpPane.setVisible( false );
        }
        super.deactivate();
    }

    /*
     * Gets the glass pane.
     * @return Component
     */
    private Component getGlassPane() {
        return AimxcelApplication.getInstance().getAimxcelFrame().getGlassPane();
    }

    /*
     * Sets the glass pane.
     * @param component
     */
    private void setGlassPane( Component component ) {
        if ( component != null ) {
            restoreGlassPane = getGlassPane();
            AimxcelApplication.getInstance().getAimxcelFrame().setGlassPane( component );
        }
    }

    protected JComponent createClockControlPanel( IClock clock ) {
        return new CoreClockControlPanel( clock );
    }
}