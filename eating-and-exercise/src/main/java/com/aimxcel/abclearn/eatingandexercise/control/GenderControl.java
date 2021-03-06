
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class GenderControl extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenderControl( final Human human ) {
        setLayout( new FlowLayout() );
        final JRadioButton femaleButton = new JRadioButton( EatingAndExerciseResources.getString( "gender.female" ), human.getGender() == Human.Gender.FEMALE );
        femaleButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                human.setGender( Human.Gender.FEMALE );
            }
        } );
        add( femaleButton );
        final JRadioButton maleButton = new JRadioButton( EatingAndExerciseResources.getString( "gender.male" ), human.getGender() == Human.Gender.MALE );
        maleButton.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                human.setGender( Human.Gender.MALE );
            }
        } );
        add( maleButton );
        human.addListener( new Human.Adapter() {
            public void genderChanged() {
                femaleButton.setSelected( human.getGender() == Human.Gender.FEMALE );
                maleButton.setSelected( human.getGender() == Human.Gender.MALE );
            }
        } );
    }
}
