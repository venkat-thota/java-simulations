
package com.aimxcel.abclearn.aimxceljfreechart.test;

import java.awt.geom.Point2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.aimxcel.abclearn.aimxceljfreechart.core.JFreeChartNode;


public class TestJFreeChartNodeBuffer {
    public static void main( String[] args ) {
        XYSeries series = new XYSeries( "a" );
        series.add( 0, 0 );
        series.add( 10, 10 );
        XYDataset dataset = new XYSeriesCollection( series );
        JFreeChart chart = ChartFactory.createXYLineChart( "title", "x", "y", dataset, PlotOrientation.VERTICAL, false, true, true );
        JFreeChartNode jFreeChartNode = new JFreeChartNode( chart );
        jFreeChartNode.setBounds( 0, 0, 100, 100 );
        jFreeChartNode.setBuffered( false );
        System.out.println( "buffered=false" );
        System.out.println( "jFreeChartNode.plotToNode( new Point2D.Double( 0, 0 ) )=" + jFreeChartNode.plotToNode( new Point2D.Double( 0, 0 ) ) );
        System.out.println( "jFreeChartNode.plotToNode( new Point2D.Double( 5, 5 ) )=" + jFreeChartNode.plotToNode( new Point2D.Double( 5, 5 ) ) );
        System.out.println( "jFreeChartNode.getDataArea( ) = " + jFreeChartNode.getDataArea() );
        jFreeChartNode.setBuffered( true );
        System.out.println( "buffered=true" );
        System.out.println( "jFreeChartNode.plotToNode( new Point2D.Double( 0, 0 ) )=" + jFreeChartNode.plotToNode( new Point2D.Double( 0, 0 ) ) );
        System.out.println( "jFreeChartNode.plotToNode( new Point2D.Double( 5, 5 ) )=" + jFreeChartNode.plotToNode( new Point2D.Double( 5, 5 ) ) );
        System.out.println( "jFreeChartNode.getDataArea( ) = " + jFreeChartNode.getDataArea() );

    }
}
