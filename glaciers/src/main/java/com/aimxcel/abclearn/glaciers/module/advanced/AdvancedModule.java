// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.glaciers.module.advanced;

import java.awt.Frame;

import com.aimxcel.abclearn.glaciers.GlaciersApplication;
import com.aimxcel.abclearn.glaciers.GlaciersConstants;
import com.aimxcel.abclearn.glaciers.GlaciersResources;
import com.aimxcel.abclearn.glaciers.GlaciersStrings;
import com.aimxcel.abclearn.glaciers.control.ClimateControlPanel;
import com.aimxcel.abclearn.glaciers.control.GraphsControlPanel;
import com.aimxcel.abclearn.glaciers.control.MiscControlPanel;
import com.aimxcel.abclearn.glaciers.control.ViewControlPanel;
import com.aimxcel.abclearn.glaciers.model.Climate;
import com.aimxcel.abclearn.glaciers.model.Glacier;
import com.aimxcel.abclearn.glaciers.model.GlaciersClock;
import com.aimxcel.abclearn.glaciers.model.GlaciersModel;
import com.aimxcel.abclearn.glaciers.module.GlaciersModule;
import com.aimxcel.abclearn.glaciers.persistence.AdvancedConfig;
import com.aimxcel.abclearn.glaciers.view.GlaciersPlayArea;

/**
 * This is the "Advanced" module.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class AdvancedModule extends GlaciersModule {

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private final GlaciersPlayArea _playArea;
    private final AdvancedControlPanel _controlPanel;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    public AdvancedModule( Frame dialogOwner ) {
        super( GlaciersStrings.TITLE_ADVANCED );
        
        GlaciersModel model = getGlaciersModel();
        
        // Play Area
        _playArea = new GlaciersPlayArea( model );
        setSimulationPanel( _playArea );

        // Put our control panel where the clock control panel normally goes
        int minHeight = GlaciersResources.getInt( "controlPanel.minHeight", 100 );
        _controlPanel = new AdvancedControlPanel( model, _playArea, dialogOwner, this, GlaciersConstants.DEFAULT_TO_ENGLISH_UNITS, minHeight );
        setClockControlPanel( _controlPanel );
        
        // Set initial state
        reset();
    }

    //----------------------------------------------------------------------------
    // Module overrides
    //----------------------------------------------------------------------------

    public void activate() {
        super.activate();
        _controlPanel.activate();
    }
    
    public void deactivate() {
        _controlPanel.deactivate();
        super.deactivate();
    }
    
    /**
     * Enables or disables help.
     * 
     * @param enabled true or false
     */
    public void setHelpEnabled( boolean enabled ) {
        super.setHelpEnabled( enabled );
        _controlPanel.setHelpEnabled( enabled );
        GlaciersApplication.getInstance().getAimxcelFrame().getHelpMenu().setHelpSelected( enabled );
    }
    
    /**
     * Resets everything in the module.
     */
    public void reset() {
        
        super.reset();
        
        // Model ---------------------------------------------
        
        GlaciersModel model = getGlaciersModel();
        model.reset();
        setClockRunningWhenActive( GlaciersConstants.CLOCK_RUNNING );

        // Controls ---------------------------------------------

        Glacier glacier = model.getGlacier();
        Climate climate = model.getClimate();
        
        ViewControlPanel viewControlPanel = _controlPanel.getViewControlPanel();
        viewControlPanel.setEnglishUnitsSelected( GlaciersConstants.DEFAULT_TO_ENGLISH_UNITS );
        viewControlPanel.setEquilibriumLineSelected( false );
        viewControlPanel.setIceFlowSelected( false );
        viewControlPanel.setSnowfallSelected( true );
        viewControlPanel.setCoordinatesSelected( false );
        
        ClimateControlPanel climateControlPanel = _controlPanel.getClimateControlPanel();
        climateControlPanel.setSnowfall( climate.getSnowfall() );
        climateControlPanel.setTemperature( climate.getTemperature() );
        
        GraphsControlPanel graphsControlPanel = _controlPanel.getGraphsControlPanel();
        graphsControlPanel.setGlacierLengthVerusTimeSelected( false );
        graphsControlPanel.setELAVersusTimeSelected( false );
        graphsControlPanel.setGlacialBudgetVersusElevationSelected( false );
        graphsControlPanel.setTemperatureVersusElevationSelected( false );
        
        MiscControlPanel miscControlPanel = _controlPanel.getMiscControlPanel();
        miscControlPanel.setSteadyStateButtonEnabled( !glacier.isSteadyState() );

        // View ---------------------------------------------
        _playArea.resetZoomedViewport();
        _playArea.setEquilibriumLineVisible( viewControlPanel.isEquilibriumLineSelected() );
        _playArea.setIceFlowVisible( viewControlPanel.isIceFlowSelected() );
        _playArea.setAxesVisible( viewControlPanel.isCoordinatesSelected() );
    }
    
    //----------------------------------------------------------------------------
    // Persistence
    //----------------------------------------------------------------------------

    public AdvancedConfig save() {

        AdvancedConfig config = new AdvancedConfig();

        // Module
        config.setActive( isActive() );

        // Model
        {
            GlaciersModel model = getGlaciersModel();
            
            // Clock
            GlaciersClock clock = model.getClock();
            config.setClockFrameRate( clock.getFrameRate() );
            config.setClockRunning( getClockRunningWhenActive() );
            
            config.setSnowfall( model.getClimate().getSnowfall() );
            config.setTemperature( model.getClimate().getTemperature() );
        }

        // Control panel settings that are view-related
        {
            config.setZoomedViewportPosition( _playArea.getZoomedViewportPosition() );
            
            ViewControlPanel viewControlPanel = _controlPanel.getViewControlPanel();
            config.setSnowfallSelected( viewControlPanel.isSnowfallSelected() );
            config.setEquilibriumLineSelected( viewControlPanel.isEquilibriumLineSelected() );
            config.setIceFlowVectorsSelected( viewControlPanel.isIceFlowSelected() );
            config.setIceFlowVectorsSelected( viewControlPanel.isCoordinatesSelected() );
            
            GraphsControlPanel graphsControlPanel = _controlPanel.getGraphsControlPanel();
            config.setGlacierLengthVersusTimeChartSelected( graphsControlPanel.isGlacierLengthVerusTimeSelected() );
            config.setELAVersusTimeChartSelected( graphsControlPanel.isELAVersusTimeSelected() );
            config.setGlacialBudgetVersusElevationChartSelected( graphsControlPanel.isGlacialBudgetVersusElevationSelected() );
            config.setTemperatureVerusElevationChartSelected( graphsControlPanel.isTemperatureVersusElevationSelected() );
        }
        
        return config;
    }

    public void load( AdvancedConfig config ) {

        // Module
        if ( config.isActive() ) {
            GlaciersApplication.getInstance().setActiveModule( this );
        }

        // Model
        {
            GlaciersModel model = getGlaciersModel();
            
            // Clock
            GlaciersClock clock = model.getClock();
            clock.setFrameRate( config.getClockFrameRate() );
            setClockRunningWhenActive( config.isClockRunning() );
            
            model.getClimate().setSnowfall( config.getSnowfall() );
            model.getClimate().setTemperature( config.getTemperature() );
            model.getGlacier().setSteadyState();
        }

        // Control panel settings that are view-related
        {
            _playArea.setZoomedViewportPosition( config.getZoomedViewportPosition() );
            
            ViewControlPanel viewControlPanel = _controlPanel.getViewControlPanel();
            viewControlPanel.setSnowfallSelected( config.isSnowfallSelected() );
            viewControlPanel.setEquilibriumLineSelected( config.isEquilibriumLineSelected() );
            viewControlPanel.setIceFlowSelected( config.isIceFlowVectorsSelected() );
            viewControlPanel.setCoordinatesSelected( config.isCoordinatesSelected() );
            
            GraphsControlPanel graphsControlPanel = _controlPanel.getGraphsControlPanel();
            graphsControlPanel.setGlacierLengthVerusTimeSelected( config.isGlacierLengthVersusTimeChartSelected() );
            graphsControlPanel.setELAVersusTimeSelected( config.isELAVersusTimeChartSelected() );
            graphsControlPanel.setGlacialBudgetVersusElevationSelected( config.isGlacialBudgetVersusElevationChartSelected() );
            graphsControlPanel.setTemperatureVersusElevationSelected( config.isTemperatureVerusElevationChartSelected() );
        }
    }

}
