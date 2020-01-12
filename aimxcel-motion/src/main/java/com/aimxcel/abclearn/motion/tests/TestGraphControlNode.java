
package com.aimxcel.abclearn.motion.tests;


import java.awt.Color;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.BufferedAimxcelPCanvas;
import com.aimxcel.abclearn.motion.graphs.ControlGraphSeries;
import com.aimxcel.abclearn.motion.graphs.GraphTimeControlNode;
import com.aimxcel.abclearn.motion.model.DefaultTemporalVariable;
import com.aimxcel.abclearn.timeseries.model.TestTimeSeries;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;

public class TestGraphControlNode {
    private JFrame frame;
    private ConstantDtClock swingClock;

    public TestGraphControlNode() {
        frame = new JFrame();
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        AimxcelPCanvas aimxcelPCanvas = new BufferedAimxcelPCanvas();
        swingClock = new ConstantDtClock( 30, 1.0 );
        TimeSeriesModel timeSeriesModel = new TimeSeriesModel( new TestTimeSeries.MyRecordableModel(), swingClock );

        swingClock.addClockListener( timeSeriesModel );
        GraphTimeControlNode graphTimeControlNode = new GraphTimeControlNode( timeSeriesModel );
        graphTimeControlNode.addVariable( new ControlGraphSeries( "title", Color.blue, "abbr", "units", null, new DefaultTemporalVariable() ) );
        aimxcelPCanvas.addScreenChild( graphTimeControlNode );

        frame.setContentPane( aimxcelPCanvas );
    }

    public static void main( String[] args ) {
        new TestGraphControlNode().start();
    }

    private void start() {
        frame.setVisible( true );
        swingClock.start();
    }
}
