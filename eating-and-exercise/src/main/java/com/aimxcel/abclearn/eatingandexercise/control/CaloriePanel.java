
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Function;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseStrings;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.EatingAndExerciseCanvas;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.EatingAndExerciseModel;
import com.aimxcel.abclearn.eatingandexercise.view.BarChartElement;
import com.aimxcel.abclearn.eatingandexercise.view.EatingAndExerciseColorScheme;
import com.aimxcel.abclearn.eatingandexercise.view.StackedBarChartNode;
import com.aimxcel.abclearn.eatingandexercise.view.StackedBarNode;
import com.aimxcel.abclearn.motion.model.DefaultTemporalVariable;
import com.aimxcel.abclearn.motion.model.IVariable;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;

public class CaloriePanel extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EatingAndExerciseCanvas aimxcelPCanvas;
    private StackedBarChartNode stackedBarChart;
    private CalorieNode foodNode;
    private CalorieNode exerciseNode;
    private ChartNode chartNode;

    public CaloriePanel( final EatingAndExerciseModel model, final EatingAndExerciseCanvas aimxcelPCanvas, Frame parentFrame ) {
        this.aimxcelPCanvas = aimxcelPCanvas;
        this.chartNode = new ChartNode( model, aimxcelPCanvas );
        addChild( chartNode );

        Function.LinearFunction transform = new Function.LinearFunction( 0, 3000, 0, 250 );
        stackedBarChart = new StackedBarChartNode( transform, EatingAndExerciseResources.getString( "units.cal-per-day" ), 10, 250, 1000, 8000 );

        StackedBarNode foodBars = new StackedBarNode( transform, 100 );
        Color labelColor = Color.black;
        foodBars.addElement( new BarChartElementAdapter( EatingAndExerciseStrings.FATS, EatingAndExerciseColorScheme.FATS, model.getHuman().getLipids(), "stick_butter.png", labelColor ), StackedBarNode.NONE );
        foodBars.addElement( new BarChartElementAdapter( EatingAndExerciseResources.getString( "food.carbs" ), EatingAndExerciseColorScheme.CARBS, model.getHuman().getCarbs(), "carbs.png", labelColor ), StackedBarNode.NONE );
        foodBars.addElement( new BarChartElementAdapter( EatingAndExerciseResources.getString( "food.protien" ), EatingAndExerciseColorScheme.PROTEIN, model.getHuman().getProteins(), "j0413686.gif", labelColor ), StackedBarNode.NONE );

        StackedBarNode exerciseBars = new StackedBarNode( transform, 100 );
        exerciseBars.addElement( new BarChartElementAdapter( EatingAndExerciseResources.getString( "exercise.resting.bmr" ), EatingAndExerciseColorScheme.BMR, model.getHuman().getBmr(), "heart2.png" ), StackedBarNode.NONE );
        exerciseBars.addElement( new BarChartElementAdapter( EatingAndExerciseResources.getString( "exercise.lifestyle" ), EatingAndExerciseColorScheme.ACTIVITY, model.getHuman().getActivity(), "j0417518.png" ), StackedBarNode.NONE );
        exerciseBars.addElement( new BarChartElementAdapter( EatingAndExerciseResources.getString( "exercise" ), EatingAndExerciseColorScheme.EXERCISE, model.getHuman().getExercise(), "road_biker.png" ), StackedBarNode.NONE );

        stackedBarChart.addStackedBarNode( foodBars );
        stackedBarChart.addStackedBarNode( exerciseBars );
        addChild( stackedBarChart );

        BarChartNodeAxisTitleLabelNode barChartNodeAxisTitleLabelNode = new BarChartNodeAxisTitleLabelNode( aimxcelPCanvas, stackedBarChart, this );
        addChild( barChartNodeAxisTitleLabelNode );

        foodNode = new CalorieNode( parentFrame, EatingAndExerciseResources.getString( "edit.diet" ),
                                    new Color( 100, 100, 255 ), model.getAvailableFoods(),
                                    model.getHuman().getSelectedFoods(), EatingAndExerciseResources.getString( "food.sources" ), EatingAndExerciseResources.getString( "diet" ), "plate-2.png" ) {
            /**
										 * 
										 */
										private static final long serialVersionUID = 1L;

			protected ICalorieSelectionPanel createCalorieSelectionPanel() {
                return new FoodSelectionPanel( model.getHuman(), getAvailable(), getCalorieSet(), getAvailableTitle(), getSelectedTitle() );
            }
        };
        foodNode.addOverlapTarget( foodBars );
        addChild( foodNode );

        exerciseNode = new CalorieNode( parentFrame, EatingAndExerciseResources.getString( "exercise.edit" ),
                                        Color.red, model.getAvailableExercise(),
                                        model.getHuman().getSelectedExercise(),
                                        EatingAndExerciseResources.getString( "menu.options" ), EatingAndExerciseResources.getString( "exercise.daily" ), "planner.png" ) {
            /**
											 * 
											 */
											private static final long serialVersionUID = 1L;

			protected ICalorieSelectionPanel createCalorieSelectionPanel() {
                return new ExerciseSelectionPanel( model.getHuman(), getAvailable(), getCalorieSet(), getAvailableTitle(), getSelectedTitle() );
            }
        };
        exerciseNode.addOverlapTarget( exerciseBars );
        addChild( exerciseNode );

        addChild( foodNode.getTooltipLayer() );
        addChild( exerciseNode.getTooltipLayer() );

        relayout();

        aimxcelPCanvas.addComponentListener( new ComponentAdapter() {
            public void componentShown( ComponentEvent e ) {
                relayout();
            }

            public void componentResized( ComponentEvent e ) {
                relayout();
            }
        } );
    }

    public void resetAll() {
        chartNode.resetAll();
        foodNode.resetAll();
        exerciseNode.resetAll();
        stackedBarChart.resetAll();
    }

    public ChartNode getChartNode() {
        return chartNode;
    }

    public PNode getEditDietButton() {
        return foodNode.getEditButton();
    }

    public void applicationStarted() {
    }

    public void addEditorClosedListener( ActionListener actionListener ) {
        foodNode.addEditorClosedListener( actionListener );
        exerciseNode.addEditorClosedListener( actionListener );
    }

    public void clearAndResetDomains() {
        chartNode.clearAndResetDomains();
    }

    public void addFoodPressedListener( ActionListener actionListener ) {
        foodNode.addItemPressedListener( actionListener );
    }

    public PNode getPlateNode() {
        return foodNode.getDropTarget();
    }

    public void addExerciseDraggedListener( ActionListener actionListener ) {
        exerciseNode.addItemPressedListener( actionListener );
    }

    public PNode getDiaryNode() {
        return exerciseNode.getDropTarget();
    }

    public static class BarChartElementAdapter extends BarChartElement {
        public BarChartElementAdapter( String name, Paint paint, final DefaultTemporalVariable variable, String image, Color textColor ) {
            super( name, paint, variable.getValue(), EatingAndExerciseResources.getImage( image ), textColor );
            variable.addListener( new IVariable.Listener() {
                public void valueChanged() {
                    BarChartElementAdapter.this.setValue( variable.getValue() );
                }
            } );
            addListener( new Listener() {
                public void valueChanged() {
                    variable.setValue( BarChartElementAdapter.this.getValue() );
                }

                public void paintChanged() {
                }
            } );
        }

        public BarChartElementAdapter( String name, Paint paint, final DefaultTemporalVariable variable, String image ) {
            this( name, paint, variable, image, Color.black );
        }
    }

    private void relayout() {
        double width = aimxcelPCanvas.getWidth() - getOffset().getX();
        stackedBarChart.setOffset( width / 2 - stackedBarChart.getFullBounds().getWidth() / 2, foodNode.getPlateBottomY() );

        foodNode.setOffset( stackedBarChart.getFullBounds().getX() - foodNode.getDropTarget().getWidth(), 0 );
        exerciseNode.setOffset( stackedBarChart.getFullBounds().getMaxX() + 25, 0 );

        double w = aimxcelPCanvas.getWidth() - aimxcelPCanvas.getControlPanelWidth();
        chartNode.relayout( w, aimxcelPCanvas.getHeight() - foodNode.getPlateBottomY() );
        chartNode.setOffset( 0, foodNode.getPlateBottomY() );
    }
}