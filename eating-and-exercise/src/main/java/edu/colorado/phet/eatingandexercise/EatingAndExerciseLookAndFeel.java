
package edu.colorado.phet.eatingandexercise;

import java.awt.*;

import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnLookAndFeel;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;

import edu.colorado.phet.eatingandexercise.view.EatingAndExerciseColorScheme;

/**
 * Created by: Sam
 * Sep 11, 2008 at 10:09:35 PM
 */
public class EatingAndExerciseLookAndFeel extends AbcLearnLookAndFeel {
    public EatingAndExerciseLookAndFeel() {
        setFont( new AbcLearnFont( 14, true ) );
        setBackgroundColor( EatingAndExerciseColorScheme.getBackgroundColor() );
        setTextFieldBackgroundColor( Color.white );
    }
}
