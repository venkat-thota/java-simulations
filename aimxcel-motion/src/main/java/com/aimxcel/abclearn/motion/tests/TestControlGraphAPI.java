
package com.aimxcel.abclearn.motion.tests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.motion.graphs.ControlGraph;
import com.aimxcel.abclearn.motion.model.DefaultTemporalVariable;
import com.aimxcel.abclearn.motion.model.ITemporalVariable;
import com.aimxcel.abclearn.timeseries.model.RecordableModel;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;

public class TestControlGraphAPI {
    public static void main( String[] args ) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        AimxcelPCanvas contentPane = new AimxcelPCanvas();

        final ITemporalVariable variable = new DefaultTemporalVariable();
        RecordableModel recordableModel = new TestRecordableModel();
        ConstantDtClock clock = new ConstantDtClock( 1, 1.0 );
        TimeSeriesModel timeSeriesModel = new TimeSeriesModel( recordableModel, clock );
        ControlGraph controlGraph = new ControlGraph( contentPane, variable, "title", 0, 10, timeSeriesModel );
        contentPane.addScreenChild( controlGraph );

        controlGraph.setBounds( 0, 0, 600, 400 );

        frame.setContentPane( contentPane );

        frame.setSize( 800, 600 );
        frame.setVisible( true );

        final double freq = 1 / 10.0;
        Timer timer = new Timer( 30, new ActionListener() {
            public void actionPerformed( ActionEvent actionEvent ) {
                variable.setValue( Math.sin( System.currentTimeMillis() / 1000.0 * freq ) );
            }
        } );
        timer.start();
    }

    private static class TestRecordableModel implements RecordableModel {

        public void stepInTime( double simulationTimeChange ) {
        }

        public Object getState() {
            return null;
        }

        public void setState( Object o ) {
        }

        public void resetTime() {
        }

        public void clear() {
        }
    }
}
