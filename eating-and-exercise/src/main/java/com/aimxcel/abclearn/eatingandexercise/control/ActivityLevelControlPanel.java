
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;

public class ActivityLevelControlPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActivityLevelControlPanel( final Human human ) {
        setBorder( CalorieSelectionPanel.createTitledBorder( EatingAndExerciseResources.getString( "exercise.lifestyle" ) ) );
        ButtonGroup bg = new ButtonGroup();
        for ( int i = 0; i < Activity.DEFAULT_ACTIVITY_LEVELS.length; i++ ) {
            JRadioButton jRadioButton = new JRadioButton( Activity.DEFAULT_ACTIVITY_LEVELS[i].toString(),
                                                          Activity.DEFAULT_ACTIVITY_LEVELS[i].getValue() == human.getActivityLevel() );
            final int i1 = i;
            jRadioButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    human.setActivityLevel( Activity.DEFAULT_ACTIVITY_LEVELS[i1] );
                }
            } );
            jRadioButton.setFont( new AimxcelFont( 13, true ) );
            bg.add( jRadioButton );
            add( jRadioButton );
        }
    }
}
