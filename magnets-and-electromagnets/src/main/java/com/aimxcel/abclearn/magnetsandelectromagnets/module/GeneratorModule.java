

package com.aimxcel.abclearn.magnetsandelectromagnets.module;

import java.awt.Color;
import java.awt.Dimension;
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
import com.aimxcel.abclearn.magnetsandelectromagnets.control.panel.PickupCoilPanel;
import com.aimxcel.abclearn.magnetsandelectromagnets.control.panel.TurbinePanel;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Compass;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.FieldMeter;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Lightbulb;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.PickupCoil;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Turbine;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Voltmeter;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.BFieldOutsideGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.CompassGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.FieldMeterGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.PickupCoilGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.TurbineGraphic;



public class GeneratorModule extends MagnetsAndElectromagnetsModule {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    // Rendering layers
    private static final double PICKUP_COIL_BACK_LAYER = 1;
    private static final double B_FIELD_LAYER = 2;
    private static final double TURBINE_LAYER = 3;
    private static final double COMPASS_LAYER = 4;
    private static final double PICKUP_COIL_FRONT_LAYER = 5;
    private static final double FIELD_METER_LAYER = 6;

    // Locations
    private static final Point TURBINE_LOCATION = new Point( 285, 400 );
    private static final Point PICKUP_COIL_LOCATION = new Point( 550, TURBINE_LOCATION.y );
    private static final Point COMPASS_LOCATION = new Point( 350, 175 );
    private static final Point FIELD_METER_LOCATION = new Point( 450, 460 );

    // Colors
    private static final Color APPARATUS_BACKGROUND = Color.BLACK;

    // Turbine
    private static final Dimension TURBINE_SIZE = MagnetsAndElectromagnetsConstants.BAR_MAGNET_SIZE;
    private static final double TURBINE_STRENGTH = 0.75 * MagnetsAndElectromagnetsConstants.TURBINE_STRENGTH_MAX;
    private static final double TURBINE_DIRECTION = 0.0; // radians
    private static final double TURBINE_SPEED = 0.0;

    // Pickup Coil
    private static final int PICKUP_COIL_NUMBER_OF_LOOPS = 2;
    private static final double PICKUP_COIL_LOOP_AREA = MagnetsAndElectromagnetsConstants.DEFAULT_PICKUP_LOOP_AREA;
    private static final double PICKUP_COIL_DIRECTION = 0.0; // radians
    private static final double PICKUP_COIL_TRANSITION_SMOOTHING_SCALE = 1.0;  // see PickupCoil.setTransitionSmoothingScale, 1 because magnet is never inside coil

    // Scaling
    private static final double CALIBRATION_EMF = 26000; // see PickupCoil.calibrateEmf for calibration instructions
    private static final double ELECTRON_SPEED_SCALE = 1.0;

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private Turbine _turbineModel;
    private Compass _compassModel;
    private FieldMeter _fieldMeterModel;
    private PickupCoil _pickupCoilModel;
    private Lightbulb _lightbulbModel;
    private Voltmeter _voltmeterModel;
    private PickupCoilGraphic _pickupCoilGraphic;
    private BFieldOutsideGraphic _bFieldOutsideGraphic;
    private PickupCoilPanel _pickupCoilPanel;
    private TurbinePanel _turbinePanel;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     */
    public GeneratorModule() {

        super( Components.generatorTab, MagnetsAndElectromagnetsStrings.TITLE_GENERATOR_MODULE );

        //----------------------------------------------------------------------------
        // Model
        //----------------------------------------------------------------------------

        // Module model
        BaseModel model = new BaseModel();
        this.setModel( model );

        // Turbine
        _turbineModel = new Turbine();
        _turbineModel.setSize( TURBINE_SIZE.getWidth(), TURBINE_SIZE.getHeight() );
        _turbineModel.setMaxStrength( MagnetsAndElectromagnetsConstants.TURBINE_STRENGTH_MAX );
        _turbineModel.setMinStrength( MagnetsAndElectromagnetsConstants.TURBINE_STRENGTH_MIN );
        _turbineModel.setStrength( TURBINE_STRENGTH );
        _turbineModel.setLocation( TURBINE_LOCATION );
        _turbineModel.setDirection( TURBINE_DIRECTION );
        _turbineModel.setSpeed( TURBINE_SPEED );
        model.addModelElement( _turbineModel );

        // Compass
        _compassModel = new Compass( _turbineModel, getClock() );
        _compassModel.setLocation( COMPASS_LOCATION );
        _compassModel.setBehavior( Compass.SIMPLE_BEHAVIOR );
        _compassModel.setEnabled( false );
        model.addModelElement( _compassModel );

        // Field Meter
        _fieldMeterModel = new FieldMeter( _turbineModel );
        _fieldMeterModel.setLocation( FIELD_METER_LOCATION );
        _fieldMeterModel.setEnabled( false );

        // Pickup Coil
        _pickupCoilModel = new PickupCoil( _turbineModel, CALIBRATION_EMF, getName() );
        _pickupCoilModel.setNumberOfLoops( PICKUP_COIL_NUMBER_OF_LOOPS );
        _pickupCoilModel.setLoopArea( PICKUP_COIL_LOOP_AREA );
        _pickupCoilModel.setDirection( PICKUP_COIL_DIRECTION );
        _pickupCoilModel.setLocation( PICKUP_COIL_LOCATION );
        _pickupCoilModel.setTransitionSmoothingScale( PICKUP_COIL_TRANSITION_SMOOTHING_SCALE );
        model.addModelElement( _pickupCoilModel );

        // Lightbulb
        _lightbulbModel = new Lightbulb( _pickupCoilModel );
        _lightbulbModel.setEnabled( true );
        _lightbulbModel.setOffWhenCurrentChangesDirection( true );

        // Volt Meter
        _voltmeterModel = new Voltmeter( _pickupCoilModel );
        _voltmeterModel.setJiggleEnabled( true );
        _voltmeterModel.setEnabled( false );
        model.addModelElement( _voltmeterModel );

        //----------------------------------------------------------------------------
        // View
        //----------------------------------------------------------------------------

        // Apparatus Panel
        // Use ApparatusPanel 3 to improve support for low resolution screens.  The size was sampled at runtime by using ApparatusPanel2 with TransformManager.DEBUG_OUTPUT_ENABLED=true on large screen size
        ApparatusPanel2 apparatusPanel = new ApparatusPanel3( getClock(), 766, 630 );
        apparatusPanel.setBackground( APPARATUS_BACKGROUND );
        this.setApparatusPanel( apparatusPanel );

        // Turbine
        TurbineGraphic turbineGraphic = new TurbineGraphic( apparatusPanel, _turbineModel );
        apparatusPanel.addChangeListener( turbineGraphic );
        apparatusPanel.addGraphic( turbineGraphic, TURBINE_LAYER );

        // Pickup Coil
        _pickupCoilGraphic = new PickupCoilGraphic( apparatusPanel, model, _pickupCoilModel, _lightbulbModel, _voltmeterModel );
        _pickupCoilGraphic.setDraggingEnabled( false );
        _pickupCoilGraphic.getCoilGraphic().setElectronSpeedScale( ELECTRON_SPEED_SCALE );
        apparatusPanel.addChangeListener( _pickupCoilGraphic );
        apparatusPanel.addGraphic( _pickupCoilGraphic.getForeground(), PICKUP_COIL_FRONT_LAYER );
        apparatusPanel.addGraphic( _pickupCoilGraphic.getBackground(), PICKUP_COIL_BACK_LAYER );

        // B-field outside the magnet
        _bFieldOutsideGraphic = new BFieldOutsideGraphic( apparatusPanel, _turbineModel, MagnetsAndElectromagnetsConstants.GRID_SPACING, MagnetsAndElectromagnetsConstants.GRID_SPACING );
        _bFieldOutsideGraphic.setNeedleSize( MagnetsAndElectromagnetsConstants.GRID_NEEDLE_SIZE );
        _bFieldOutsideGraphic.setGridBackground( APPARATUS_BACKGROUND );
        _bFieldOutsideGraphic.setVisible( false );
        apparatusPanel.addChangeListener( _bFieldOutsideGraphic );
        apparatusPanel.addGraphic( _bFieldOutsideGraphic, B_FIELD_LAYER );
        super.setBFieldOutsideGraphic( _bFieldOutsideGraphic );

        // CompassGraphic
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
        compassGraphic.getCollisionDetector().add( _pickupCoilGraphic );

        //----------------------------------------------------------------------------
        // Control
        //----------------------------------------------------------------------------

        // Control Panel
        {
            MagnetsAndElectromagnetsControlPanel controlPanel = new MagnetsAndElectromagnetsControlPanel();
            setControlPanel( controlPanel );

            // Turbine controls
            _turbinePanel = new TurbinePanel( _turbineModel, _compassModel, _fieldMeterModel, _bFieldOutsideGraphic );
            controlPanel.addControlFullWidth( _turbinePanel );

            // Spacer
            controlPanel.addDefaultVerticalSpace();

            // Pickup Coil controls
            _pickupCoilPanel = new PickupCoilPanel(
                    _pickupCoilModel, _pickupCoilGraphic, _lightbulbModel, _voltmeterModel );
            controlPanel.addControlFullWidth( _pickupCoilPanel );

            // Developer controls
            if ( AimxcelApplication.getInstance().isDeveloperControlsEnabled() ) {
                controlPanel.addDefaultVerticalSpace();

                DeveloperControlsPanel developerControlsPanel = new DeveloperControlsPanel( _pickupCoilModel, _pickupCoilGraphic, null, _pickupCoilGraphic.getLightbulbGraphic(), null, _bFieldOutsideGraphic );
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

        // Turbine model
        _turbineModel.setStrength( TURBINE_STRENGTH );
        _turbineModel.setLocation( TURBINE_LOCATION );
        _turbineModel.setDirection( TURBINE_DIRECTION );
        _turbineModel.setSpeed( TURBINE_SPEED );

        // Compass model
        _compassModel.setLocation( COMPASS_LOCATION );
        _compassModel.setEnabled( true );

        // Pickup Coil model
        _pickupCoilModel.setNumberOfLoops( PICKUP_COIL_NUMBER_OF_LOOPS );
        _pickupCoilModel.setLoopArea( PICKUP_COIL_LOOP_AREA );
        _pickupCoilModel.setDirection( PICKUP_COIL_DIRECTION );
        _pickupCoilModel.setLocation( PICKUP_COIL_LOCATION );

        // Lightbulb
        _lightbulbModel.setEnabled( true );

        // Volt Meter
        _voltmeterModel.setEnabled( false );

        // Pickup Coil view
        if ( MagnetsAndElectromagnetsConstants.HIDE_ELECTRONS_FEATURE ) {
            _pickupCoilGraphic.getCoilGraphic().setElectronAnimationEnabled( false );
            _pickupCoilPanel.setElectronsControlVisible( false );
        }
        else {
            _pickupCoilGraphic.getCoilGraphic().setElectronAnimationEnabled( true );
        }

        // B-field view outside the magnet
        if ( MagnetsAndElectromagnetsConstants.HIDE_BFIELD_FEATURE ) {
            _bFieldOutsideGraphic.setVisible( false );
            _turbinePanel.setBFieldControlVisible( false );
        }
        else {
            _bFieldOutsideGraphic.setVisible( false );
        }

        // Field Meter view
        _fieldMeterModel.setLocation( FIELD_METER_LOCATION );
        _fieldMeterModel.setEnabled( false );

        // Control panel
        _turbinePanel.update();
        _pickupCoilPanel.update();
    }
}
