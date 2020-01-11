
package com.aimxcel.abclearn.eatingandexercise;

import java.awt.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelLookAndFeel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.eatingandexercise.view.EatingAndExerciseColorScheme;


public class EatingAndExerciseLookAndFeel extends AimxcelLookAndFeel {
    public EatingAndExerciseLookAndFeel() {
        setFont( new AimxcelFont( 14, true ) );
        setBackgroundColor( EatingAndExerciseColorScheme.getBackgroundColor() );
        setTextFieldBackgroundColor( Color.white );
    }
}
