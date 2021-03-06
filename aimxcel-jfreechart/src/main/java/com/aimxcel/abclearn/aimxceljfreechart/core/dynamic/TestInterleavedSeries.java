
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import com.aimxcel.abclearn.aimxcel2dcore.event.PDragEventHandler;
public class TestInterleavedSeries extends TestDynamicJFreeChartNode {

    public TestInterleavedSeries() {

        getDynamicJFreeChartNode().addSeries( "Series 1", Color.green );
        getDynamicJFreeChartNode().addSeries( "Series 2", Color.red );
        getDynamicJFreeChartNode().addSeries( "Series 3", Color.black );
        AimxcelPPath aimxcelPPath = new AimxcelPPath( new Rectangle( 0, 0, 100, 100 ), new BasicStroke( 2 ), Color.green );
        aimxcelPPath.addInputEventListener( new PDragEventHandler() );
        aimxcelPPath.addInputEventListener( new CursorHandler() );
        getAimxcelPCanvas().addScreenChild( aimxcelPPath );
    }

    protected void updateGraph() {
        double t = ( System.currentTimeMillis() - super.getInitialTime() ) / 1000.0;
        double frequency = 1.0 / 10.0;
        double sin = Math.sin( t * 2 * Math.PI * frequency );
        Point2D.Double pt = new Point2D.Double( t / 100.0, sin );

        getDynamicJFreeChartNode().addValue( 0, pt.getX(), pt.getY() );
        getDynamicJFreeChartNode().addValue( 1, pt.getX(), pt.getY() * 0.9 );
        getDynamicJFreeChartNode().addValue( 2, pt.getX(), pt.getY() * 0.8 );
        getDynamicJFreeChartNode().addValue( 3, pt.getX(), pt.getY() * 1.5 );
    }

    public static void main( String[] args ) {
        new TestInterleavedSeries().start();
    }
}
