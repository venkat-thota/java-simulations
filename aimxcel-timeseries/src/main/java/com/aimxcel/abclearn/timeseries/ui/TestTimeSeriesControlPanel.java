
package com.aimxcel.abclearn.timeseries.ui;


import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.timeseries.model.TestTimeSeries;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;

public class TestTimeSeriesControlPanel {
    private JFrame frame;
    private ConstantDtClock clock = new ConstantDtClock( 30, 1 );

    public TestTimeSeriesControlPanel() {
        frame = new JFrame();
        frame.setSize( 800, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        TimeSeriesModel timeSeriesModel = new TimeSeriesModel( new TestTimeSeries.MyRecordableModel(), clock );
        frame.setContentPane( new TimeSeriesControlPanel( timeSeriesModel, 0.1, 2.0 ) );
        clock.addClockListener( timeSeriesModel );
    }

    public static void main( String[] args ) {
        new TestTimeSeriesControlPanel().start();
    }

    private void start() {
        frame.setVisible( true );
        clock.start();
    }
}
