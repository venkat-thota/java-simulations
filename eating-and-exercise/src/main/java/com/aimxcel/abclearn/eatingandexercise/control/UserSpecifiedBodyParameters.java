
package com.aimxcel.abclearn.eatingandexercise.control;

import com.aimxcel.abclearn.eatingandexercise.model.Human;


public class UserSpecifiedBodyParameters {

   
    public double getAutoFatMassPercent( Human human ) {
           return 1.4 * human.getBMI() - 8.0 * ( human.getGender() == Human.Gender.MALE ? 1 : 0 ) - 9.0;
    }

    public static void main( String[] args ) {
        Human human = new Human();
        double pref = new UserSpecifiedBodyParameters().getAutoFatMassPercent( human );
        System.out.println( "pref = " + pref );
    }
}
