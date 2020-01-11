
package com.aimxcel.abclearn.eatingandexercise.util;

import java.text.*;
import java.util.StringTokenizer;

import com.aimxcel.abclearn.eatingandexercise.model.EatingAndExerciseUnits;


public class FeetInchesFormat extends NumberFormat {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DecimalFormat format = new DecimalFormat( "0" );

    public Number parse( String source, ParsePosition parsePosition ) {
        StringTokenizer st = new StringTokenizer( source.substring( parsePosition.getIndex() ), "'\" /" );
        parsePosition.setIndex( source.length() );
        if ( st.countTokens() == 0 ) {
            return new Long( 0 );
        }
        else if ( st.countTokens() == 1 ) {
            try {
                return format.parse( st.nextToken() );
            }
            catch( ParseException e ) {
                e.printStackTrace();
            }
        }
        else {
            try {
                double feet = format.parse( st.nextToken() ).doubleValue();
                double inches = format.parse( st.nextToken() ).doubleValue();
                return new Double( feet + EatingAndExerciseUnits.inchesToFeet( inches ) );
            }
            catch( ParseException e ) {
                e.printStackTrace();
            }
        }
        return new Long( 0 );
    }

    public StringBuffer format( double number, StringBuffer toAppendTo, FieldPosition pos ) {
        double totalFeet = number;
        double feetDecimal = number - (int) number;
        double feet = totalFeet - feetDecimal;
        double inches = EatingAndExerciseUnits.feetToInches( feetDecimal );

        String inchText = format.format( inches );
        if ( inchText.equals( "12" ) ) {
            inchText = "0";
            feet++;
        }
        toAppendTo.append( "" + format.format( feet ) + "' " + inchText + "\"" );
        return toAppendTo;
    }

    public StringBuffer format( long number, StringBuffer toAppendTo, FieldPosition pos ) {
        return format( (double) number, toAppendTo, pos );
    }
}
