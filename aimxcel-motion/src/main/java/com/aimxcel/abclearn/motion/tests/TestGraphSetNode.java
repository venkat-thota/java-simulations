
package com.aimxcel.abclearn.motion.tests;


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.BufferedAimxcelPCanvas;
import com.aimxcel.abclearn.motion.graphs.ControlGraph;
import com.aimxcel.abclearn.motion.graphs.ControlGraphSeries;
import com.aimxcel.abclearn.motion.graphs.GraphSetModel;
import com.aimxcel.abclearn.motion.graphs.GraphSetNode;
import com.aimxcel.abclearn.motion.graphs.GraphSuite;
import com.aimxcel.abclearn.motion.graphs.MinimizableControlGraph;
import com.aimxcel.abclearn.motion.model.DefaultTemporalVariable;
import com.aimxcel.abclearn.timeseries.model.TestTimeSeries;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;

public class TestGraphSetNode {
    private JFrame frame = new JFrame( getClass().getName().substring( getClass().getName().lastIndexOf( '.' ) + 1 ) );
    private AimxcelPCanvas aimxcelPCanvas;
    private GraphSetNode graphSetNode;

    public TestGraphSetNode() {
        aimxcelPCanvas = new BufferedAimxcelPCanvas();


        frame.setContentPane( aimxcelPCanvas );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 800, 600 );

        TimeSeriesModel timeSeriesModel = new TimeSeriesModel( new TestTimeSeries.MyRecordableModel(), new ConstantDtClock( 30, 1 ) );
        MinimizableControlGraph minimizableControlGraphA = new MinimizableControlGraph( "labelA", new ControlGraph(
                aimxcelPCanvas, new ControlGraphSeries( new DefaultTemporalVariable() ), "titleA", 0, 10, timeSeriesModel ) );
        MinimizableControlGraph minimizableControlGraphB = new MinimizableControlGraph( "Long labelB", new ControlGraph(
                aimxcelPCanvas, new ControlGraphSeries( new DefaultTemporalVariable() ), "Long titleB", 0, 10, timeSeriesModel ) );


        graphSetNode = new GraphSetNode( new GraphSetModel( new GraphSuite( new MinimizableControlGraph[] { minimizableControlGraphA, minimizableControlGraphB } ) ) );
        graphSetNode.setAlignedLayout();
        aimxcelPCanvas.addScreenChild( graphSetNode );

        aimxcelPCanvas.addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                relayout();
            }
        } );
        relayout();
    }

    private void relayout() {
        graphSetNode.setBounds( 0, 0, aimxcelPCanvas.getWidth(), aimxcelPCanvas.getHeight() );
    }

    private void start() {
        frame.setVisible( true );
    }

    public static void main( String[] args ) {
        new TestGraphSetNode().start();
    }
}
