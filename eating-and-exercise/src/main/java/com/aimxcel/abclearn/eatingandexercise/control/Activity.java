
package com.aimxcel.abclearn.eatingandexercise.control;

import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;


public class Activity {
    private String name;
    private double activityLevel;
    private double BMI_0;
    public static final Activity DEFAULT_ACTIVITY_LEVEL = new Activity( EatingAndExerciseResources.getString( "activity.moderate" ), 0.45, 22.5 );

    

    public static final Activity[] DEFAULT_ACTIVITY_LEVELS = {
            new Activity( EatingAndExerciseResources.getString( "activity.very-sedentary" ), 0.05, 18.5 ),
            new Activity( EatingAndExerciseResources.getString( "activity.sedentary" ), 0.15, 20 ),
            DEFAULT_ACTIVITY_LEVEL,
            new Activity( EatingAndExerciseResources.getString( "activity.active" ), 0.75, 25 ),
    };


    public Activity( String name, double activityLevel, double BMI_0 ) {
        this.name = name;
        this.activityLevel = activityLevel;
        this.BMI_0 = BMI_0;
    }

    public String toString() {
        return name;
    }

    public double getBMI_0() {
        return BMI_0;
    }

    public double getValue() {
        return activityLevel;
    }
}
