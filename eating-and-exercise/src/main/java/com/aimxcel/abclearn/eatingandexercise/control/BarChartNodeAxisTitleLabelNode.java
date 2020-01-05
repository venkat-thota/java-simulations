
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseConstants;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.EatingAndExerciseCanvas;
import com.aimxcel.abclearn.eatingandexercise.view.EatingAndExercisePText;
import com.aimxcel.abclearn.eatingandexercise.view.StackedBarChartNode;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;

/**
 * Created by: Sam
 * Aug 18, 2008 at 11:21:02 AM
 */
public class BarChartNodeAxisTitleLabelNode extends PNode {
    private EatingAndExerciseCanvas canvas;
    private StackedBarChartNode stackedBarChart;
    private PNode parent;
    private PPath backgroundCoverup;

    public BarChartNodeAxisTitleLabelNode( EatingAndExerciseCanvas canvas, StackedBarChartNode stackedBarChart, PNode parent ) {
        this.canvas = canvas;
        this.stackedBarChart = stackedBarChart;
        this.parent = parent;
        EatingAndExercisePText text = new EatingAndExercisePText( stackedBarChart.getTitle() );
        text.setFont( new AimxcelFont( 20, true ) );

        canvas.addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                updateLayout();
                updateLayout();
            }
        } );

        parent.addPropertyChangeListener( PNode.PROPERTY_FULL_BOUNDS, new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
                updateLayout();
                updateLayout();
            }
        } );

        updateLayout();
        updateLayout();

        backgroundCoverup = new AimxcelPPath( text.getFullBounds(), EatingAndExerciseConstants.BACKGROUND );
        addChild( backgroundCoverup );
        addChild( text );
    }

    private void updateLayout() {
        PNode axisNode = stackedBarChart.getAxisNode();
        Point2D center = axisNode.getGlobalFullBounds().getCenter2D();
        parent.globalToLocal( center );
        setOffset( center.getX() - getFullBounds().getWidth() / 2, 0 );
    }
}
