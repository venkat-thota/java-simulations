

package com.aimxcel.abclearn.aimxceljfreechart.test;

import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;

import com.aimxcel.abclearn.aimxceljfreechart.AimxcelHistogramDataset;
import com.aimxcel.abclearn.aimxceljfreechart.AimxcelHistogramSeries;


public class TestAimxcelHistogram extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestAimxcelHistogram() {
        super();

        // Series 1
        double[] values = new double[1000];
        Random generator = new Random( 12345678L );
        for ( int i = 0; i < 1000; i++ ) {
            values[i] = generator.nextGaussian() + 5;
        }
        AimxcelHistogramSeries series1 = new AimxcelHistogramSeries( "series1", 2.0, 8.0, 100, values );

        // Series 2
        values = new double[1000];
        for ( int i = 0; i < 1000; i++ ) {
            values[i] = generator.nextGaussian() + 7;
        }
        AimxcelHistogramSeries series2 = new AimxcelHistogramSeries( "series2", 4.0, 10.0, 100, values );

        // Dataset
        AimxcelHistogramDataset dataset = new AimxcelHistogramDataset();
        dataset.addSeries( series1 );
        dataset.addSeries( series2 );

        // Plot
        XYPlot plot = new XYPlot();
        plot.setDomainAxis( new NumberAxis( "position (meters)" ) );
        plot.setRangeAxis( new NumberAxis( "frequency" ) );
        plot.setDataset( dataset );

        // Renderer
        XYBarRenderer renderer = new XYBarRenderer();
        plot.setRenderer( renderer );

        // Chart
        JFreeChart chart = new JFreeChart( "AimxcelHistogram Test", plot );

        // Panel
        JPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 500, 270 ) );
        setContentPane( chartPanel );
    }

    public static void main( String[] args ) throws IOException {
        TestAimxcelHistogram testFrame = new TestAimxcelHistogram();
        testFrame.pack();
        testFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        testFrame.setVisible( true );
    }
}
