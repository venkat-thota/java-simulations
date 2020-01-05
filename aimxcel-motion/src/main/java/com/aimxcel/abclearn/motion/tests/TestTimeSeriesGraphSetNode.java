
package com.aimxcel.abclearn.motion.tests;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.motion.graphs.ControlGraph;
import com.aimxcel.abclearn.motion.graphs.ControlGraphSeries;
import com.aimxcel.abclearn.motion.graphs.GraphSetModel;
import com.aimxcel.abclearn.motion.graphs.GraphSuite;
import com.aimxcel.abclearn.motion.graphs.GraphSuiteSet;
import com.aimxcel.abclearn.motion.graphs.MinimizableControlGraph;
import com.aimxcel.abclearn.motion.graphs.MotionControlGraph;
import com.aimxcel.abclearn.motion.graphs.TimeSeriesGraphSetNode;
import com.aimxcel.abclearn.motion.model.SingleBodyMotionModel;
import com.aimxcel.abclearn.timeseries.model.TestTimeSeries;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;

public class TestTimeSeriesGraphSetNode {
    private JFrame frame;
    private TimeSeriesGraphSetNode timeSeriesGraphSetNode;
    private AimxcelPCanvas pSwingCanvas;
    private ConstantDtClock clock;

    public TestTimeSeriesGraphSetNode() {
        frame = new JFrame();
        frame.setSize( 1024, 768 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        pSwingCanvas = new AimxcelPCanvas();
        frame.setContentPane( pSwingCanvas );
        clock = new ConstantDtClock( 30, 1 );
        final TestMotionModel testMotionModel = new TestMotionModel( clock );
        timeSeriesGraphSetNode = new TimeSeriesGraphSetNode( new GraphSetModel( new TestGraphSet( pSwingCanvas, testMotionModel ).getGraphSuite( 0 ) ), new TimeSeriesModel( new TestTimeSeries.MyRecordableModel(), clock ), 0.01, 1.0 );
        pSwingCanvas.getLayer().addChild( timeSeriesGraphSetNode );
        pSwingCanvas.addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                relayout();
            }
        } );

        clock.addClockListener( new ClockAdapter() {
            public void simulationTimeChanged( ClockEvent clockEvent ) {
                testMotionModel.stepInTime( clockEvent.getSimulationTimeChange() );
            }
        } );
    }

    class TestGraphSet extends GraphSuiteSet {
        private MinimizableControlGraph positionGraph;

        public TestGraphSet( AimxcelPCanvas pSwingCanvas, final TestMotionModel motionModel ) {
            positionGraph = new MinimizableControlGraph( "x", new MotionControlGraph( pSwingCanvas, new ControlGraphSeries( motionModel.getXVariable() ), "X", "Position", -Math.PI * 3, Math.PI * 3, true, motionModel.getTimeSeriesModel(), motionModel ) );
            addGraphSuite( new GraphSuite( new MinimizableControlGraph[] { positionGraph } ) );

//            motionModel.addListener( new MotionModel.Listener() {
//                public void steppedInTime() {
//                    positionGraph.addValue( motionModel.getTime(), motionModel.getPosition() );
//                }
//            } );
            positionGraph.addControlGraphListener( new ControlGraph.Adapter() {
                public void controlFocusGrabbed() {
                    motionModel.setPositionDriven();
                }
            } );
        }
    }

    class TestMotionModel extends SingleBodyMotionModel {
        public TestMotionModel( ConstantDtClock clock ) {
            super( clock );
        }
    }

    private void relayout() {
        timeSeriesGraphSetNode.setBounds( 0, 0, pSwingCanvas.getWidth(), pSwingCanvas.getHeight() );
    }

    public static void main( String[] args ) {
        new TestTimeSeriesGraphSetNode().start();
    }

    private void start() {
        frame.setVisible( true );
        clock.start();
    }
}
