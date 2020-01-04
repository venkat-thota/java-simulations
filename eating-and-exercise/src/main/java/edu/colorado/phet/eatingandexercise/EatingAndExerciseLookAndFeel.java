
package edu.colorado.phet.eatingandexercise;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLookAndFeel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

import edu.colorado.phet.eatingandexercise.view.EatingAndExerciseColorScheme;

/**
 * Created by: Sam
 * Sep 11, 2008 at 10:09:35 PM
 */
public class EatingAndExerciseLookAndFeel extends AimxcelLookAndFeel {
    public EatingAndExerciseLookAndFeel() {
        setFont( new AimxcelFont( 14, true ) );
        setBackgroundColor( EatingAndExerciseColorScheme.getBackgroundColor() );
        setTextFieldBackgroundColor( Color.white );
    }
}
