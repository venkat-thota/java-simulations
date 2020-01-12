
package com.aimxcel.abclearn.greenhouse.developer;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearValueControl;
import com.aimxcel.abclearn.photonabsorption.model.PhotonAbsorptionModel;
import com.aimxcel.abclearn.photonabsorption.model.PhotonAbsorptionStrategy;


public class PhotonAbsorptionParamsDlg extends PaintImmediateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     */
    public PhotonAbsorptionParamsDlg (Frame frame, final PhotonAbsorptionModel model){
        super(frame);

        setTitle("Params");
        setLayout(new GridLayout( 3, 2));

        // Create and add the slider for controlling the photon emission rate.
        add(new JLabel( "Single Target Photon Emission Frequency", JLabel.CENTER));
        final LinearValueControl singleTargetEmissionRateSlider = new LinearValueControl( 0, 5, "Frequency:", "#.#", "Photons/sec" );
        singleTargetEmissionRateSlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                model.setPhotonEmissionPeriod( 1 / singleTargetEmissionRateSlider.getValue() * 1000 );
            }
        });
        singleTargetEmissionRateSlider.setValue( 1 / model.getPhotonEmissionPeriod() * 1000 );
        add( singleTargetEmissionRateSlider  );

        // Create and add the slider for controlling the photon emission rate.
        add(new JLabel( "Multi-Target Photon Emission Frequency", JLabel.CENTER));
        final LinearValueControl multiTargetEmissionRateSlider = new LinearValueControl( 0, 5, "Frequency:", "#.#", "Photons/sec" );
        multiTargetEmissionRateSlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                model.setPhotonEmissionPeriod( 1 / multiTargetEmissionRateSlider.getValue() * 1000 );
            }
        });
        multiTargetEmissionRateSlider.setValue( 1 / model.getPhotonEmissionPeriod() * 1000 );
        add( multiTargetEmissionRateSlider  );

        // Create and add the slider for controlling absorption probability.
        add(new JLabel( "Absorption Probability", JLabel.CENTER));
        final LinearValueControl abosrptionProbabilitySlider = new LinearValueControl( 0, 1, "Probability", "#.#", null );
        abosrptionProbabilitySlider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                PhotonAbsorptionStrategy.photonAbsorptionProbability.set( abosrptionProbabilitySlider.getValue() );
            }
        });
        abosrptionProbabilitySlider.setValue( PhotonAbsorptionStrategy.photonAbsorptionProbability.get() );
        add( abosrptionProbabilitySlider  );

        // Set this to hide itself if the user clicks the close button.
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Center this in the parent frame.
        setLocationRelativeTo( null );

        // Size the dialog to just fit all the controls.
        pack();
    }
}
