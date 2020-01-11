
package com.aimxcel.abclearn.motion.charts;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.motion.tests.ColorArrows;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;


public class TemporalChartSliderNode extends MotionSliderNode.Vertical {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final TemporalChart temporalChart;

    public TemporalChartSliderNode( final TemporalChart temporalChart, Color highlightColor ) {
        this( temporalChart, new PImage( ColorArrows.createArrow( highlightColor ) ), highlightColor );
    }

    public TemporalChartSliderNode( final TemporalChart temporalChart, final PNode sliderThumb, Color highlightColor ) {
        super( new Range( temporalChart.getMinRangeValue(), temporalChart.getMaxRangeValue() ), 0.0, new Range( 0, 100 ), sliderThumb, highlightColor );
        this.temporalChart = temporalChart;

        final SimpleObserver updateLayoutBasedOnChart = new SimpleObserver() {
            public void update() {
                setViewRange( 0, temporalChart.getViewDimension().getHeight() );
            }
        };
        temporalChart.addPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
                updateLayoutBasedOnChart.update();
            }
        } );
        updateLayoutBasedOnChart.update();

        temporalChart.getDataModelBounds().addObserver( new SimpleObserver() {
            public void update() {
                setModelRange( temporalChart.getMinRangeValue(), temporalChart.getMaxRangeValue() );
            }
        } );
    }
}