package com.aimxcel.abclearn.glaciers.module.intro;

import java.awt.Frame;

import com.aimxcel.abclearn.glaciers.GlaciersApplication;
import com.aimxcel.abclearn.glaciers.GlaciersConstants;
import com.aimxcel.abclearn.glaciers.GlaciersResources;
import com.aimxcel.abclearn.glaciers.GlaciersStrings;
import com.aimxcel.abclearn.glaciers.control.ClimateControlPanel;
import com.aimxcel.abclearn.glaciers.control.MiscControlPanel;
import com.aimxcel.abclearn.glaciers.control.ViewControlPanel;
import com.aimxcel.abclearn.glaciers.model.Climate;
import com.aimxcel.abclearn.glaciers.model.Glacier;
import com.aimxcel.abclearn.glaciers.model.GlaciersClock;
import com.aimxcel.abclearn.glaciers.model.GlaciersModel;
import com.aimxcel.abclearn.glaciers.module.GlaciersModule;
import com.aimxcel.abclearn.glaciers.persistence.IntroConfig;
import com.aimxcel.abclearn.glaciers.view.GlaciersPlayArea;
public class IntroModule extends GlaciersModule {

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private final GlaciersPlayArea _playArea;
    private final IntroControlPanel _controlPanel;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    public IntroModule( Frame dialogOwner ) {
        super( GlaciersStrings.TITLE_INTRO );
        
        GlaciersModel model = getGlaciersModel();

        // Play Area
        _playArea = new GlaciersPlayArea( model );
        setSimulationPanel( _playArea );

        // Put our control panel where the clock control panel normally goes
        int minHeight = GlaciersResources.getInt( "controlPanel.minHeight", 100 );
        _controlPanel = new IntroControlPanel( model, _playArea, dialogOwner, this, GlaciersConstants.DEFAULT_TO_ENGLISH_UNITS, minHeight );
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

    public IntroConfig save() {

        IntroConfig config = new IntroConfig();

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
            
            config.setSnowfallSelected( _controlPanel.getViewControlPanel().isSnowfallSelected() );
            config.setEquilibriumLineSelected( _controlPanel.getViewControlPanel().isEquilibriumLineSelected() );
        }
        
        return config;
    }

    public void load( IntroConfig config ) {

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
            
            _controlPanel.getViewControlPanel().setSnowfallSelected( config.isSnowfallSelected() );
            _controlPanel.getViewControlPanel().setEquilibriumLineSelected( config.isEquilibriumLineSelected() );
        }
    }
}
