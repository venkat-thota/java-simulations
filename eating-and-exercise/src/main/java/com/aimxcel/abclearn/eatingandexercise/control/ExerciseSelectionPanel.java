
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.eatingandexercise.model.CalorieSet;
import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class ExerciseSelectionPanel extends JPanel implements ICalorieSelectionPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Human human;
    private CalorieSelectionPanel calorieSelectionPanel;

    public ExerciseSelectionPanel( final Human human, final CalorieSet available, final CalorieSet selected, String availableTitle, String selectedTitle ) {
        setLayout( new GridBagLayout() );
        calorieSelectionPanel = new CalorieSelectionPanel( available, selected, availableTitle, selectedTitle );
        this.human = human;

//        JPanel activityLevels = new ActivityControlPanel( human );
//        add( activityLevels, new GridBagConstraints( 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 1, 1, 1, 1 ), 0, 0 ), 0 );
        add( calorieSelectionPanel, new GridBagConstraints( 0, 1, 1, 1, 1, 1E6, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 1, 1, 1, 1 ), 0, 0 ) );
    }

    public void addListener( CalorieSelectionPanel.Listener listener ) {
        calorieSelectionPanel.addListener( listener );
    }

}
