
package com.aimxcel.abclearn.aimxceljfreechart.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

import com.aimxcel.abclearn.aimxceljfreechart.core.JFreeChartNode;
import com.aimxcel.abclearn.aimxceljfreechart.core.dynamic.DynamicJFreeChartNode;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

public class TestDataAreaChange {
    private JFrame frame;
    private Timer timer;
    private AimxcelPCanvas aimxcelPCanvas;
    private JFreeChartNode dynamicJFreeChartNode;
    private PSwing pSwing;

    public TestDataAreaChange() {
        frame = new JFrame();
        frame.setSize( 1280, 768 - 100 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        aimxcelPCanvas = new AimxcelPCanvas();
        frame.setContentPane( aimxcelPCanvas );

        JFreeChart chart = ChartFactory.createXYLineChart( "title", "x", "y", new XYSeriesCollection(), PlotOrientation.VERTICAL, false, false, false );
//        dynamicJFreeChartNode = new DynamicJFreeChartNode2( aimxcelPCanvas, chart );
        dynamicJFreeChartNode = new DynamicJFreeChartNode( aimxcelPCanvas, chart );
//        dynamicJFreeChartNode = new JFreeChartNode( chart);

        chart.getXYPlot().getRangeAxis().setAutoRange( false );
        chart.getXYPlot().getRangeAxis().setRange( -1, 1 );
        chart.getXYPlot().getDomainAxis().setAutoRange( false );
        chart.getXYPlot().getDomainAxis().setRange( 0, 1 );

        aimxcelPCanvas.addScreenChild( dynamicJFreeChartNode );

        JPanel panel = new JPanel();

        final JCheckBox jCheckBox = new JCheckBox( "buffered", dynamicJFreeChartNode.isBuffered() );
        jCheckBox.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                dynamicJFreeChartNode.setBuffered( jCheckBox.isSelected() );
            }
        } );
        dynamicJFreeChartNode.addBufferedPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
                jCheckBox.setSelected( dynamicJFreeChartNode.isBuffered() );
            }
        } );
        panel.add( jCheckBox );
        pSwing = new PSwing( panel );
        aimxcelPCanvas.addScreenChild( pSwing );

        timer = new Timer( 30, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.out.println( "dynamicJFreeChartNode.getDataArea( ) = " + dynamicJFreeChartNode.getDataArea() );
            }
        } );
        aimxcelPCanvas.addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                relayout();
            }
        } );
        relayout();
    }

    protected void relayout() {
        pSwing.setOffset( 0, 0 );
        dynamicJFreeChartNode.setBounds( 0, pSwing.getFullBounds().getHeight(), aimxcelPCanvas.getWidth(), aimxcelPCanvas.getHeight() - pSwing.getFullBounds().getHeight() );
    }

    public void start() {
        frame.setVisible( true );
        timer.start();
        aimxcelPCanvas.requestFocus();
    }

    public static void main( String[] args ) {
        new TestDataAreaChange().start();
    }
}
