

package com.aimxcel.abclearn.magnetandcompass.control.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJButton;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingJSlider;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentChain;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassConstants;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassStrings;
import com.aimxcel.abclearn.magnetandcompass.MagnetAndCompassSimSharing.Components;
import com.aimxcel.abclearn.magnetandcompass.control.panel.MagnetAndCompassPanel;
import com.aimxcel.abclearn.magnetandcompass.module.ICompassGridModule;



public class GridControlsDialog extends PaintImmediateDialog implements ActionListener, ChangeListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private AimxcelApplication _app;
    private JSlider _spacingSlider, _needleSizeSlider;
    private JLabel _gridSpacingValue, _needleSizeValue;
    private JButton _okButton, _cancelButton;
    private int _xSpacing, _ySpacing;
    private Dimension _needleSize;

    //----------------------------------------------------------------------------
    // Constructors & initializers
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     *
     * @param app the application
     */
    public GridControlsDialog( AimxcelApplication app ) {
        super( app.getAimxcelFrame() );
        _app = app;

        super.setTitle( MagnetAndCompassStrings.TITLE_GRID_CONTROLS );
        super.setModal( false );
        super.setResizable( false );

        initValues();
        createUI( app.getAimxcelFrame() );
    }

    /**
     * Reads the current values for the grid controls.
     */
    private void initValues() {
        // Find the first module that has a grid and use its values.
        Module module = _app.getActiveModule();
        if ( !( module instanceof ICompassGridModule ) ) {
            int numberOfModules = _app.numModules();
            for ( int i = 0; i < numberOfModules; i++ ) {
                module = _app.getModule( i );
                if ( module instanceof ICompassGridModule ) {
                    break;
                }
                else {
                    module = null;
                }
            }
        }
        assert ( module != null ); // Why are you using this dialog in your app?
        ICompassGridModule compassGridModule = (ICompassGridModule) module;
        _xSpacing = compassGridModule.getGridXSpacing();
        _ySpacing = compassGridModule.getGridYSpacing();
        _needleSize = compassGridModule.getGridNeedleSize();
    }

    /**
     * Creates the user interface for the dialog.
     *
     * @param parent the parent Frame
     */
    private void createUI( Frame parent ) {
        JPanel inputPanel = createInputPanel();
        JPanel actionsPanel = createActionsPanel();

        JPanel bottomPanel = new JPanel( new BorderLayout() );
        bottomPanel.add( new JSeparator(), BorderLayout.NORTH );
        bottomPanel.add( actionsPanel, BorderLayout.CENTER );

        JPanel mainPanel = new JPanel( new BorderLayout() );
        mainPanel.setBorder( new EmptyBorder( 10, 10, 0, 10 ) );
        mainPanel.add( inputPanel, BorderLayout.CENTER );
        mainPanel.add( bottomPanel, BorderLayout.SOUTH );

        getContentPane().add( mainPanel );
        pack();
        this.setResizable( false );
        this.setLocationRelativeTo( parent );
    }

    /**
     * Creates the dialog's input panel.
     *
     * @return the input panel
     */
    private JPanel createInputPanel() {

        // Warning panel
        JLabel warningMessage = new JLabel( MagnetAndCompassStrings.LABEL_GRID_CONTROLS_WARNING );
        JPanel warningPanel = new JPanel();
        warningPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
        warningPanel.add( warningMessage );

        // Grid spacing
        JLabel spacingLabel = new JLabel( MagnetAndCompassStrings.LABEL_NEEDLE_SPACING );
        {
            // Slider
            _spacingSlider = new SimSharingJSlider( qualifiedUserComponent( Components.needleSpacingSlider ) );
            _spacingSlider.setMinimum( MagnetAndCompassConstants.GRID_SPACING_MIN );
            _spacingSlider.setMaximum( MagnetAndCompassConstants.GRID_SPACING_MAX );
            _spacingSlider.setValue( _xSpacing );
            _spacingSlider.setPreferredSize( MagnetAndCompassPanel.SLIDER_SIZE );
            _spacingSlider.setMaximumSize( MagnetAndCompassPanel.SLIDER_SIZE );
            _spacingSlider.setMinimumSize( MagnetAndCompassPanel.SLIDER_SIZE );

            // Value
            _gridSpacingValue = new JLabel( String.valueOf( _xSpacing ) );
        }

        // Needle size
        JLabel needleSizeLabel = new JLabel( MagnetAndCompassStrings.LABEL_NEEDLE_SIZE );
        {
            // Slider
            _needleSizeSlider = new SimSharingJSlider( qualifiedUserComponent( Components.needleSizeSlider ) );
            _needleSizeSlider.setMinimum( MagnetAndCompassConstants.GRID_NEEDLE_WIDTH_MIN );
            _needleSizeSlider.setMaximum( MagnetAndCompassConstants.GRID_NEEDLE_WIDTH_MAX );
            _needleSizeSlider.setValue( _needleSize.width );
            _needleSizeSlider.setPreferredSize( MagnetAndCompassPanel.SLIDER_SIZE );
            _needleSizeSlider.setMaximumSize( MagnetAndCompassPanel.SLIDER_SIZE );
            _needleSizeSlider.setMinimumSize( MagnetAndCompassPanel.SLIDER_SIZE );

            // Value
            String value = String.valueOf( _needleSize.width ) + "x" + String.valueOf( _needleSize.height );
            _needleSizeValue = new JLabel( value );
        }

        // Listeners
        _spacingSlider.addChangeListener( this );
        _needleSizeSlider.addChangeListener( this );

        // Layout
        JPanel controlPanel = new JPanel();
        {
            EasyGridBagLayout layout = new EasyGridBagLayout( controlPanel );
            controlPanel.setLayout( layout );
            controlPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

            // Grid spacing
            int row = 0;
            layout.addAnchoredComponent( spacingLabel, row, 0, GridBagConstraints.EAST );
            layout.addAnchoredComponent( _spacingSlider, row, 1, GridBagConstraints.WEST );
            layout.addAnchoredComponent( _gridSpacingValue, row, 2, GridBagConstraints.WEST );

            // Needle size
            row++;
            layout.addAnchoredComponent( needleSizeLabel, row, 0, GridBagConstraints.EAST );
            layout.addAnchoredComponent( _needleSizeSlider, row, 1, GridBagConstraints.WEST );
            layout.addAnchoredComponent( _needleSizeValue, row, 2, GridBagConstraints.WEST );
        }

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout( new BorderLayout() );
        inputPanel.add( warningPanel, BorderLayout.NORTH );
        inputPanel.add( controlPanel, BorderLayout.CENTER );
        return inputPanel;
    }

    private IUserComponent qualifiedUserComponent( IUserComponent userComponent ) {
        return UserComponentChain.chain( Components.fieldControlsDialog, userComponent );
    }

    /**
     * Creates the dialog's actions panel, consisting of OK and Cancel buttons.
     *
     * @return the actions panel
     */
    private JPanel createActionsPanel() {
        _okButton = new SimSharingJButton( qualifiedUserComponent( Components.okButton ), MagnetAndCompassStrings.BUTTON_OK );
        _okButton.addActionListener( this );

        _cancelButton = new SimSharingJButton( qualifiedUserComponent( Components.cancelButton ), MagnetAndCompassStrings.BUTTON_CANCEL );
        _cancelButton.addActionListener( this );

        JPanel innerPanel = new JPanel( new GridLayout( 1, 2, 10, 0 ) );
        innerPanel.add( _okButton );
        innerPanel.add( _cancelButton );

        JPanel actionPanel = new JPanel( new FlowLayout() );
        actionPanel.add( innerPanel );

        return actionPanel;
    }

    /**
     * Handles OK and Cancel button presses.
     */
    public void actionPerformed( ActionEvent e ) {
        if ( e.getSource() == _okButton ) {
            this.dispose();
        }
        else if ( e.getSource() == _cancelButton ) {
            revert();
            this.dispose();
        }
    }

    public void revert() {
        setAllGrids( _xSpacing, _ySpacing, _needleSize );
    }

    /**
     * Handles changes to the sliders.  Since the parameters are closely
     * related, we simply read and update all values.
     *
     * @param e the event
     */
    public void stateChanged( ChangeEvent e ) {
        int spacing = _spacingSlider.getValue();
        int needleWidth = _needleSizeSlider.getValue();
        int needleHeight = (int) ( needleWidth / MagnetAndCompassConstants.GRID_NEEDLE_ASPECT_RATIO );
        Dimension needleSize = new Dimension( needleWidth, needleHeight );

        _gridSpacingValue.setText( String.valueOf( spacing ) );
        _needleSizeValue.setText( String.valueOf( needleWidth ) + "x" + String.valueOf( needleHeight ) );

        setAllGrids( spacing, spacing, needleSize );
    }

    /**
     * Sets grid parameters for all Modules that have a grid.
     *
     * @param xSpacing   horizontal spacing, in pixels
     * @param ySpacing   vertical spacing, in pixels
     * @param needleSize needle size, in pixels
     */
    private void setAllGrids( int xSpacing, int ySpacing, Dimension needleSize ) {
        int numberOfModules = _app.numModules();
        for ( int i = 0; i < numberOfModules; i++ ) {
            Module module = _app.getModule( i );
            if ( module instanceof ICompassGridModule ) {
                ( (ICompassGridModule) module ).setGridSpacing( xSpacing, ySpacing );
                ( (ICompassGridModule) module ).setGridNeedleSize( needleSize );
            }
        }
    }
}