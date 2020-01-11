
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;


public class Exercise {
    private String name;
    private double calories;

    public Exercise( String name, double caloriesBurned ) {
        this.name = name;
        this.calories = caloriesBurned;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public String toString() {
        String calPerDay = EatingAndExerciseResources.getString( "units.cal-per-day" );
        return name + ": " + calories + " " + calPerDay;
    }
}
