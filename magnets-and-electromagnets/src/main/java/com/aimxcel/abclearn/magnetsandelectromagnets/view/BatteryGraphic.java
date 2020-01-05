

package com.aimxcel.abclearn.magnetsandelectromagnets.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelImageGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.AimxcelTextGraphic;
import com.aimxcel.abclearn.aimxcelgraphics.view.phetgraphics.GraphicLayerSet;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsConstants;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsResources;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsStrings;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsSimSharing.Components;
import com.aimxcel.abclearn.magnetsandelectromagnets.control.MagnetsAndElectromagnetsSlider;
import com.aimxcel.abclearn.magnetsandelectromagnets.control.GraphicSlider;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Battery;


/**
 * BatteryGraphic is the graphical representation of a battery.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class BatteryGraphic extends GraphicLayerSet implements SimpleObserver {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    private static final double BATTERY_LAYER = 1;
    private static final double SLIDER_LAYER = 2;
    private static final double VALUE_LAYER = 3;

    private static final Font VALUE_FONT = new AimxcelFont( 15 );
    private static final Color VALUE_COLOR = Color.BLACK;
    private static final Point VALUE_POSITIVE_LOCATION = new Point( 158, 83 );
    private static final Point VALUE_NEGATIVE_LOCATION = new Point( 55, 83 );

    private static final DecimalFormat VOLTAGE_FORMAT = new DecimalFormat( "0" );

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private Battery _batteryModel;
    private AimxcelImageGraphic _batteryGraphic;
    private GraphicSlider _amplitudeSlider;
    private AimxcelTextGraphic _amplitudeValue;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     *
     * @param component    the parent component
     * @param batteryModel the battery that this graphic represents
     */
    public BatteryGraphic( Component component, Battery batteryModel ) {
        super( component );

        _batteryModel = batteryModel;
        _batteryModel.addObserver( this );

        // Battery image
        {
            BufferedImage batteryImage = MagnetsAndElectromagnetsResources.getImage( MagnetsAndElectromagnetsConstants.BATTERY_IMAGE );
            _batteryGraphic = new AimxcelImageGraphic( component, batteryImage );
            addGraphic( _batteryGraphic, BATTERY_LAYER );

            // Registration point at top center.
            int rx = _batteryGraphic.getWidth() / 2;
            int ry = 0;
            _batteryGraphic.setRegistrationPoint( rx, ry );

            _batteryGraphic.setLocation( rx, 0 );
        }

        // Amplitude slider
        {
            _amplitudeSlider = new MagnetsAndElectromagnetsSlider( Components.batterySlider, component, 100 /* track length */ );
            addGraphic( _amplitudeSlider, SLIDER_LAYER );

            _amplitudeSlider.setMinimum( (int) -( _batteryModel.getMaxVoltage() ) );
            _amplitudeSlider.setMaximum( (int) ( _batteryModel.getMaxVoltage() ) );
            _amplitudeSlider.setValue( (int) ( _batteryModel.getAmplitude() * _batteryModel.getMaxVoltage() ) );
            _amplitudeSlider.addTick( _amplitudeSlider.getMinimum() );
            _amplitudeSlider.addTick( _amplitudeSlider.getMaximum() );
            _amplitudeSlider.addTick( 0 );

            _amplitudeSlider.centerRegistrationPoint();
            _amplitudeSlider.setLocation( 105, 35 );
            _amplitudeSlider.addChangeListener( new SliderListener() );
        }

        // Amplitude value
        {
            _amplitudeValue = new AimxcelTextGraphic( component, VALUE_FONT, "", VALUE_COLOR );
            addGraphic( _amplitudeValue, VALUE_LAYER );
        }

        // Registration point is the bottom center.
        int rx = getWidth() / 2;
        int ry = getHeight();
        setRegistrationPoint( rx, ry );

        update();
    }

    /**
     * Call this method prior to releasing all references to an object of this type.
     */
    public void cleanup() {
        _batteryModel.removeObserver( this );
    }

    //----------------------------------------------------------------------------
    // SimpleObserver implementation
    //----------------------------------------------------------------------------

    /**
     * Updates the view to match the model.
     */
    public void update() {

        setVisible( _batteryModel.isEnabled() );
        if ( isVisible() ) {

            double voltage = _batteryModel.getVoltage();

            // Update the battery orientation.
            {
                _batteryGraphic.clearTransform();
                if ( voltage < 0 ) {
                    _batteryGraphic.scale( -1, 1 ); // horizontal reflection to indicate voltage polarity
                }
            }

            // Update the slider position.
            int value = (int) ( _batteryModel.getAmplitude() * _batteryModel.getMaxVoltage() );
            if ( value != _amplitudeSlider.getValue() ) {
                _amplitudeSlider.setValue( value );
            }

            // Update the displayed value.
            {
                // Format the text
                String text = VOLTAGE_FORMAT.format( Math.abs( voltage ) ) + " " + MagnetsAndElectromagnetsStrings.UNITS_VOLTS;
                _amplitudeValue.setText( text );

                // Move the voltage label to the positive end of the battery
                if ( voltage < 0 ) {
                    _amplitudeValue.setLocation( VALUE_NEGATIVE_LOCATION );
                }
                else {
                    _amplitudeValue.setLocation( VALUE_POSITIVE_LOCATION );
                }

                // Right justify
                int rx = _amplitudeValue.getBounds().width;
                int ry = _amplitudeValue.getBounds().height;
                _amplitudeValue.setRegistrationPoint( rx, ry );
            }

            repaint();
        }
    }

    //----------------------------------------------------------------------------
    // Event handling
    //----------------------------------------------------------------------------

    /**
     * SliderListener handles changes to the amplitude slider.
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
        public void stateChanged( ChangeEvent event ) {
            if ( event.getSource() == _amplitudeSlider ) {
                // Read the value.
                int voltage = _amplitudeSlider.getValue();
                // Update the model.
                _batteryModel.setAmplitude( voltage / _batteryModel.getMaxVoltage() );
            }
        }
    }
}
