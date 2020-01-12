
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;

import com.aimxcel.abclearn.aimxcel2dcore.util.PDebug;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class TestDynamicJFreeChartNode {
    private JFrame frame;
    private Timer timer;
    private double t0 = System.currentTimeMillis();
    private AimxcelPCanvas aimxcelPCanvas;
    private DynamicJFreeChartNode dynamicJFreeChartNode;
    private PSwing pSwing;

    public TestDynamicJFreeChartNode() {
        frame = new JFrame();
        frame.setSize( 1280, 768 - 100 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        aimxcelPCanvas = new AimxcelPCanvas();
        frame.setContentPane( aimxcelPCanvas );

        JFreeChart chart = ChartFactory.createXYLineChart( "title", "x", "y", new XYSeriesCollection(), PlotOrientation.VERTICAL, false, false, false );
        dynamicJFreeChartNode = new DynamicJFreeChartNode( aimxcelPCanvas, chart );
        dynamicJFreeChartNode.addSeries( "sine", Color.blue );
        chart.getXYPlot().getRangeAxis().setAutoRange( false );
        chart.getXYPlot().getRangeAxis().setRange( -1, 1 );
        chart.getXYPlot().getDomainAxis().setAutoRange( false );
        chart.getXYPlot().getDomainAxis().setRange( 0, 1 );

        aimxcelPCanvas.addScreenChild( dynamicJFreeChartNode );

        JPanel controlPanel = new JPanel();
        JPanel dynamicJFreeChartNodeControlPanel = new DynamicJFreeChartNodeControlPanel( dynamicJFreeChartNode );
        JButton clear = new JButton( "Clear" );
        clear.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                clear();
            }
        } );
        controlPanel.add( dynamicJFreeChartNodeControlPanel );
        controlPanel.add( clear );
//        panel.add( clear );
        pSwing = new PSwing( controlPanel );
        aimxcelPCanvas.addScreenChild( pSwing );

        timer = new Timer( 30, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                updateGraph();
            }
        } );
        aimxcelPCanvas.addKeyListener( new KeyAdapter() {
            public void keyPressed( KeyEvent e ) {
                if ( e.getKeyCode() == KeyEvent.VK_F1 && e.isAltDown() ) {
                    PDebug.debugRegionManagement = !PDebug.debugRegionManagement;
                }
            }
        } );
        aimxcelPCanvas.addMouseListener( new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                aimxcelPCanvas.requestFocus();
            }
        } );
        aimxcelPCanvas.addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                relayout();
            }
        } );
        relayout();
    }

    protected void updateGraph() {
//        double t = ( System.currentTimeMillis() - t0 ) / 1000.0;
        double t = ( System.currentTimeMillis() - t0 ) / 500.0;
        double frequency = 1.0 / 10.0;
        double y = Math.sin( t * 2 * Math.PI * frequency );
//        double y = 0;
        Point2D.Double pt = new Point2D.Double( t / 100.0, y );
        dynamicJFreeChartNode.addValue( pt.getX(), pt.getY() );

//        dynamicJFreeChartNode.updateChartRenderingInfo();
//        System.out.println( "dynamicJFreeChartNode.getDataArea( ) = " + dynamicJFreeChartNode.getDataArea() );
    }

    public DynamicJFreeChartNode getDynamicJFreeChartNode() {
        return dynamicJFreeChartNode;
    }

    public AimxcelPCanvas getAimxcelPCanvas() {
        return aimxcelPCanvas;
    }

    public PSwing getPSwing() {
        return pSwing;
    }

    private void clear() {
        dynamicJFreeChartNode.clear();
        t0 = System.currentTimeMillis();
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

    public double getInitialTime() {
        return t0;
    }

    public static void main( String[] args ) {
        new TestDynamicJFreeChartNode().start();
    }
}
