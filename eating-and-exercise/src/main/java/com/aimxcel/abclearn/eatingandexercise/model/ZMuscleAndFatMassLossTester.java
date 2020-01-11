
package com.aimxcel.abclearn.eatingandexercise.model;

import junit.framework.TestCase;


public class ZMuscleAndFatMassLossTester extends TestCase {
    public void testWeightLoss( HumanUpdate update ) {

        Human h = new Human();
        double expectedChange = EatingAndExerciseUnits.caloriesToKG( h.getDeltaCaloriesGainedPerDay() );
        double mass = h.getMass();
        update.update( h, EatingAndExerciseUnits.daysToSeconds( 1 ) );
        double finalMass = h.getMass();
        double actualChange = finalMass - mass;
        assertEquals( "mass should match", expectedChange, actualChange, 1E-6 );
    }

    public void testWeightLoss2() {
        testWeightLoss( new MuscleAndFatMassLoss2() );
    }
}
