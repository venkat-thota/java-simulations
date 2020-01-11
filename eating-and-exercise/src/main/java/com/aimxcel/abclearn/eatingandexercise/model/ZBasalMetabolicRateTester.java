
package com.aimxcel.abclearn.eatingandexercise.model;

import junit.framework.TestCase;


public class ZBasalMetabolicRateTester extends TestCase {
    public void testBMR() {
        assertEquals( 1266, BasalMetabolicRate.getBasalMetabolicRateHarrisBenedict( 59, 1.68, EatingAndExerciseUnits.yearsToSeconds( 55 ), Human.Gender.FEMALE ), 10 );
    }
}
