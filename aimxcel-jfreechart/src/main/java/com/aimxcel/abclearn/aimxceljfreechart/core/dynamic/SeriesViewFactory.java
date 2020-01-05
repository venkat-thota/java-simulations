
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

/**
 * DISCLAIMER: This class and subclasses are under development and not ready for general use.
 * This class creates renderers for use in DynamicJFreeChartNode
 *
 * @author Sam Reid
 */
public interface SeriesViewFactory {
    SeriesView createSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData );
}