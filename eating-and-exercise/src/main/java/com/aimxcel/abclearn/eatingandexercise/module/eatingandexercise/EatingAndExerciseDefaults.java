
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.eatingandexercise.model.EatingAndExerciseUnits;


public class EatingAndExerciseDefaults {
    public static final double CLOCK_DT = 1000.0 * 250 / 2;
    //    public static final int CLOCK_DELAY = 30;
    public static final int CLOCK_DELAY = 30 * 3 * 2;
    public static final boolean STARTS_PAUSED = true;
    public static final double SINGLE_STEP_TIME = EatingAndExerciseUnits.monthsToSeconds( 1 );
}
