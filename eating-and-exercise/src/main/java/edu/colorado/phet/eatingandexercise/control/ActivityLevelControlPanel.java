
package edu.colorado.phet.eatingandexercise.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;

import edu.colorado.phet.eatingandexercise.EatingAndExerciseResources;
import edu.colorado.phet.eatingandexercise.model.Human;

/**
 * Created by: Sam
 * Jun 26, 2008 at 11:57:34 AM
 */
public class ActivityLevelControlPanel extends JPanel {
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
            jRadioButton.setFont( new AbcLearnFont( 13, true ) );
            bg.add( jRadioButton );
            add( jRadioButton );
        }
    }
}
