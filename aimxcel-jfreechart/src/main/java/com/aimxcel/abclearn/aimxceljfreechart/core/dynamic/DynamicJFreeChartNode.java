
package com.aimxcel.abclearn.aimxceljfreechart.core.dynamic;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.jfree.chart.JFreeChart;

import com.aimxcel.abclearn.aimxceljfreechart.core.JFreeChartNode;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;


public class DynamicJFreeChartNode extends JFreeChartNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<SeriesData> seriesDataList = new ArrayList<SeriesData>();
    private ArrayList<SeriesView> seriesViewList = new ArrayList<SeriesView>();
    private AimxcelPCanvas aimxcelPCanvas;
//    private AimxcelPPath debugBufferRegion;//internal debugging tool for deciphering screen output regions

    //The default SeriesView is JFreeChart rendering.
    private SeriesViewFactory viewFactory = RENDERER_JFREECHART;
    private boolean autoUpdateAll = true;//require user to force repaints to reduce redundant calls

    public DynamicJFreeChartNode( AimxcelPCanvas aimxcelPCanvas, JFreeChart chart ) {
        super( chart );
        this.aimxcelPCanvas = aimxcelPCanvas;
//        debugBufferRegion = new AimxcelPPath( new BasicStroke( 1.0f ), Color.green );
//        addChild( debugBufferRegion );//this can destroy the bounds of the graph, use with care
    }

    /**
     * Sets whether updateAll should have the normal effect or be a no-op.  This should only be set to false in the case of performance issues due to redundant
     * work or calls to updateAll.
     *
     * @param autoUpdateAll false if the client application will manually call forceUpdateAll
     */
    public void setAutoUpdateAll( boolean autoUpdateAll ) {
        this.autoUpdateAll = autoUpdateAll;
    }

    public void forceUpdateAll() {
        super.updateAll();
        for ( SeriesView seriesView : seriesViewList ) {
            seriesView.forceRepaintAll();
        }
    }

    public void updateAll() {
        if ( autoUpdateAll ) {
            super.updateAll();
        }
    }

    /*
     * This update overriden here doesn't seem to do anything in the Rotation sim except to extend startup time by about 25%
     * todo: investigate this issue
     */
    protected void addPNodeUpdateHandler() {
        addPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
//                updateAll();
            }
        } );
    }

    /**
     * Adds the specified (x,y) pair to the 0th series in this plot.
     *
     * @param x the x-value to add
     * @param y the y-value to add
     */
    public void addValue( double x, double y ) {
        addValue( 0, x, y );
    }

    /**
     * Adds the specified (x,y) pair to the specified series.
     *
     * @param series the series to which data should be added.  This series should have already been added to this dynamic jfreechartnode with addSeries().
     * @param x      the x-value to add
     * @param y      the y-value to add
     */
    public void addValue( int series, double x, double y ) {
        getSeries( series ).addValue( x, y );
    }

    public void clearSeries( int series ) {
        getSeries( series ).clear();
    }

    public void addSeries( String title, Color color ) {
        addSeries( title, color, BufferedSeriesView.DEFAULT_STROKE );
    }

    /**
     * Adds a new series to this chart for plotting, with the given name and color.
     *
     * @param title  the title for the series
     * @param color  the series' color
     * @param stroke
     */
    public SeriesData addSeries( String title, Color color, Stroke stroke ) {
        SeriesData seriesData = new SeriesData( title, color, stroke );
        seriesDataList.add( seriesData );
        updateSeriesViews();
        return seriesData;
    }

    public void removeSeries( String title ) {
        seriesDataList.remove( getSeriesData( title ) );
        updateSeriesViews();
    }

    /**
     * Looks up the series data object for the specified title; returns the first found, or null if none found.
     *
     * @param title
     * @return the first found, or null if none found.
     */
    private SeriesData getSeriesData( String title ) {
        for ( SeriesData seriesData : seriesDataList ) {
            if ( seriesData.getTitle().equals( title ) ) {
                return seriesData;
            }
        }
        return null;
    }

    /**
     * Empties each series associated with this chart.
     */
    public void clear() {
        for ( SeriesData seriesData : seriesDataList ) {
            seriesData.clear();
        }
        updateAll();
    }

    //todo: this is public merely for purposes of debugging
    public SeriesData getSeries( int series ) {
        return seriesDataList.get( series );
    }

    /**
     * Sets the rendering strategy to JFreeChart style.
     */
    public void setJFreeChartSeries() {
        setViewFactory( RENDERER_JFREECHART );
    }

    public SeriesViewFactory getViewFactory() {
        return viewFactory;
    }

    /**
     * Sets the rendering strategy to Core style.
     */
    public void setCoreSeries() {
        setViewFactory( RENDERER_PICCOLO );
    }

    /**
     * Sets the rendering strategy to Buffered style.
     */
    public void setBufferedSeries() {
        setViewFactory( RENDERER_BUFFERED );
    }

    /**
     * Sets the rendering strategy to Buffered Immediate style.
     */
    public void setBufferedImmediateSeries() {
        setViewFactory( RENDERER_BUFFERED_IMMEDIATE );
    }

    /**
     * Sets an arbitrary (possibly user-defined) view style.
     *
     * @param factory
     */
    public void setViewFactory( SeriesViewFactory factory ) {
        viewFactory = factory;
        updateSeriesViews();
        forceUpdateAll();
    }

    private void updateSeriesViews() {
        removeAllSeriesViews();
        updateAll();
        addAllSeriesViews();
        updateChartRenderingInfo();
    }

    private void addAllSeriesViews() {
        for ( SeriesData seriesData : seriesDataList ) {
            SeriesView seriesDataView = viewFactory.createSeriesView( this, seriesData );
            if ( seriesData.isVisible() ) {//todo: visibility should probably be handled in renderer subclasses
                seriesDataView.install();
                seriesViewList.add( seriesDataView );
            }
        }
    }

    private void removeAllSeriesViews() {
        while ( seriesViewList.size() > 0 ) {
            SeriesView seriesView = seriesViewList.get( 0 );
            seriesView.uninstall();
            seriesViewList.remove( seriesView );
        }
    }

    public void setBuffered( boolean buffered ) {
        super.setBuffered( buffered );
        updateSeriesViews();
    }

    public AimxcelPCanvas getAimxcelPCanvas() {
        return aimxcelPCanvas;
    }

    public void addBufferedImagePropertyChangeListener( PropertyChangeListener listener ) {
        super.addBufferedImagePropertyChangeListener( listener );
    }

    public void removeBufferedImagePropertyChangeListener( PropertyChangeListener listener ) {
        super.removeBufferedImagePropertyChangeListener( listener );
    }

    public BufferedImage getBuffer() {
        return super.getBuffer();
    }

    public static final SeriesViewFactory RENDERER_JFREECHART = new SeriesViewFactory() {
        public SeriesView createSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData ) {
            return new JFreeChartSeriesView( dynamicJFreeChartNode, seriesData );
        }
    };

    public static final SeriesViewFactory RENDERER_BUFFERED = new SeriesViewFactory() {
        public SeriesView createSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData ) {
            return new BufferedSeriesView( dynamicJFreeChartNode, seriesData );
        }
    };
    public static final SeriesViewFactory RENDERER_PICCOLO = new SeriesViewFactory() {
        public SeriesView createSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData ) {
            return new PPathSeriesView( dynamicJFreeChartNode, seriesData );
        }
    };
    public static final SeriesViewFactory RENDERER_BUFFERED_IMMEDIATE = new SeriesViewFactory() {
        public SeriesView createSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData ) {
            return new BufferedImmediateSeriesView( dynamicJFreeChartNode, seriesData );
        }
    };
    public static final SeriesViewFactory RENDERER_PICCOLO_INCREMENTAL_IMMEDIATE = new SeriesViewFactory() {
        public SeriesView createSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData ) {
            return new ImmediateBoundedPPathSeriesView( dynamicJFreeChartNode, seriesData );
        }
    };

    public static final SeriesViewFactory RENDERER_PICCOLO_INCREMENTAL = new SeriesViewFactory() {
        public SeriesView createSeriesView( DynamicJFreeChartNode dynamicJFreeChartNode, SeriesData seriesData ) {
            return new BoundedPPathSeriesView( dynamicJFreeChartNode, seriesData );
        }
    };

    public void setSeriesVisible( SeriesData seriesData, boolean visible ) {
        seriesData.setVisible( visible );
        updateSeriesViews();
    }
}
