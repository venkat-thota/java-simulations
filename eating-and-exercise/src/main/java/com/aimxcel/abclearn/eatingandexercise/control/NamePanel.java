
package com.aimxcel.abclearn.eatingandexercise.control;

import javax.swing.*;

import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class NamePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NamePanel( Human human ) {

        JPanel namePanel = new JPanel();
        namePanel.add( new JLabel( EatingAndExerciseResources.getString( "name" ) ) );
        JTextField name = new JTextField( human.getName() );
        name.setColumns( 10 );
        namePanel.add( name );
        add( namePanel );
    }
}
