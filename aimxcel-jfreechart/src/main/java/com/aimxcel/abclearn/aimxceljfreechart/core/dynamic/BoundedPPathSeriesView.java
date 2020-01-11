
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.IncrementalPPath;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;


public class BoundedPPathSeriesView extends PPathSeriesView {
    public BoundedPPathSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData ) {
        super( dynamicJFreeChartNode, seriesData );
    }

    protected PPath createPPath() {
        return new IncrementalPPath( getDynamicJFreeChartNode().getAimxcelPCanvas() ) {
            protected void globalBoundsChanged( Rectangle2D bounds ) {
                Rectangle2D dataArea = getDataArea();
                getDynamicJFreeChartNode().localToGlobal( dataArea );
                Rectangle2D b = bounds.createIntersection( dataArea );
                super.globalBoundsChanged( b );
            }
        };
    }
}
