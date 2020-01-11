
package com.aimxcel.abclearn.motion.charts;

import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;


public class MultiControlChart extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<MinimizableControlChart> children = new ArrayList<MinimizableControlChart>();
    private double width;
    private double height;

    public MultiControlChart( final MinimizableControlChart[] charts ) {
        for ( MinimizableControlChart chart : charts ) {
            children.add( chart );
            addChild( chart );
            chart.getMaximized().addObserver( new SimpleObserver() {
                public void update() {
                    updateLayout();
                }
            } );
        }

        //Only show the bottom-most horizontal zoom control because it controls all axes and cleans up the screen to omit the extraneous controls
        //The bottom one is used since that is the graph with the visible domain axis labels
        final SimpleObserver updateHorizontalZoomButtonVisibility = new SimpleObserver() {
            public void update() {
                for ( int i = charts.length - 1; i >= 0; i-- ) {
                    if ( charts[i].getMaximized().get() ) {
                        setHorizontalZoomButtonVisible( i );
                        break;
                    }
                }
            }
        };
        for ( MinimizableControlChart chart : charts ) {
            chart.getMaximized().addObserver( updateHorizontalZoomButtonVisibility );
        }
        updateHorizontalZoomButtonVisibility.update();

        //Only show the domain axis labels for the bottom chart
        final SimpleObserver updateRangeLabelsVisible = new SimpleObserver() {
            public void update() {
                for ( int i = charts.length - 1; i >= 0; i-- ) {
                    if ( charts[i].getMaximized().get() ) {
                        setDomainAxisLabelsVisible( i );
                        break;
                    }
                }
            }
        };
        for ( MinimizableControlChart chart : charts ) {
            chart.getMaximized().addObserver( updateRangeLabelsVisible );
        }
        updateRangeLabelsVisible.update();

        //Synchronize the chart bounds
        for ( final MinimizableControlChart chart : charts ) {
            chart.getChartNode().getDataModelBounds().addObserver( new SimpleObserver() {
                public void update() {
                    for ( MinimizableControlChart chart2 : charts ) {
                        chart2.getChartNode().getDataModelBounds().setHorizontalRange( chart.getChartNode().getDataModelBounds().getMinX(), chart.getChartNode().getDataModelBounds().getMaxX() );
                    }
                }
            } );
        }
    }

    /**
     * Show the domain axis labels for the specified chart, hiding them from all other charts.
     *
     * @param index the index of the chart that should have the visible range axis labels
     */
    private void setDomainAxisLabelsVisible( int index ) {
        for ( int i = 0; i < children.size(); i++ ) {
            children.get( i ).setDomainAxisLabelsVisible( i == index );
        }
    }

    private void updateLayout() {
        new ControlChartLayout.AlignedLayout().updateLayout( children.toArray( new MinimizableControlChart[0] ), width, height );
    }

    private void setHorizontalZoomButtonVisible( int index ) {
        for ( int i = 0; i < children.size(); i++ ) {
            children.get( i ).setHorizontalZoomVisible( index == i );
        }
    }

    public void setSize( double width, double height ) {
        this.width = width;
        this.height = height;
        updateLayout();
    }

    public boolean setBounds( double x, double y, double width, double height ) {
        setSize( width, height );
        setOffset( x, y );
        return true;
    }

    /**
     * Removes data from the charts and restores the zoom levels and other settings to their default vaules.
     */
    public void resetAll() {
        for ( MinimizableControlChart child : children ) {
            child.resetAll();
        }
    }

    /**
     * Removes the data from the charts.
     */
    public void clear() {
        for ( MinimizableControlChart child : children ) {
            child.clear();
        }
    }
}