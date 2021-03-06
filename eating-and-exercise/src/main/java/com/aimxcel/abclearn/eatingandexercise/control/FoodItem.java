
package com.aimxcel.abclearn.eatingandexercise.control;

public class FoodItem implements Cloneable {
    private String image;
    private double calories;

    public FoodItem( String image, double calories ) {
        this.image = image;
        this.calories = calories;
    }

    public String getImage() {
        return image;
    }

    protected Object clone() throws CloneNotSupportedException {
        FoodItem clone = (FoodItem) super.clone();
        clone.image = image;
        clone.calories = calories;
        return clone;
    }

    public FoodItem copy() {
        try {
            return (FoodItem) clone();
        }
        catch( CloneNotSupportedException e ) {
            e.printStackTrace();
            throw new RuntimeException( e );
        }
    }

    public double getCalories() {
        return calories;
    }

    public static void main( String[] args ) {
        FoodItem a = new FoodItem( "burger", 123 );
        FoodItem b = a.copy();
        System.out.println( "a=" + a );
        System.out.println( "b = " + b );
    }
}
