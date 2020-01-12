
package com.aimxcel.abclearn.glaciers.util;

public class UnitsConverter {

    public static final double metersToFeet( double meters ) {
        return meters * 3.2808399;
    }
    
    public static final double feetToMeters( double feet ) {
        return feet / 3.2808399;
    }
    
    public static double celsiusToFahrenheit( double celsius ) {
        return ( (9./5.) * celsius ) + 32.;
    }
    
    public static double fahrenheitToCelsius( double fahrenheit ) {
        return ( 5./9.) * ( fahrenheit - 32. );
    }
}
