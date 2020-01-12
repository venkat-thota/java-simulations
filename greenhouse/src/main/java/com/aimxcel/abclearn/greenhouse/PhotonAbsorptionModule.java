
package com.aimxcel.abclearn.greenhouse;

import java.awt.Frame;

import javax.swing.JComponent;

import com.aimxcel.abclearn.greenhouse.controlpanel.PhotonAbsorptionControlPanel;
import com.aimxcel.abclearn.greenhouse.developer.PhotonAbsorptionParamsDlg;
import com.aimxcel.abclearn.greenhouse.view.PhotonAbsorptionCanvas;
import com.aimxcel.abclearn.photonabsorption.model.PhotonAbsorptionModel;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.CoreModule;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.mediabuttons.CoreClockControlPanel;

public class PhotonAbsorptionModule extends CoreModule {

    private static final int CLOCK_DELAY = 1000 / GreenhouseDefaults.CLOCK_FRAME_RATE;
    private static final double CLOCK_DT = 1000 / GreenhouseDefaults.CLOCK_FRAME_RATE;

    //----------------------------------------------------------------------------
    // Instance Data
    //----------------------------------------------------------------------------

    private final PhotonAbsorptionModel model;
    private final AimxcelPCanvas canvas;
    private final PhotonAbsorptionControlPanel controlPanel;
    private CoreClockControlPanel clockControlPanel;

    // Developer controls
    private PhotonAbsorptionParamsDlg photonAbsorptionParamsDlg;
    private boolean photonAbsorptionParamsDlgVisible;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    public PhotonAbsorptionModule( Frame parentFrame ) {
        super( GreenhouseResources.getString( "ModuleTitle.PhotonAbsorptionModule" ),
                new ConstantDtClock( CLOCK_DELAY, CLOCK_DT ));

        // Explicitly turn off the logo panel.
        setLogoPanel( null );

        // Physical model
        model = new PhotonAbsorptionModel( (ConstantDtClock)getClock());

        // Canvas
        canvas = new PhotonAbsorptionCanvas(model, true);
        setSimulationPanel( canvas );

        // Control panel.
        controlPanel = new PhotonAbsorptionControlPanel(this, model);
        setControlPanel(controlPanel);

        // Help
        if ( hasHelp() ) {
            //XXX add help items
        }

        // Developer controls.
        if ( AimxcelApplication.getInstance().isDeveloperControlsEnabled() ) {
            photonAbsorptionParamsDlg = new PhotonAbsorptionParamsDlg( parentFrame, model );
            photonAbsorptionParamsDlgVisible = false;
        }

        // Set initial state
        reset();
    }

    //----------------------------------------------------------------------------
    // Methods
    //----------------------------------------------------------------------------

	@Override
    protected JComponent createClockControlPanel( IClock clock ) {
		clockControlPanel = new CoreClockControlPanel( clock );
        return clockControlPanel;
    }

    @Override
    public void reset() {
        model.reset();
    }

    @Override
    public void activate() {
        super.activate();
        if ( photonAbsorptionParamsDlg != null && photonAbsorptionParamsDlgVisible ) {
            photonAbsorptionParamsDlg.setVisible( true );
        }
    }

    @Override
    public void deactivate() {
        if ( photonAbsorptionParamsDlg != null ) {
            photonAbsorptionParamsDlg.setVisible( false );
        }
        super.deactivate();
    }

    public void setPhotonAbsorptionParamsDlgVisible(boolean visible){
        photonAbsorptionParamsDlgVisible = visible;
        if ( isActive() && photonAbsorptionParamsDlg != null ) {
            photonAbsorptionParamsDlg.setVisible( visible );
        }
    }
}
