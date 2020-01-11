
package com.aimxcel.abclearn.eatingandexercise.control;

import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseStrings;
import com.aimxcel.abclearn.eatingandexercise.model.EatingAndExerciseUnits;
import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class ExerciseItem extends CaloricItem {
    private double weightDependence;
    private double referenceWeightPounds;
    private double referenceCalories;
    private Human human;

    public ExerciseItem( String name, String image, double referenceCalories, double weightDependence, double referenceWeightPounds, Human human ) {
        super( name, image, referenceCalories );
        this.weightDependence = weightDependence;
        this.referenceCalories = referenceCalories;
        this.referenceWeightPounds = referenceWeightPounds;
        this.human = human;
        init();
    }

    private void init() {
        human.addListener( new Human.Adapter() {
            public void weightChanged() {
                updateCalories();
            }
        } );
        updateCalories();
    }

    private void updateCalories() {
        double newCalories = referenceCalories * ( 1 + weightDependence * ( EatingAndExerciseUnits.kgToPounds( human.getMass() ) - referenceWeightPounds ) / referenceWeightPounds );
//        System.out.println( "weightDependence = " + weightDependence + ", newcal=" + newCalories );
        setCalories( newCalories );
        //setCalories( round(newCalories) );
    }

    private static double round( double newCalories ) {
        //round to nearest 10
        return Math.round( newCalories / 10 ) * 10;
    }

    public Object clone() {
        final ExerciseItem clone = (ExerciseItem) super.clone();
        clone.weightDependence = weightDependence;
        clone.referenceCalories = referenceCalories;
        clone.referenceWeightPounds = referenceWeightPounds;
        clone.human = human;
        clone.init();
        return clone;
    }

    public String getLabelText() {
        String onehour = EatingAndExerciseResources.getString( "one.hour" );
        return "<html>" + onehour + " " + getName() + " " + "<br>(" + EatingAndExerciseStrings.KCAL_PER_DAY_FORMAT.format( getCalories() ) + " " + EatingAndExerciseStrings.KCAL_PER_DAY + ")</html>";
    }

    public static void main( String[] args ) {
        for ( double d = 0.0; d <= 20.0; d += 0.023 ) {
            System.out.println( "" + d + ": " + round( d ) );
        }
    }
}
