

package com.aimxcel.abclearn.magnetsandelectromagnets.control.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJButton;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJCheckBox;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentChain;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.SimSharingLinearValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsConstants;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsStrings;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsSimSharing.Components;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.BarMagnet;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.Compass;
import com.aimxcel.abclearn.magnetsandelectromagnets.model.FieldMeter;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.BFieldInsideGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.BFieldOutsideGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.EarthGraphic;



public class BarMagnetPanel extends MagnetsAndElectromagnetsPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    // Model & view components to be controlled.
    private BarMagnet _barMagnetModel;
    private Compass _compassModel;
    private FieldMeter _fieldMeterModel;
    private BFieldInsideGraphic _bFieldInsideGraphic;
    private BFieldOutsideGraphic _bFieldOutsideGraphic;
    private EarthGraphic _earthGraphic;

    // UI components
    private JButton _flipPolarityButton;
    private LinearValueControl _strengthControl;
    private JCheckBox _seeInsideCheckBox;
    private JCheckBox _bFieldCheckBox;
    private JCheckBox _fieldMeterCheckBox;
    private JCheckBox _compassCheckBox;
    private JCheckBox _earthCheckBox;
    private EventListener _listener;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    public BarMagnetPanel(
            BarMagnet barMagnetModel,
            Compass compassModel,
            FieldMeter fieldMeterModel,
            BFieldInsideGraphic bFieldInsideGraphic,
            BFieldOutsideGraphic bFieldOutsideGraphic,
            EarthGraphic earthGraphic ) {
        super();

        assert ( barMagnetModel != null );
        assert ( compassModel != null );
        assert ( fieldMeterModel != null );
        assert ( bFieldOutsideGraphic != null );

        // Things we'll be controlling.
        _barMagnetModel = barMagnetModel;
        _compassModel = compassModel;
        _fieldMeterModel = fieldMeterModel;
        _bFieldInsideGraphic = bFieldInsideGraphic;
        _bFieldOutsideGraphic = bFieldOutsideGraphic;
        _earthGraphic = earthGraphic;

        // Title
        Border lineBorder = BorderFactory.createLineBorder( Color.BLACK, 2 );
        TitledBorder titleBorder = BorderFactory.createTitledBorder( lineBorder, MagnetsAndElectromagnetsStrings.TITLE_BAR_MAGNET_PANEL );
        titleBorder.setTitleFont( getTitleFont() );
        setBorder( titleBorder );

        // Magnet strength
        {
            // Values are a percentage of the maximum.
            int max = 100;
            int min = (int) ( 100.0 * MagnetsAndElectromagnetsConstants.BAR_MAGNET_STRENGTH_MIN / MagnetsAndElectromagnetsConstants.BAR_MAGNET_STRENGTH_MAX );

            // Slider
            _strengthControl = new SimSharingLinearValueControl( qualifyUserComponent( Components.strengthControl ), min, max, MagnetsAndElectromagnetsStrings.LABEL_STRENGTH, "0", "%" );
            _strengthControl.setValue( min );
            _strengthControl.setMajorTickSpacing( 50 );
            _strengthControl.setMinorTickSpacing( 10 );
            _strengthControl.setTextFieldEditable( true );
            _strengthControl.setTextFieldColumns( 3 );
            _strengthControl.setUpDownArrowDelta( 1 );
            _strengthControl.setBorder( BorderFactory.createEtchedBorder() );
        }

        //  Flip Polarity button
        _flipPolarityButton = new SimSharingJButton( qualifyUserComponent( Components.flipPolarityButton ), MagnetsAndElectromagnetsStrings.BUTTON_FLIP_POLARITY );

        // Magnet transparency on/off
        _seeInsideCheckBox = new SimSharingJCheckBox( qualifyUserComponent( Components.seeInsideMagnetCheckBox ), MagnetsAndElectromagnetsStrings.CHECK_BOX_SEE_INSIDE );

        // B-field on/off
        _bFieldCheckBox = new SimSharingJCheckBox( qualifyUserComponent( Components.showFieldCheckBox ), MagnetsAndElectromagnetsStrings.CHECK_BOX_SHOW_B_FIELD );

        // Field Meter on/off
        _fieldMeterCheckBox = new SimSharingJCheckBox( qualifyUserComponent( Components.showFieldMeterCheckBox ), MagnetsAndElectromagnetsStrings.CHECK_BOX_SHOW_FIELD_METER );

        // Compass on/off
        _compassCheckBox = new SimSharingJCheckBox( qualifyUserComponent( Components.showCompassCheckBox ), MagnetsAndElectromagnetsStrings.CHECK_BOX_SHOW_COMPASS );

        // Earth on/off
        _earthCheckBox = new SimSharingJCheckBox( UserComponentChain.chain( Components.showEarthCheckBox ), MagnetsAndElectromagnetsStrings.CHECK_BOX_SHOW_EARTH );

        // Layout
        EasyGridBagLayout layout = new EasyGridBagLayout( this );
        setLayout( layout );
        int row = 0;
        layout.addFilledComponent( _strengthControl, row++, 0, GridBagConstraints.HORIZONTAL );
        layout.addComponent( _flipPolarityButton, row++, 0 );
        layout.addComponent( _seeInsideCheckBox, row++, 0 );
        layout.addComponent( _bFieldCheckBox, row++, 0 );
        layout.addComponent( _compassCheckBox, row++, 0 );
        layout.addComponent( _fieldMeterCheckBox, row++, 0 );
        if ( earthGraphic != null ) {
            layout.addComponent( _earthCheckBox, row++, 0 );
        }

        // Wire up event handling.
        _listener = new EventListener();
        _flipPolarityButton.addActionListener( _listener );
        _strengthControl.addChangeListener( _listener );
        _bFieldCheckBox.addActionListener( _listener );
        _seeInsideCheckBox.addActionListener( _listener );
        _fieldMeterCheckBox.addActionListener( _listener );
        _compassCheckBox.addActionListener( _listener );
        _earthCheckBox.addActionListener( _listener );

        // Set the state of the controls.
        update();
    }

    private IUserComponent qualifyUserComponent( IUserComponent userComponent ) {
        return UserComponentChain.chain( Components.barMagnetControlPanel, userComponent );
    }

    /**
     * Updates the control panel to match the state of the things that it's controlling.
     */
    public void update() {
        _strengthControl.setValue( (int) ( 100.0 * _barMagnetModel.getStrength() / MagnetsAndElectromagnetsConstants.BAR_MAGNET_STRENGTH_MAX ) );
        if ( _bFieldInsideGraphic != null ) {
            _seeInsideCheckBox.setSelected( _bFieldInsideGraphic.isVisible() );
        }
        _bFieldCheckBox.setSelected( _bFieldOutsideGraphic.isVisible() );
        _fieldMeterCheckBox.setSelected( _fieldMeterModel.isEnabled() );
        _compassCheckBox.setSelected( _compassModel.isEnabled() );
        if ( _earthGraphic != null ) {
            _earthCheckBox.setSelected( _earthGraphic.isVisible() );
        }
    }

    //----------------------------------------------------------------------------
    // Feature controls
    //----------------------------------------------------------------------------

    /**
     * Access to the "Flip Polarity" control.
     *
     * @param visible true of false
     */
    public void setFlipPolarityControlVisible( boolean visible ) {
        _flipPolarityButton.setVisible( visible );
    }

    /**
     * Access to the "See Inside" control.
     *
     * @param visible true of false
     */
    public void setSeeInsideControlVisible( boolean visible ) {
        _seeInsideCheckBox.setVisible( visible );
    }

    /**
     * Access to the "Show Field Meter" control.
     *
     * @param visible true or false
     */
    public void setFieldMeterControlVisible( boolean visible ) {
        _fieldMeterCheckBox.setVisible( visible );
    }

    /**
     * Access to the "Show Earth" control.
     *
     * @param visible true or false
     */
    public void setShowEarthControlVisible( boolean visible ) {
        _earthCheckBox.setVisible( visible );
    }

    /**
     * Access to the "Show B-Field" control.
     *
     * @param visible true or false
     * @return
     */
    public void setBFieldControlVisible( boolean visible ) {
        _bFieldCheckBox.setVisible( visible );
    }

    //----------------------------------------------------------------------------
    // Event Handling
    //----------------------------------------------------------------------------

    /**
     * EventListener is a nested class that is private to this control panel.
     * It handles dispatching of all events generated by the controls.
     *
     * @author Chris Malley (cmalley@pixelzoom.com)
     * @version $Revision$
     */
    private class EventListener implements ActionListener, ChangeListener {

        /**
         * Sole constructor
         */
        public EventListener() {
        }

        //----------------------------------------------------------------------------
        // ActionListener implementation
        //----------------------------------------------------------------------------

        /**
         * ActionEvent handler.
         *
         * @param e the event
         * @throws IllegalArgumentException if the event is unexpected
         */
        public void actionPerformed( ActionEvent e ) {
            if ( e.getSource() == _flipPolarityButton ) {
                // Magnet polarity
                _barMagnetModel.flipPolarity();
                _compassModel.startMovingNow();
            }
            else if ( e.getSource() == _bFieldCheckBox ) {
                // B-field enable
                _bFieldOutsideGraphic.setVisible( _bFieldCheckBox.isSelected() );
            }
            else if ( e.getSource() == _seeInsideCheckBox ) {
                _bFieldInsideGraphic.setVisible( _seeInsideCheckBox.isSelected() );
            }
            else if ( e.getSource() == _fieldMeterCheckBox ) {
                // Meter enable
                _fieldMeterModel.setEnabled( _fieldMeterCheckBox.isSelected() );
            }
            else if ( e.getSource() == _compassCheckBox ) {
                // Compass enable
                _compassModel.setEnabled( _compassCheckBox.isSelected() );
            }
            else if ( e.getSource() == _earthCheckBox ) {
                _earthGraphic.setVisible( _earthCheckBox.isSelected() );
                if ( _earthCheckBox.isSelected() ) {
                    _barMagnetModel.setDirection( _barMagnetModel.getDirection() + Math.PI / 2 );
                }
                else {
                    _barMagnetModel.setDirection( _barMagnetModel.getDirection() - Math.PI / 2 );
                }
            }
            else {
                throw new IllegalArgumentException( "unexpected event: " + e );
            }
        }

        //----------------------------------------------------------------------------
        // ChangeListener implementation
        //----------------------------------------------------------------------------

        /**
         * ChangeEvent handler.
         *
         * @param e the event
         * @throws IllegalArgumentException if the event is unexpected
         */
        public void stateChanged( ChangeEvent e ) {
            if ( e.getSource() == _strengthControl ) {
                // Read the value.
                double percent = Math.floor( _strengthControl.getValue() );
                // Update the model.
                double strength = ( percent / 100.0 ) * MagnetsAndElectromagnetsConstants.BAR_MAGNET_STRENGTH_MAX;
                _barMagnetModel.setStrength( strength );
                /*
                 * We're displaying strength in integer precision, but the slider is in double precision.
                 * This hack ensures that the slider is always on integer values.
                 * See Unfuddle #504.
                 */
                _strengthControl.removeChangeListener( _listener );
                _strengthControl.setValue( percent );
                _strengthControl.addChangeListener( _listener );
            }
            else {
                throw new IllegalArgumentException( "unexpected event: " + e );
            }
        }
    }
}
