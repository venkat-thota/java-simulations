
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;


public class BufferedImmediateSeriesView extends BufferedSeriesView {

    public BufferedImmediateSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData ) {
        super( dynamicJFreeChartNode, seriesData );
    }

    protected void repaintPanel( Rectangle2D bounds ) {

        Rectangle2D dataArea = getDataArea();
        getDynamicJFreeChartNode().localToGlobal( dataArea );
        bounds = bounds.createIntersection( dataArea );
//        bounds=b;
        /*Paint immediately requires a parent component to be opaque.  Perhaps this code should be replaced with a subclass of RepaintManager?*/
        getDynamicJFreeChartNode().getAimxcelPCanvas().paintImmediately( new Rectangle( (int) bounds.getX(), (int) bounds.getY(), (int) ( bounds.getWidth() + 1 ), (int) ( bounds.getHeight() + 1 ) ) );
    }
}
