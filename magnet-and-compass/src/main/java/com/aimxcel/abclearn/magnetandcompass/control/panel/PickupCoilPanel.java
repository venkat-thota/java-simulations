

package com.aimxcel.abclearn.magnetandcompass.control.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJCheckBox;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJRadioButton;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJSpinner;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentChain;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.SimSharingLinearValueControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassConstants;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassResources;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassStrings;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassSimSharing.Components;
import com.aimxcel.abclearn.magnetandcompass.model.Lightbulb;
import com.aimxcel.abclearn.magnetandcompass.model.PickupCoil;
import com.aimxcel.abclearn.magnetandcompass.model.Voltmeter;
import com.aimxcel.abclearn.magnetandcompass.view.CoilGraphic;
import com.aimxcel.abclearn.magnetandcompass.view.PickupCoilGraphic;



public class PickupCoilPanel extends MagnetAndCompassPanel {

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Model & view components to be controlled.
    private PickupCoil _pickupCoilModel;
    private CoilGraphic _coilGraphic;
    private Lightbulb _lightbulbModel;
    private Voltmeter _voltmeterModel;

    // UI components
    private JSpinner _loopsSpinner;
    private LinearValueControl _areaControl;
    private JRadioButton _voltmeterRadioButton;
    private JRadioButton _lightbulbRadioButton;
    private JCheckBox _electronsCheckBox;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     *
     * @param pickupCoilModel
     * @param pickupCoilGraphic
     * @param lightbulbModel
     * @param voltmeterModel
     */
    public PickupCoilPanel(
            PickupCoil pickupCoilModel,
            PickupCoilGraphic pickupCoilGraphic,
            Lightbulb lightbulbModel,
            Voltmeter voltmeterModel ) {

        super();

        assert ( pickupCoilModel != null );
        assert ( pickupCoilGraphic != null );
        assert ( lightbulbModel != null );
        assert ( voltmeterModel != null );

        // Things we'll be controlling.
        _pickupCoilModel = pickupCoilModel;
        _coilGraphic = pickupCoilGraphic.getCoilGraphic();
        _lightbulbModel = lightbulbModel;
        _voltmeterModel = voltmeterModel;

        //  Title
        Border lineBorder = BorderFactory.createLineBorder( Color.BLACK, 2 );
        TitledBorder titleBorder = BorderFactory.createTitledBorder( lineBorder, MagnetAndCompassStrings.TITLE_PICKUP_COIL_PANEL );
        titleBorder.setTitleFont( getTitleFont() );
        setBorder( titleBorder );

        JPanel indicatorPanel = new JPanel();
        {
            // Title
            TitledBorder indicatorBorder = new TitledBorder( MagnetAndCompassStrings.TITLE_INDICATOR );
            indicatorPanel.setBorder( indicatorBorder );

            // Layout
            EasyGridBagLayout layout = new EasyGridBagLayout( indicatorPanel );
            indicatorPanel.setLayout( layout );

            // Radio buttons with icons.
            ImageIcon lightbulbIcon = new ImageIcon( MagnetAndCompassResources.getImage( MagnetAndCompassConstants.LIGHTBULB_ICON ) );
            ImageIcon lightbulbIconSelected = new ImageIcon( MagnetAndCompassResources.getImage( MagnetAndCompassConstants.LIGHTBULB_ICON_SELECTED ) );
            _lightbulbRadioButton = new SimSharingJRadioButton( qualifyUserComponent( Components.lightbuldRadioButton ), lightbulbIcon );
            _lightbulbRadioButton.setSelectedIcon( lightbulbIconSelected );

            ImageIcon voltmeterIcon = new ImageIcon( MagnetAndCompassResources.getImage( MagnetAndCompassConstants.VOLTMETER_ICON ) );
            ImageIcon voltmeterIconSelected = new ImageIcon( MagnetAndCompassResources.getImage( MagnetAndCompassConstants.VOLTMETER_ICON_SELECTED ) );
            _voltmeterRadioButton = new SimSharingJRadioButton( qualifyUserComponent( Components.voltmeterRadioButton ), voltmeterIcon );
            _voltmeterRadioButton.setSelectedIcon( voltmeterIconSelected );

            // Horizontal layout.
            layout.addAnchoredComponent( _lightbulbRadioButton, 0, 0, GridBagConstraints.WEST );
            layout.addAnchoredComponent( _voltmeterRadioButton, 0, 1, GridBagConstraints.WEST );

            // Button group
            ButtonGroup group = new ButtonGroup();
            group.add( _lightbulbRadioButton );
            group.add( _voltmeterRadioButton );
        }

        // Number of loops
        JPanel loopsPanel = new JPanel();
        {
            JLabel loopsLabel = new JLabel( MagnetAndCompassStrings.LABEL_NUMBER_OF_LOOPS );

            // Spinner, keyboard editing disabled.
            SpinnerNumberModel spinnerModel = new SpinnerNumberModel();
            spinnerModel.setMaximum( new Integer( MagnetAndCompassConstants.MAX_PICKUP_LOOPS ) );
            spinnerModel.setMinimum( new Integer( MagnetAndCompassConstants.MIN_PICKUP_LOOPS ) );
            spinnerModel.setValue( new Integer( MagnetAndCompassConstants.MIN_PICKUP_LOOPS ) );
            _loopsSpinner = new SimSharingJSpinner( qualifyUserComponent( Components.loopsSpinner ), spinnerModel );
            JFormattedTextField tf = ( (JSpinner.DefaultEditor) _loopsSpinner.getEditor() ).getTextField();
            tf.setEditable( false );

            // Dimensions
            _loopsSpinner.setPreferredSize( SPINNER_SIZE );
            _loopsSpinner.setMaximumSize( SPINNER_SIZE );
            _loopsSpinner.setMinimumSize( SPINNER_SIZE );

            // Layout
            EasyGridBagLayout layout = new EasyGridBagLayout( loopsPanel );
            loopsPanel.setLayout( layout );
            layout.addAnchoredComponent( loopsLabel, 0, 0, GridBagConstraints.WEST );
            layout.addAnchoredComponent( _loopsSpinner, 0, 1, GridBagConstraints.WEST );
        }

        // Loop area
        {
            // Values are a percentage of the maximum.
            int max = 100;
            int min = (int) ( 100.0 * MagnetAndCompassConstants.MIN_PICKUP_LOOP_AREA / MagnetAndCompassConstants.MAX_PICKUP_LOOP_AREA );

            // Slider
            _areaControl = new SimSharingLinearValueControl( qualifyUserComponent( Components.loopAreaControl ), min, max, MagnetAndCompassStrings.LABEL_LOOP_AREA, "0", "%" );
            _areaControl.setValue( min );
            _areaControl.setMinorTickSpacing( 10 );
            _areaControl.setTextFieldEditable( true );
            _areaControl.setTextFieldColumns( 3 );
            _areaControl.setUpDownArrowDelta( 1 );
            _areaControl.setBorder( BorderFactory.createEtchedBorder() );
        }

        // Electrons on/off
        _electronsCheckBox = new SimSharingJCheckBox( qualifyUserComponent( Components.showElectrons ), MagnetAndCompassStrings.CHECK_BOX_SHOW_ELECTRONS );

        // Layout
        EasyGridBagLayout layout = new EasyGridBagLayout( this );
        setLayout( layout );
        int row = 0;
        layout.addFilledComponent( indicatorPanel, row++, 0, GridBagConstraints.HORIZONTAL );
        layout.addComponent( loopsPanel, row++, 0 );
        layout.addFilledComponent( _areaControl, row++, 0, GridBagConstraints.HORIZONTAL );
        layout.addComponent( _electronsCheckBox, row++, 0 );

        // Wire up event handling
        EventListener listener = new EventListener();
        _loopsSpinner.addChangeListener( listener );
        _areaControl.addChangeListener( listener );
        _lightbulbRadioButton.addActionListener( listener );
        _voltmeterRadioButton.addActionListener( listener );
        _electronsCheckBox.addActionListener( listener );

        // Set the state of the controls.
        update();
    }

    /**
     * Updates the control panel to match the state of the things that it's controlling.
     */
    public void update() {
        _loopsSpinner.setValue( new Integer( _pickupCoilModel.getNumberOfLoops() ) );
        _areaControl.setValue( (int) ( 100.0 * _pickupCoilModel.getLoopArea() / MagnetAndCompassConstants.MAX_PICKUP_LOOP_AREA ) );
        _lightbulbRadioButton.setSelected( _lightbulbModel.isEnabled() );
        _voltmeterRadioButton.setSelected( _voltmeterModel.isEnabled() );
        _electronsCheckBox.setSelected( _coilGraphic.isElectronAnimationEnabled() );
    }

    private IUserComponent qualifyUserComponent( IUserComponent userComponent ) {
        return UserComponentChain.chain( Components.pickupCoilControlPanel, userComponent );
    }

    //----------------------------------------------------------------------------
    // Feature controls
    //----------------------------------------------------------------------------

    /**
     * Access to the "Show Electrons" control.
     *
     * @param visible true or false
     * @return
     */
    public void setElectronsControlVisible( boolean visible ) {
        _electronsCheckBox.setVisible( visible );
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

        /**
         * ActionEvent handler.
         *
         * @param e the event
         * @throws IllegalArgumentException if the event is unexpected
         */
        public void actionPerformed( ActionEvent e ) {
            if ( e.getSource() == _lightbulbRadioButton ) {
                // Lightbulb enable
                _lightbulbModel.setEnabled( _lightbulbRadioButton.isSelected() );
                _voltmeterModel.setEnabled( !_lightbulbRadioButton.isSelected() );
            }
            else if ( e.getSource() == _voltmeterRadioButton ) {
                // Voltmeter enable
                _voltmeterModel.setEnabled( _voltmeterRadioButton.isSelected() );
                _lightbulbModel.setEnabled( !_voltmeterRadioButton.isSelected() );
            }
            else if ( e.getSource() == _electronsCheckBox ) {
                // Electrons enabled
                _coilGraphic.setElectronAnimationEnabled( _electronsCheckBox.isSelected() );
            }
            else {
                throw new IllegalArgumentException( "unexpected event: " + e );
            }
        }

        /**
         * ChangeEvent handler.
         *
         * @param e the event
         * @throws IllegalArgumentException if the event is unexpected
         */
        public void stateChanged( ChangeEvent e ) {
            if ( e.getSource() == _areaControl ) {
                // Read the value.
                int percent = (int) _areaControl.getValue();
                // Update the model.
                double area = ( percent / 100.0 ) * MagnetAndCompassConstants.MAX_PICKUP_LOOP_AREA;
                _pickupCoilModel.setLoopArea( area );
            }
            else if ( e.getSource() == _loopsSpinner ) {
                // Read the value.
                int numberOfLoops = ( (Integer) _loopsSpinner.getValue() ).intValue();
                // Update the model.
                _pickupCoilModel.setNumberOfLoops( numberOfLoops );
            }
            else {
                throw new IllegalArgumentException( "unexpected event: " + e );
            }
        }
    }
}