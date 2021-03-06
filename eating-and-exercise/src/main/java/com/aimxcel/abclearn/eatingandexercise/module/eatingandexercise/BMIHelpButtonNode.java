
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLImageButtonNode;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class BMIHelpButtonNode extends HTMLImageButtonNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Human human;

    public BMIHelpButtonNode( final Component parentComponent, final Human human ) {
        super( "?", new AimxcelFont( Font.BOLD, 14 ), Color.red );
        this.human = human;
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                String currentBMI = EatingAndExerciseResources.getString( "bmi.current" );
                String line1 = EatingAndExerciseResources.getString( "bmi.table" );
                String line2 = EatingAndExerciseResources.getString( "bmi.columns" );
                String line3 = EatingAndExerciseResources.getString( "bmi.underweight" );
                String line4 = EatingAndExerciseResources.getString( "bmi.normal" );
                String line5 = EatingAndExerciseResources.getString( "bmi.overweight" );
                String line6 = EatingAndExerciseResources.getString( "bmi.obese" );
                AimxcelOptionPane.showMessageDialog( parentComponent, currentBMI + new DecimalFormat( "0.0" ).format( human.getBMI() ) + "\n\n" +
                                                                   line1 +
                                                                   line2 +
                                                                   line3 +
                                                                   line4 +
                                                                   line5 +
                                                                   line6 );
            }
        } );
    }
}
