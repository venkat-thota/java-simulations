
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.CalorieSet;
import com.aimxcel.abclearn.eatingandexercise.model.Human;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.CaloricFoodItem;


public class FoodSelectionPanel extends JPanel implements ICalorieSelectionPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CalorieSelectionPanel selectionPanel;
    private Human human;

    public FoodSelectionPanel( Human human, CalorieSet available, CalorieSet calorieSet, String availableTitle, String selectedTitle ) {
        super( new GridBagLayout() );
        this.human = human;
        selectionPanel = new CalorieSelectionPanel( available, calorieSet, availableTitle, selectedTitle );

        JPanel activityLevels = new ActivityLevelsPanel();
        add( activityLevels, new GridBagConstraints( 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 1, 1, 1, 1 ), 0, 0 ), 0 );
        add( selectionPanel, new GridBagConstraints( 0, 1, 1, 1, 1, 1E6, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 1, 1, 1, 1 ), 0, 0 ) );
    }

    public void addListener( CalorieSelectionPanel.Listener listener ) {
        selectionPanel.addListener( listener );
    }

    private class ActivityLevelsPanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private ActivityLevelsPanel() {
            setBorder( CalorieSelectionPanel.createTitledBorder( EatingAndExerciseResources.getString( "diet.base" ) ) );
            ButtonGroup bg = new ButtonGroup();
            final CaloricFoodItem[] baseDiets = new CaloricFoodItem[]{
                    new CaloricFoodItem( EatingAndExerciseResources.getString( "diet.nothing" ), null, 0, 0, 0, false ),
                    human.getDefaultIntake(),
            };
            for ( int i = 0; i < baseDiets.length; i++ ) {
                JRadioButton jRadioButton = new JRadioButton( baseDiets[i].getName(),
                                                              human.getSelectedFoods().contains( baseDiets[i] ) );
                final int i1 = i;
                jRadioButton.addActionListener( new ActionListener() {
                    public void actionPerformed( ActionEvent e ) {
                        human.getSelectedFoods().removeAll( baseDiets );
                        human.getSelectedFoods().addItem( baseDiets[i1] );
                    }
                } );
                jRadioButton.setFont( new AimxcelFont( 13, true ) );
                bg.add( jRadioButton );
                add( jRadioButton );
            }
        }
    }
}
