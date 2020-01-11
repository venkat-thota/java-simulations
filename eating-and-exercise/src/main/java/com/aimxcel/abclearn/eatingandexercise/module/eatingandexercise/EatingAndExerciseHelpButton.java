
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;


public class EatingAndExerciseHelpButton extends JButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EatingAndExerciseHelpButton() {
        super( getHelpString() );
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                AimxcelOptionPane.showMessageDialog( EatingAndExerciseHelpButton.this, EatingAndExerciseResources.getString( "help.message" ), getHelpString() );
            }
        } );
    }

    private static String getHelpString() {
        return EatingAndExerciseResources.getCommonString( AimxcelCommonResources.STRING_HELP_MENU_HELP );
    }
}
