

package com.aimxcel.abclearn.magnetsandelectromagnets.module;

import java.awt.Color;
import java.awt.Point;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel3;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.BaseModel;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsConstants;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsStrings;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsSimSharing.Components;
import com.aimxcel.abclearn.magnetsandelectromagnets.control.MagnetsAndElectromagnetsControlPanel;
import com.aimxcel.abclearn.magnetsandelectromagnets.control.panel.DeveloperControlsPanel;
import com.aimxcel.abclearn.magnetsandelectromagnets.control.panel.ElectromagnetPanel;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.ACPowerSupply;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.AbstractCurrentSource;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Battery;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Compass;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Electromagnet;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.FieldMeter;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.SourceCoil;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.BFieldOutsideGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.CompassGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.ElectromagnetGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.FieldMeterGraphic;



public class ElectromagnetModule extends MagnetsAndElectromagnetsModule {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    // Rendering layers
    private static final double ELECTROMAGNET_BACK_LAYER = 1;
    private static final double B_FIELD_LAYER = 2;
    private static final double COMPASS_LAYER = 3;
    private static final double ELECTROMAGNET_FRONT_LAYER = 4;
    private static final double FIELD_METER_LAYER = 5;

    // Locations
    private static final Point ELECTROMAGNET_LOCATION = new Point( 400, 400 );
    private static final Point COMPASS_LOCATION = new Point( 150, 200 );
    private static final Point FIELD_METER_LOCATION = new Point( 150, 400 );

    // Colors
    private static final Color APPARATUS_BACKGROUND = new Color(0, 51, 51);

    // Battery
    private static final double BATTERY_AMPLITUDE = 1.0;

    // AC Power Supply
    private static final double AC_MAX_AMPLITUDE = 0.5;
    private static final double AC_FREQUENCY = 0.5;

    // Source Coil
    private static final int ELECTROMAGNET_NUMBER_OF_LOOPS = MagnetsAndElectromagnetsConstants.ELECTROMAGNET_LOOPS_MAX;
    private static final double ELECTROMAGNET_LOOP_RADIUS = 50.0;  // Fixed loop radius
    private static final double ELECTROMAGNET_DIRECTION = 0.0; // radians

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private Battery _batteryModel;
    private ACPowerSupply _acPowerSupplyModel;
    private SourceCoil _sourceCoilModel;
    private Electromagnet _electromagnetModel;
    private Compass _compassModel;
    private FieldMeter _fieldMeterModel;
    private ElectromagnetGraphic _electromagnetGraphic;
    private BFieldOutsideGraphic _bFieldOutsideGraphic;
    private ElectromagnetPanel _electromagnetPanel;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     */
    public ElectromagnetModule() {

        super( Components.electromagnetTab, MagnetsAndElectromagnetsStrings.TITLE_ELECTROMAGNET_MODULE );

        //----------------------------------------------------------------------------
        // Model
        //----------------------------------------------------------------------------

        // Module model
        BaseModel model = new BaseModel();
        this.setModel( model );

        // Battery
        _batteryModel = new Battery();
        _batteryModel.setMaxVoltage( MagnetsAndElectromagnetsConstants.BATTERY_VOLTAGE_MAX );
        _batteryModel.setAmplitude( BATTERY_AMPLITUDE );
        _batteryModel.setEnabled( true );

        // AC Power Supply
        _acPowerSupplyModel = new ACPowerSupply();
        _acPowerSupplyModel.setMaxVoltage( MagnetsAndElectromagnetsConstants.AC_VOLTAGE_MAX );
        _acPowerSupplyModel.setMaxAmplitude( AC_MAX_AMPLITUDE );
        _acPowerSupplyModel.setFrequency( AC_FREQUENCY );
        _acPowerSupplyModel.setEnabled( false );
        model.addModelElement( _acPowerSupplyModel );

        // Source Coil
        _sourceCoilModel = new SourceCoil();
        _sourceCoilModel.setNumberOfLoops( ELECTROMAGNET_NUMBER_OF_LOOPS );
        _sourceCoilModel.setRadius( ELECTROMAGNET_LOOP_RADIUS );
        _sourceCoilModel.setDirection( ELECTROMAGNET_DIRECTION );

        // Electromagnet
        AbstractCurrentSource currentSource = null;
        if ( _batteryModel.isEnabled() ) {
            currentSource = _batteryModel;
        }
        else if ( _acPowerSupplyModel.isEnabled() ) {
            currentSource = _acPowerSupplyModel;
        }
        _electromagnetModel = new Electromagnet( _sourceCoilModel, currentSource );
        _electromagnetModel.setMaxStrength( MagnetsAndElectromagnetsConstants.ELECTROMAGNET_STRENGTH_MAX );
        _electromagnetModel.setLocation( ELECTROMAGNET_LOCATION );
        _electromagnetModel.setDirection( ELECTROMAGNET_DIRECTION );
        // Do NOT set the strength! -- strength will be set based on the source coil model.
        // Do NOT set the size! -- size will be based on the source coil model.
        _electromagnetModel.update();

        // Compass model
        _compassModel = new Compass( _electromagnetModel, getClock() );
        _compassModel.setBehavior( Compass.INCREMENTAL_BEHAVIOR );
        _compassModel.setLocation( COMPASS_LOCATION );
        model.addModelElement( _compassModel );

        // Field Meter
        _fieldMeterModel = new FieldMeter( _electromagnetModel );
        _fieldMeterModel.setLocation( FIELD_METER_LOCATION );
        _fieldMeterModel.setEnabled( false );

        //----------------------------------------------------------------------------
        // View
        //----------------------------------------------------------------------------

        // Apparatus Panel
        // Use ApparatusPanel 3 to improve support for low resolution screens.  The size was sampled at runtime by using ApparatusPanel2 with TransformManager.DEBUG_OUTPUT_ENABLED=true on large screen size
        ApparatusPanel2 apparatusPanel = new ApparatusPanel3( getClock(), 783, 630 );
        apparatusPanel.setBackground( APPARATUS_BACKGROUND );
        this.setApparatusPanel( apparatusPanel );

        // Electromagnet
        _electromagnetGraphic = new ElectromagnetGraphic( apparatusPanel, model,
                                                          _electromagnetModel, _sourceCoilModel, _batteryModel, _acPowerSupplyModel );
        apparatusPanel.addChangeListener( _electromagnetGraphic );
        apparatusPanel.addGraphic( _electromagnetGraphic.getForeground(), ELECTROMAGNET_FRONT_LAYER );
        apparatusPanel.addGraphic( _electromagnetGraphic.getBackground(), ELECTROMAGNET_BACK_LAYER );

        // B-field outside the magnet
        _bFieldOutsideGraphic = new BFieldOutsideGraphic( apparatusPanel, _electromagnetModel, MagnetsAndElectromagnetsConstants.GRID_SPACING, MagnetsAndElectromagnetsConstants.GRID_SPACING );
        _bFieldOutsideGraphic.setNeedleSize( MagnetsAndElectromagnetsConstants.GRID_NEEDLE_SIZE );
        _bFieldOutsideGraphic.setGridBackground( APPARATUS_BACKGROUND );
        apparatusPanel.addChangeListener( _bFieldOutsideGraphic );
        apparatusPanel.addGraphic( _bFieldOutsideGraphic, B_FIELD_LAYER );
        super.setBFieldOutsideGraphic( _bFieldOutsideGraphic );

        // Compass
        CompassGraphic compassGraphic = new CompassGraphic( apparatusPanel, _compassModel );
        compassGraphic.setLocation( COMPASS_LOCATION );
        apparatusPanel.addChangeListener( compassGraphic );
        apparatusPanel.addGraphic( compassGraphic, COMPASS_LAYER );

        // Field Meter
        FieldMeterGraphic fieldMeterGraphic = new FieldMeterGraphic( apparatusPanel, _fieldMeterModel );
        fieldMeterGraphic.setLocation( FIELD_METER_LOCATION );
        apparatusPanel.addChangeListener( fieldMeterGraphic );
        apparatusPanel.addGraphic( fieldMeterGraphic, FIELD_METER_LAYER );

        // Collision detection
        _electromagnetGraphic.getCollisionDetector().add( compassGraphic );
        compassGraphic.getCollisionDetector().add( _electromagnetGraphic );

        //----------------------------------------------------------------------------
        // Control
        //----------------------------------------------------------------------------

        // Control Panel
        {
            MagnetsAndElectromagnetsControlPanel controlPanel = new MagnetsAndElectromagnetsControlPanel();
            setControlPanel( controlPanel );

            // Electromagnet controls
            _electromagnetPanel = new ElectromagnetPanel( _electromagnetModel,
                                                          _sourceCoilModel, _batteryModel, _acPowerSupplyModel, _compassModel, _fieldMeterModel,
                                                          _electromagnetGraphic, _bFieldOutsideGraphic );
            controlPanel.addControlFullWidth( _electromagnetPanel );

            // Developer controls
            if ( AimxcelApplication.getInstance().isDeveloperControlsEnabled() ) {
                controlPanel.addDefaultVerticalSpace();

                DeveloperControlsPanel developerControlsPanel = new DeveloperControlsPanel( null, null, _electromagnetGraphic, null, null, _bFieldOutsideGraphic );
                controlPanel.addControlFullWidth( developerControlsPanel );
            }

            // Reset button
            controlPanel.addResetAllButton( this );
        }

        reset();
    }

    //----------------------------------------------------------------------------
    // Superclass overrides
    //----------------------------------------------------------------------------

    /**
     * Resets everything to the initial state.
     */
    public void reset() {

        // Battery model
        _batteryModel.setAmplitude( BATTERY_AMPLITUDE );
        _batteryModel.setEnabled( true );

        // AC Power Supply model
        _acPowerSupplyModel.setMaxAmplitude( AC_MAX_AMPLITUDE );
        _acPowerSupplyModel.setFrequency( AC_FREQUENCY );
        _acPowerSupplyModel.setEnabled( false );

        // Source Coil model
        _sourceCoilModel.setNumberOfLoops( ELECTROMAGNET_NUMBER_OF_LOOPS );
        _sourceCoilModel.setRadius( ELECTROMAGNET_LOOP_RADIUS );
        _sourceCoilModel.setDirection( ELECTROMAGNET_DIRECTION );

        // Electromagnet model
        _electromagnetModel.setLocation( ELECTROMAGNET_LOCATION );
        _electromagnetModel.setDirection( ELECTROMAGNET_DIRECTION );
        if ( _batteryModel.isEnabled() ) {
            _electromagnetModel.setCurrentSource( _batteryModel );
        }
        else {
            _electromagnetModel.setCurrentSource( _acPowerSupplyModel );
        }
        // Do NOT set the strength! -- strength will be set based on the source coil model.
        // Do NOT set the size! -- size will be based on the source coil appearance.
        _electromagnetModel.update();

        // Compass model
        _compassModel.setLocation( COMPASS_LOCATION );
        _compassModel.setEnabled( true );

        // Electromagnet view
        if ( MagnetsAndElectromagnetsConstants.HIDE_ELECTRONS_FEATURE ) {
            _electromagnetGraphic.getCoilGraphic().setElectronAnimationEnabled( false );
            _electromagnetPanel.setElectronsControlVisible( false );
        }
        else {
            _electromagnetGraphic.getCoilGraphic().setElectronAnimationEnabled( true );
        }

        // B-field view outside the magnet
        if ( MagnetsAndElectromagnetsConstants.HIDE_BFIELD_FEATURE ) {
            _bFieldOutsideGraphic.setVisible( false );
            _electromagnetPanel.setBFieldControlVisible( false );
        }
        else {
            _bFieldOutsideGraphic.setVisible( true );
        }

        // Field Meter view
        _fieldMeterModel.setLocation( FIELD_METER_LOCATION );
        _fieldMeterModel.setEnabled( false );

        // Control panel
        _electromagnetPanel.update();
    }
}