
package com.aimxcel.abclearn.timeseries.model;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.timeseries.ui.TimeSeriesControlPanel;

/**
 * Author: Sam Reid
 * May 15, 2007, 9:27:10 PM
 */
public class TestTimeSeries {
    private JFrame frame;

    public TestTimeSeries() {
        frame = new JFrame();
        frame.setSize( 800, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        RecordableModel recordableModel = new MyRecordableModel();
        ConstantDtClock clock = new ConstantDtClock( 30, 1.0 );
        TimeSeriesModel timeSeriesModel = new TimeSeriesModel( recordableModel, clock );
        frame.setContentPane( new TimeSeriesControlPanel( timeSeriesModel, 0.01, 2.0 ) );

        clock.addClockListener( timeSeriesModel );
        clock.start();
    }

    public static void main( String[] args ) {
        new TestTimeSeries().start();
    }

    private void start() {
        frame.setVisible( true );
    }

    public static class MyRecordableModel implements RecordableModel {
        double time = 0;

        public void stepInTime( double simulationTimeChange ) {
            time += simulationTimeChange;
            System.out.println( "TestTimeSeries.step: time=" + time );
        }

        public Object getState() {
            System.out.println( "TestTimeSeries.getState: time=" + time );
            return new Double( time );
        }

        public void setState( Object o ) {
            this.time = ( (Double) o ).doubleValue();
            System.out.println( "TestTimeSeries.setState: time=" + time );
        }

        public void resetTime() {
            time = 0;
            System.out.println( "reset time = " + time );
        }

        public void clear() {
            resetTime();
        }
    }
}
