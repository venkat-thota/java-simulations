
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.eatingandexercise.model.CalorieSet;


public class FoodCalorieSet extends CalorieSet {
    public FoodCalorieSet() {
    }

    public FoodCalorieSet( CaloricFoodItem[] caloricItems ) {
        super( caloricItems );
    }

    public double getTotalProteinCalories() {
        double sum = 0;
        for ( int i = 0; i < getItemCount(); i++ ) {
            sum += ( (CaloricFoodItem) getItem( i ) ).getProteinCalories();
        }
        return sum;
    }

    public double getTotalLipidCalories() {
        double sum = 0;
        for ( int i = 0; i < getItemCount(); i++ ) {
            sum += ( (CaloricFoodItem) getItem( i ) ).getLipidCalories();
        }
        return sum;
    }

    public double getTotalCarbCalories() {
        double sum = 0;
        for ( int i = 0; i < getItemCount(); i++ ) {
            sum += ( (CaloricFoodItem) getItem( i ) ).getCarbCalories();
        }
        return sum;
    }
}
