
package com.aimxcel.abclearn.eatingandexercise.model;


public class MuscleGainedFromExercising implements HumanUpdate {
    private static boolean debug = false;
    public static boolean enabled = true;

   
    public void update( Human human, double dt ) {
        if ( !enabled ) {
            return;
        }
//        double calExercise = human.getCaloriesExerciseAndActivityPerDay() * EatingAndExerciseUnits.secondsToDays( dt );
        //8/27/08 NP added ActivityCaloriesPerDay added to calExercise used for muscle gained
        double calExercise = ( human.getCaloriesExercisePerDay() + human.getActivityCaloriesPerDay() / 2 ) * EatingAndExerciseUnits.secondsToDays( dt );
        println( "Calories exercise: " + calExercise );
//        double percentFat = human.getGender().getStdPercentFat();
        double stdBMI = human.getGender().getStdBMI();
        println( "stdBMI = " + stdBMI );
        double stdLeanMassFraction = human.getGender().getStdLeanMassFraction();
        println( "stdLeanMassFraction = " + stdLeanMassFraction );
        double LBM_0 = stdBMI * human.getHeight() * human.getHeight() * stdLeanMassFraction;//todo: should use standard height instead of actual human instance height?
        println( "LBM_0 = human.getGender().getStdBMI() * human.getHeight() * human.getHeight() * human.getGender().getStdLeanMassFraction() =" + LBM_0 + " kg" );
        double muscleMassGained = Math.max( 0, 0.08 * calExercise * ( LBM_0 - human.getLeanBodyMass() ) / LBM_0 / 4000.0 );
        println( "Human lean body mass=" + human.getLeanBodyMass() );
        println( "muscleMassGained= 0.08 * calExercise * ( LBM_0 - human.getLeanBodyMass() ) / LBM_0 / 4000.0 = " + muscleMassGained + " kg" );
        double origMass = human.getMass();
        double newLeanMass = muscleMassGained + human.getLeanBodyMass();
        double fracLean = newLeanMass / origMass;
        human.setLeanFraction( fracLean );
    }

    public static void main( String[] args ) {
        Human human = new Human();
        System.out.println( "Before" );
        System.out.println( "human.getFatMassPercent() = " + human.getFatMassPercent() );
        System.out.println( "human.getMass() = " + human.getMass() );
        new MuscleGainedFromExercising().update( human, EatingAndExerciseUnits.daysToSeconds( 1 ) );
        System.out.println( "After:" );
        System.out.println( "human.getFatMassPercent() = " + human.getFatMassPercent() );
        System.out.println( "human.getMass() = " + human.getMass() );
    }

    private static void println( String s ) {
        if ( debug ) {
            System.out.println( s );
        }
    }
}
