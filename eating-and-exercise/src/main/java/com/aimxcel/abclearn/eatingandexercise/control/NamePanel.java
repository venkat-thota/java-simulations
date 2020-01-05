
package com.aimxcel.abclearn.eatingandexercise.control;

import javax.swing.*;

import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;

/**
 * Created by: Sam
 * Apr 23, 2008 at 8:02:01 AM
 */
public class NamePanel extends JPanel {
    public NamePanel( Human human ) {

        JPanel namePanel = new JPanel();
        namePanel.add( new JLabel( EatingAndExerciseResources.getString( "name" ) ) );
        JTextField name = new JTextField( human.getName() );
        name.setColumns( 10 );
        namePanel.add( name );
        add( namePanel );
    }
}