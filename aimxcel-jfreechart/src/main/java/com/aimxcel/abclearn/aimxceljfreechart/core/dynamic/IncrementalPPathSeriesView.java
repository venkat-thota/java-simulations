
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import com.aimxcel.abclearn.core.aimxcelcore.nodes.IncrementalPPath;

import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;


public class IncrementalPPathSeriesView extends PPathSeriesView {
    public IncrementalPPathSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData ) {
        super( dynamicJFreeChartNode, seriesData );
    }

    protected PPath createPPath() {
        return new IncrementalPPath( getDynamicJFreeChartNode().getAimxcelPCanvas() );
    }
}
