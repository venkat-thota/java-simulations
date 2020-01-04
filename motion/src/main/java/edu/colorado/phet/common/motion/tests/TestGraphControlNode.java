
package edu.colorado.phet.common.motion.tests;

/**
 * User: Sam Reid
 * Date: Dec 28, 2006
 * Time: 10:38:40 PM
 *
 */

import java.awt.Color;

import javax.swing.JFrame;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.BufferedAimxcelPCanvas;

import edu.colorado.phet.common.motion.graphs.ControlGraphSeries;
import edu.colorado.phet.common.motion.graphs.GraphTimeControlNode;
import edu.colorado.phet.common.motion.model.DefaultTemporalVariable;
import edu.colorado.phet.common.timeseries.model.TestTimeSeries;
import edu.colorado.phet.common.timeseries.model.TimeSeriesModel;

public class TestGraphControlNode {
    private JFrame frame;
    private ConstantDtClock swingClock;

    public TestGraphControlNode() {
        frame = new JFrame();
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        AimxcelPCanvas phetPCanvas = new BufferedAimxcelPCanvas();
        swingClock = new ConstantDtClock( 30, 1.0 );
        TimeSeriesModel timeSeriesModel = new TimeSeriesModel( new TestTimeSeries.MyRecordableModel(), swingClock );

        swingClock.addClockListener( timeSeriesModel );
        GraphTimeControlNode graphTimeControlNode = new GraphTimeControlNode( timeSeriesModel );
        graphTimeControlNode.addVariable( new ControlGraphSeries( "title", Color.blue, "abbr", "units", null, new DefaultTemporalVariable() ) );
        phetPCanvas.addScreenChild( graphTimeControlNode );

        frame.setContentPane( phetPCanvas );
    }

    public static void main( String[] args ) {
        new TestGraphControlNode().start();
    }

    private void start() {
        frame.setVisible( true );
        swingClock.start();
    }
}
