

package com.aimxcel.abclearn.magnetandcompass.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel2;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelImageGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelShapeGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelTextGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.GraphicLayerSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.NonInteractiveEventListener;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassConstants;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassResources;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassSimSharing;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassStrings;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassSimSharing.Components;
import com.aimxcel.abclearn.magnetandcompass.control.MagnetAndCompassSlider;
import com.aimxcel.abclearn.magnetandcompass.model.Turbine;



public class TurbineGraphic extends GraphicLayerSet implements SimpleObserver, ApparatusPanel2.ChangeListener {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    private static final boolean TURBINE_IS_DRAGGABLE = false;

    private static final double WATER_WHEEL_LAYER = 1;
    private static final double WATER_LAYER = 2;
    private static final double FAUCET_LAYER = 3;
    private static final double SLIDER_LAYER = 4;
    private static final double BAR_MAGNET_LAYER = 5; // magnet in front of water!
    private static final double PIVOT_LAYER = 6;
    private static final double RPM_LAYER = 7;

    private static final Color RPM_COLOR = Color.GREEN;
    private static final Font RPM_VALUE_FONT = new AimxcelFont( 15 );
    private static final Font RPM_UNITS_FONT = new AimxcelFont( 12 );

    private static final double MAX_WATER_WIDTH = 40.0;
    private static final Color WATER_COLOR = new Color( 194, 234, 255, 180 );

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private Rectangle _parentBounds;
    private Turbine _turbineModel;
    private AimxcelShapeGraphic _waterGraphic;
    private Rectangle2D _waterShape;
    private AimxcelImageGraphic _barMagnetGraphic;
    private AimxcelImageGraphic _waterWheelGraphic;
    private AimxcelTextGraphic _rpmValue;
    private MagnetAndCompassSlider _flowSlider;
    private double _previousSpeed;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     *
     * @param component    the parent Component
     * @param turbineModel the turbine that this graphic represents
     */
    public TurbineGraphic( Component component, Turbine turbineModel ) {
        super( component );

        assert ( turbineModel != null );

        _turbineModel = turbineModel;
        _turbineModel.addObserver( this );

        _parentBounds = new Rectangle( 0, 0, component.getWidth(), component.getHeight() );

        // Faucet 
        {
            BufferedImage faucetImage = MagnetAndCompassResources.getImage( MagnetAndCompassConstants.FAUCET_IMAGE );
            AimxcelImageGraphic faucet = new AimxcelImageGraphic( component, faucetImage );
            addGraphic( faucet, FAUCET_LAYER );
            faucet.setLocation( -405, -350 );
            faucet.addMouseInputListener( new NonInteractiveEventListener( Components.faucet ) );
        }

        // Water
        {
            _waterGraphic = new AimxcelShapeGraphic( component );
            _waterGraphic.setIgnoreMouse( !TURBINE_IS_DRAGGABLE );
            addGraphic( _waterGraphic, WATER_LAYER );
            _waterGraphic.setPaint( WATER_COLOR );

            _waterShape = new Rectangle2D.Double( 0, 0, 0, 0 );
            _waterGraphic.setShape( _waterShape );

            _waterGraphic.setLocation( -112, -245 );
        }

        // Water Flow slider
        {
            _flowSlider = new MagnetAndCompassSlider( Components.faucetSlider, component, 70 );
            addGraphic( _flowSlider, SLIDER_LAYER );
            _flowSlider.setMinimum( 0 );
            _flowSlider.setMaximum( 100 );
            _flowSlider.setValue( 0 );
            _flowSlider.centerRegistrationPoint();
            _flowSlider.setLocation( -160, -335 );
            _flowSlider.addChangeListener( new SliderListener() );
        }

        // Water Wheel
        {
            BufferedImage waterWheelImage = MagnetAndCompassResources.getImage( MagnetAndCompassConstants.WATER_WHEEL_IMAGE );
            _waterWheelGraphic = new AimxcelImageGraphic( component, waterWheelImage );
            _waterWheelGraphic.centerRegistrationPoint();
            _waterWheelGraphic.setLocation( 0, 0 );
            _waterWheelGraphic.addMouseInputListener( new NonInteractiveEventListener( MagnetAndCompassSimSharing.Components.waterWheel ) );
            addGraphic( _waterWheelGraphic, WATER_WHEEL_LAYER );
        }

        // Bar magnet
        {
            BufferedImage barMagnetImage = MagnetAndCompassResources.getImage( MagnetAndCompassConstants.BAR_MAGNET_IMAGE );
            _barMagnetGraphic = new AimxcelImageGraphic( component, barMagnetImage );
            _barMagnetGraphic.setIgnoreMouse( !TURBINE_IS_DRAGGABLE );
            _barMagnetGraphic.centerRegistrationPoint();
            _barMagnetGraphic.setLocation( 0, 0 );
            addGraphic( _barMagnetGraphic, BAR_MAGNET_LAYER );

            // Scale the magnet image to match the model.
            final double xScale = _turbineModel.getWidth() / _barMagnetGraphic.getWidth();
            final double yScale = _turbineModel.getHeight() / _barMagnetGraphic.getHeight();
            _barMagnetGraphic.scale( xScale, yScale );
        }

        // Pivot point
        {
            BufferedImage turbinePivotImage = MagnetAndCompassResources.getImage( MagnetAndCompassConstants.TURBINE_PIVOT_IMAGE );
            AimxcelImageGraphic pivotGraphic = new AimxcelImageGraphic( component, turbinePivotImage );
            pivotGraphic.setIgnoreMouse( !TURBINE_IS_DRAGGABLE );
            addGraphic( pivotGraphic, PIVOT_LAYER );
            pivotGraphic.centerRegistrationPoint();
            pivotGraphic.setLocation( 0, 0 );
        }

        // RPM readout
        {
            String valueString = String.valueOf( (int) ( _turbineModel.getRPM() ) );
            _rpmValue = new AimxcelTextGraphic( component, RPM_VALUE_FONT, valueString, RPM_COLOR );
            addGraphic( _rpmValue, RPM_LAYER );
            _rpmValue.centerRegistrationPoint();
            _rpmValue.setLocation( 0, 10 );
            _rpmValue.setIgnoreMouse( !TURBINE_IS_DRAGGABLE );

            AimxcelTextGraphic rpmUnits = new AimxcelTextGraphic( component, RPM_UNITS_FONT, MagnetAndCompassStrings.UNITS_RPM, RPM_COLOR );
            addGraphic( rpmUnits, RPM_LAYER );
            rpmUnits.centerRegistrationPoint();
            rpmUnits.setLocation( 0, 22 );
            rpmUnits.setIgnoreMouse( !TURBINE_IS_DRAGGABLE );
        }

        _previousSpeed = 0.0;

        update();
    }

    /**
     * Call this method prior to releasing all references to an object of this type.
     */
    public void cleanup() {
        _turbineModel.removeObserver( this );
        _turbineModel = null;
    }

    //----------------------------------------------------------------------------
    // SimpleObserver implementation
    //----------------------------------------------------------------------------

    /*
    * @see edu.colorado.aimxcel.common.util.SimpleObserver#update()
    */
    public void update() {

        if ( isVisible() ) {

            // Location
            setLocation( (int) _turbineModel.getX(), (int) _turbineModel.getY() );

            double speed = _turbineModel.getSpeed();

            // If the turbine is moving...
            if ( speed != 0 ) {

                // Rotate the water wheel.
                double direction = _turbineModel.getDirection();

                _barMagnetGraphic.clearTransform();
                _barMagnetGraphic.rotate( direction );

                _waterWheelGraphic.clearTransform();
                _waterWheelGraphic.rotate( direction );
            }

            // If the speed has changed...
            if ( speed != _previousSpeed ) {

                _previousSpeed = speed;

                // Update the RPM readout.
                {
                    int rpms = (int) _turbineModel.getRPM();
                    _rpmValue.setText( String.valueOf( rpms ) );
                    _rpmValue.centerRegistrationPoint();
                }

                // Update the water flow.
                updateWater( speed );

                // Position the faucet slider.
                if ( -speed * 100 != _flowSlider.getValue() ) {
                    _flowSlider.setValue( (int) ( -speed * 100 ) );
                }
            }

            repaint();
        }
    }

    /*
    * Updates the shape used to represent the column of water.
    * @param speed
    */
    private void updateWater( final double speed ) {
        if ( speed == 0 ) {
            _waterGraphic.setShape( null );
        }
        else {
            double waterWidth = Math.abs( speed * MAX_WATER_WIDTH );
            if ( waterWidth < 1 ) {
                waterWidth = 1; // must be at least 1 pixel wide
            }
            _waterShape.setRect( -( waterWidth / 2.0 ), 0, waterWidth, _parentBounds.height );
            _waterGraphic.setShape( _waterShape );
            setBoundsDirty();
        }
    }

    //----------------------------------------------------------------------------
    // ApparatusPanel2.ChangeListener implementation
    //----------------------------------------------------------------------------

    /*
    * @see edu.colorado.aimxcel.common.view.ApparatusPanel2.ChangeListener#canvasSizeChanged(edu.colorado.aimxcel.common.view.ApparatusPanel2.ChangeEvent)
    */
    public void canvasSizeChanged( ApparatusPanel2.ChangeEvent event ) {
        _parentBounds.setBounds( 0, 0, event.getCanvasSize().width, event.getCanvasSize().height );
        updateWater( _turbineModel.getSpeed() );
    }

    //----------------------------------------------------------------------------
    // Event handling
    //----------------------------------------------------------------------------

    /**
     * SliderListener handles changes to the speed slider.
     */
    private class SliderListener implements ChangeListener {

        /**
         * Sole constructor
         */
        public SliderListener() {
            super();
        }

        /**
         * Handles amplitude slider changes.
         *
         * @param event the event
         */
        public void stateChanged( javax.swing.event.ChangeEvent event ) {
            if ( event.getSource() == _flowSlider ) {
                // Read the value.
                double speed = -( _flowSlider.getValue() / 100.0 );  // counterclockwise
                // Update the model.
                _turbineModel.setSpeed( speed );
            }
        }
    }
}