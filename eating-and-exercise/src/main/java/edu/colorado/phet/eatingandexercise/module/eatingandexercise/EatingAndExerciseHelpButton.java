
package edu.colorado.phet.eatingandexercise.module.eatingandexercise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;

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
                AimxcelOptionPane.showMessageDialog( EatingAndExerciseHelpButton.this, EatingAndExerciseResources.getString( "help.message" ), getHelpString() );
            }
        } );
    }

    private static String getHelpString() {
        return EatingAndExerciseResources.getCommonString( AimxcelCommonResources.STRING_HELP_MENU_HELP );
    }
}
