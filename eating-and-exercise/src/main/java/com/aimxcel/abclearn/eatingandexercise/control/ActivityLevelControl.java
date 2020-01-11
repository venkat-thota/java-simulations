
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.EatingAndExerciseCanvas;

public class ActivityLevelControl extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActivityLevelControl( final EatingAndExerciseCanvas canvas, Human human ) {
        add( new ActivityLevelComboBox( canvas, human ) );
        final JButton button = new JButton( "?" );
        add( button );
        button.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                AimxcelOptionPane.showMessageDialog( canvas, getExplanationText() );
            }
        } );
    }

    String[] activityLevelKeys = new String[]{
            "very-sedentary",
            "sedentary",
            "moderate",
            "active",
    };

    private String getExplanationText() {
        String string = "<html>";
        for ( int i = 0; i < activityLevelKeys.length; i++ ) {
            String key = EatingAndExerciseResources.getString( "activity." + activityLevelKeys[i] );
            String desc = EatingAndExerciseResources.getString( "activity." + activityLevelKeys[i] + ".description" );
            string += key + ": " + desc + "<br><br><br>";
        }
        return string + "</html>";
    }
}
