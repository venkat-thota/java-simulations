
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.TimingStrategy;


public class EatingAndExerciseClock extends SwingClock {
    public EatingAndExerciseClock() {
        super( EatingAndExerciseDefaults.CLOCK_DELAY, new TimingStrategy.Constant( EatingAndExerciseDefaults.CLOCK_DT, EatingAndExerciseDefaults.SINGLE_STEP_TIME ) );
    }

    public void setDt( double clockDt ) {
        setTimingStrategy( new TimingStrategy.Constant( clockDt, EatingAndExerciseDefaults.SINGLE_STEP_TIME ) );
    }
}
