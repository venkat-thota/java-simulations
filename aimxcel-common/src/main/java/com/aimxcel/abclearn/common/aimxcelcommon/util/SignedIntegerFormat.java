
package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelResources;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SignedIntegerFormat;


public class SignedIntegerFormat extends DecimalFormat {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final NumberFormat decimalFormat;

    public SignedIntegerFormat() {
        this.decimalFormat = ( DecimalFormat.getNumberInstance( AimxcelResources.readLocale() ) );
        if ( decimalFormat instanceof DecimalFormat ) {
            ( (DecimalFormat) decimalFormat ).applyPattern( "0" );
        }
    }

    @Override
    public StringBuffer format( long number, StringBuffer result, FieldPosition fieldPosition ) {
        fieldPosition.setBeginIndex( 0 );
        fieldPosition.setEndIndex( 0 );

        //Add a '+' for positive numbers
        if ( number > 0 ) {
            return new StringBuffer( "+" + number );
        }
        else {
            return new StringBuffer( "" + number );
        }
    }

    @Override
    public Number parse( String text, ParsePosition pos ) {
        //Strip out '+' if the user had typed it in
        if ( text.startsWith( "+" ) ) {
            return new DecimalFormat( "0" ).parse( text.substring( 1 ), pos );
        }
        else {
            return new DecimalFormat( "0" ).parse( text, pos );
        }
    }

    public static void main( String[] args ) {
        for ( int i = -4; i < 10; i++ ) {
            System.out.println( "new SignedIntegerFormat().format(" + i + ") = " + new SignedIntegerFormat().format( i ) );
        }
    }
}
