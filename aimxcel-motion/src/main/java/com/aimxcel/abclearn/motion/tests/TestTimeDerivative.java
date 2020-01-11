
package com.aimxcel.abclearn.motion.tests;

import junit.framework.TestCase;

import java.util.ArrayList;

import com.aimxcel.abclearn.motion.MotionMath;
import com.aimxcel.abclearn.motion.model.TimeData;



public class TestTimeDerivative extends TestCase {
    public void testDerivative() {
        double slope = MotionMath.estimateDerivative( getTestData() );
        assertEquals( slope, 2.0, 1E-7 );
    }

   
    public static void main( String[] args ) {
        double slope = MotionMath.estimateDerivative( getTestData() );
        System.out.println( "slope = " + slope );
    }

    private static TimeData[] getTestData() {
        double[] x = new double[] { 0, 1, 2, 3, 4 };
        double[] y = new double[] { 1, 3, 5, 7, 9 };

        TimeData[] timeData = new TimeData[x.length];
        for ( int i = 0; i < timeData.length; i++ ) {
            timeData[i] = new TimeData( y[i], x[i] );
        }
        return timeData;
    }

    public void testSecondDerivative() {
        ArrayList data = new ArrayList();
        for ( int i = 0; i < 100; i++ ) {
            double x = i / 100.0;
            TimeData timeData = new TimeData( x * x, x );
            data.add( timeData );
        }
        for ( int i = 10; i < data.size(); i++ ) {
            TimeData derivative = MotionMath.getSecondDerivative( (TimeData[]) data.subList( i - 10, i ).toArray( new TimeData[0] ) );
            assertEquals( 2.0, derivative.getValue(), 1E-6 );
        }
    }
}
