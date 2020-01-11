
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.eatingandexercise.model.EatingAndExerciseUnits;
import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class AgeRangeMessage extends TimeoutWarningMessage {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Human human;

    public AgeRangeMessage( final Human human ) {
        super( "<html>This simulation is based on data<br>from 20-60 year olds.<html>" );
        this.human = human;

        human.addListener( new Human.Adapter() {
            public void ageChanged() {
                update();
            }
        } );
    }

    protected void update() {
        if ( human.getAge() < EatingAndExerciseUnits.yearsToSeconds( 20 ) || human.getAge() > EatingAndExerciseUnits.yearsToSeconds( 60 ) ) {
            resetVisibleTime();
        }
    }
}