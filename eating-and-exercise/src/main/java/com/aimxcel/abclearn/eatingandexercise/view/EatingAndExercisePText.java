
package com.aimxcel.abclearn.eatingandexercise.view;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

import edu.umd.cs.piccolo.nodes.PText;

/**
 * Created by: Sam
 * May 21, 2008 at 10:53:52 AM
 */
public class EatingAndExercisePText extends PText {
    public EatingAndExercisePText() {
        setFont( new AimxcelFont() );
    }

    public EatingAndExercisePText( String aText ) {
        super( aText );
        setFont( new AimxcelFont() );
    }
}
