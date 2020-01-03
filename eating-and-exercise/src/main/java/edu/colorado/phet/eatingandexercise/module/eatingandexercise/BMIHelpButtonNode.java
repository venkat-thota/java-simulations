
package edu.colorado.phet.eatingandexercise.module.eatingandexercise;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnOptionPane;

import edu.colorado.phet.common.piccolophet.nodes.HTMLImageButtonNode;
import edu.colorado.phet.eatingandexercise.EatingAndExerciseResources;
import edu.colorado.phet.eatingandexercise.model.Human;

/**
 * Created by: Sam
 * May 4, 2008 at 11:16:09 PM
 */
public class BMIHelpButtonNode extends HTMLImageButtonNode {
    private Human human;

    public BMIHelpButtonNode( final Component parentComponent, final Human human ) {
        super( "?", new AbcLearnFont( Font.BOLD, 14 ), Color.red );
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
                AbcLearnOptionPane.showMessageDialog( parentComponent, currentBMI + new DecimalFormat( "0.0" ).format( human.getBMI() ) + "\n\n" +
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
