
package edu.colorado.phet.eatingandexercise.module.eatingandexercise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnOptionPane;

import edu.colorado.phet.eatingandexercise.EatingAndExerciseResources;

/**
 * Created by: Sam
 * Aug 15, 2008 at 11:11:24 AM
 */
public class EatingAndExerciseHelpButton extends JButton {
    public EatingAndExerciseHelpButton() {
        super( getHelpString() );
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                AbcLearnOptionPane.showMessageDialog( EatingAndExerciseHelpButton.this, EatingAndExerciseResources.getString( "help.message" ), getHelpString() );
            }
        } );
    }

    private static String getHelpString() {
        return EatingAndExerciseResources.getCommonString( AbcLearnCommonResources.STRING_HELP_MENU_HELP );
    }
}
