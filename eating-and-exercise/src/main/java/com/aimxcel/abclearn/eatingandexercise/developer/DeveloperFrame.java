
package com.aimxcel.abclearn.eatingandexercise.developer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.valuecontrol.LinearValueControl;
import com.aimxcel.abclearn.eatingandexercise.model.MuscleAndFatMassLoss2;
import com.aimxcel.abclearn.eatingandexercise.model.MuscleGainedFromExercising;

public class DeveloperFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeveloperFrame() {
        VerticalLayoutPanel verticalLayoutPanel = new VerticalLayoutPanel();
        final LinearValueControl slider = new LinearValueControl( 0, 1, MuscleAndFatMassLoss2.FRACTION_FAT_LOST, "fraction fat", "0.0000", "" );
        slider.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                MuscleAndFatMassLoss2.FRACTION_FAT_LOST = slider.getValue();
            }
        } );
        verticalLayoutPanel.add( slider );
        final JCheckBox jCheckBox = new JCheckBox( "Use Muscle Gained From Exercising Algorithm", MuscleGainedFromExercising.enabled );
        jCheckBox.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                MuscleGainedFromExercising.enabled = jCheckBox.isSelected();
            }
        } );
        verticalLayoutPanel.add( jCheckBox );


        final JCheckBox asymmetry = new JCheckBox( "all fat when gaining weight (asymmetric)", MuscleAndFatMassLoss2.allFatWhenGainingWeight );
        asymmetry.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                MuscleAndFatMassLoss2.allFatWhenGainingWeight = asymmetry.isSelected();
            }
        } );
        verticalLayoutPanel.add( asymmetry );


        setContentPane( verticalLayoutPanel );
        pack();
    }
}
