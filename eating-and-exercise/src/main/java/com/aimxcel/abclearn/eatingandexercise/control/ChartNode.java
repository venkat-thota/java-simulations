 
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.data.Range;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.util.DefaultDecimalFormat;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.Arrow;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLImageButtonNode;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseStrings;
import com.aimxcel.abclearn.eatingandexercise.model.EatingAndExerciseUnits;
import com.aimxcel.abclearn.eatingandexercise.model.Human;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.EatingAndExerciseModel;
import com.aimxcel.abclearn.eatingandexercise.util.YearMonthFormat;
import com.aimxcel.abclearn.eatingandexercise.view.EatingAndExercisePText;
import com.aimxcel.abclearn.motion.graphs.ControlGraph;
import com.aimxcel.abclearn.motion.graphs.ControlGraphSeries;
import com.aimxcel.abclearn.motion.graphs.GraphSuiteSet;
import com.aimxcel.abclearn.motion.graphs.MinimizableControlGraph;
import com.aimxcel.abclearn.motion.model.DefaultTemporalVariable;
import com.aimxcel.abclearn.motion.model.ITemporalVariable;
import com.aimxcel.abclearn.motion.model.MotionTimeSeriesModel;
import com.aimxcel.abclearn.timeseries.model.TestTimeSeries;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

public class ChartNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MinimizableControlGraph weightChart;
    private MinimizableControlGraph calorieChart;

    private DefaultTemporalVariable massVar = new DefaultTemporalVariable();
    private DefaultTemporalVariable calIntakeVar = new DefaultTemporalVariable();
    private DefaultTemporalVariable calBurnVar = new DefaultTemporalVariable();
    private EatingAndExerciseModel model;
    private EatingAndExerciseControlGraph weightGraph;
    private EatingAndExerciseControlGraph calorieGraph;
    private static final double DEFAULT_RANGE_YEARS = 2;
    //    private static final double DEFAULT_RANGE_YEARS = 2 / 10.0;//for testing
    private EatingAndExerciseModel.Units previousUnits;
    private ArrayList listeners = new ArrayList();

    public ChartNode( final EatingAndExerciseModel model, AimxcelPCanvas aimxcelPCanvas ) {
        this.model = model;
        GraphSuiteSet graphSuiteSet = new GraphSuiteSet();

        //todo: remove the following bogus line
        TimeSeriesModel tsm = new MotionTimeSeriesModel( new TestTimeSeries.MyRecordableModel(), new ConstantDtClock( 30, 1 ) );

        model.addListener( new EatingAndExerciseModel.Adapter() {
            public void simulationTimeChanged() {
                updateVars();
            }
        } );
        updateVars();
        model.addListener( new EatingAndExerciseModel.Adapter() {
            public void unitsChanged() {
                updateWeightMassLabel();
                syncVerticalRanges();
            }
        } );
        model.getHuman().addListener( new Human.Adapter() {
            public void weightChanged() {
                massVar.setValue( getMassDisplayValue() );
            }
        } );
        model.getHuman().addListener( new Human.Adapter() {
            public void caloricIntakeChanged() {
                calIntakeVar.setValue( model.getHuman().getDailyCaloricIntake() );
            }

            public void caloricBurnChanged() {
                calBurnVar.setValue( model.getHuman().getDailyCaloricBurn() );
            }
        } );

        final ControlGraphSeries weightSeries = new ControlGraphSeries( EatingAndExerciseResources.getString( "weight" ), Color.blue, EatingAndExerciseResources.getString( "weight" ), EatingAndExerciseResources.getString( "units.lbs" ), "", massVar );
        weightSeries.setDecimalFormat( new DefaultDecimalFormat( "0" ) );
        model.addListener( new EatingAndExerciseModel.Adapter() {
            public void unitsChanged() {
                weightSeries.setUnits( model.getUnits().getMassUnit() );
            }
        } );
        weightGraph = new EatingAndExerciseControlGraph( aimxcelPCanvas, weightSeries, EatingAndExerciseResources.getString( "weight" ), 0, 250, tsm );
        weightGraph.setEditable( false );
//        weightGraph.getJFreeChartNode().getChart().getXYPlot().getDomainAxis().setLabel( "Label" );//takes up too much vertical space
        updateWeightMassLabel();
        weightChart = new MinimizableControlGraph( EatingAndExerciseResources.getString( "weight" ), weightGraph );
        weightChart.setAvailableBounds( 600, 125 );

        ControlGraphSeries intakeSeries = new ControlGraphSeries( EatingAndExerciseResources.getString( "calories.intake" ), Color.green, EatingAndExerciseResources.getString( "calories.intake" ), EatingAndExerciseStrings.KCAL_PER_DAY, new BasicStroke( 4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER ), "", calIntakeVar );
        intakeSeries.setDecimalFormat( new DefaultDecimalFormat( EatingAndExerciseStrings.KCAL_PER_DAY_FORMAT ) );
        ControlGraphSeries burnSeries = new ControlGraphSeries( EatingAndExerciseResources.getString( "calories.burned" ), Color.red, EatingAndExerciseResources.getString( "calories.burned" ), EatingAndExerciseStrings.KCAL_PER_DAY, new BasicStroke( 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER ), "", calBurnVar );
        burnSeries.setDecimalFormat( new DefaultDecimalFormat( EatingAndExerciseStrings.KCAL_PER_DAY_FORMAT ) );

        calorieGraph = new EatingAndExerciseControlGraph( aimxcelPCanvas, intakeSeries, EatingAndExerciseResources.getString( "units.calories" ), 0, 6000, tsm );
        calorieGraph.getJFreeChartNode().getChart().getXYPlot().getRangeAxis().setLabel( EatingAndExerciseResources.getString( "units.cal-day" ) );
        calorieGraph.addSeries( burnSeries );
        updateGraphDomains( DEFAULT_RANGE_YEARS );
        calorieGraph.setEditable( false );
        model.addListener( new EatingAndExerciseModel.Adapter() {
            public void simulationTimeChanged() {
                calorieGraph.forceUpdateAll();
            }
        } );
        calorieChart = new MinimizableControlGraph( EatingAndExerciseResources.getString( "units.calories" ), calorieGraph );
        calorieChart.setAvailableBounds( 600, 125 );

        calorieGraph.addListener( new ControlGraph.Adapter() {
            public void zoomChanged() {
                weightGraph.setDomain( calorieGraph.getMinDataX(), calorieGraph.getMaxDataX() );
            }
        } );
        weightGraph.addListener( new ControlGraph.Adapter() {
            public void zoomChanged() {
                calorieGraph.setDomain( weightGraph.getMinDataX(), weightGraph.getMaxDataX() );
            }
        } );

        MinimizableControlGraph[] graphs = { weightChart, calorieChart };
        weightChart.setAlignedLayout( graphs );
        calorieChart.setAlignedLayout( graphs );
        graphSuiteSet.addGraphSuite( graphs );

        addChild( weightChart );
        addChild( calorieChart );

        resetChartVerticalRanges();
        syncVerticalRanges();
    }

    public double getMaxChartTime() {
        return weightGraph.getLowerBound() + DEFAULT_RANGE_YEARS;
    }

    public void clearAndResetDomains() {
        clearData();
        updateGraphDomains();
    }

    private void updateWeightMassLabel() {
        String weight = EatingAndExerciseResources.getString( "weight" );
        weightGraph.getJFreeChartNode().getChart().getXYPlot().getRangeAxis().setLabel( weight + " (" + model.getUnits().getMassUnit() + ")" );
    }

    private void syncVerticalRanges() {
        if ( previousUnits == null ) {
            previousUnits = model.getUnits();
        }
        if ( previousUnits != model.getUnits() ) {
            double max = weightGraph.getJFreeChartNode().getChart().getXYPlot().getRangeAxis().getUpperBound();
            double modelMax = previousUnits.viewToModelMass( max );
            double viewMax = model.getUnits().modelToViewMass( modelMax );
            weightGraph.setVerticalRange( 0, viewMax );
            syncMassVar();
        }
        previousUnits = model.getUnits();
    }

    private void resetChartVerticalRanges() {
        weightGraph.setVerticalRange( 0, 250 );
        calorieGraph.setVerticalRange( 0, 6000 );
    }

    private void updateGraphDomains() {
        double min = weightGraph.getJFreeChartNode().getChart().getXYPlot().getDomainAxis().getLowerBound();
        double max = weightGraph.getJFreeChartNode().getChart().getXYPlot().getDomainAxis().getUpperBound();
        double currentRange = max - min;
        updateGraphDomains( currentRange );
    }

    private void updateGraphDomains( double rangeYears ) {
        double startTime = model.getHuman().getAge();
        calorieGraph.setDomain( EatingAndExerciseUnits.secondsToYears( startTime ),
                                EatingAndExerciseUnits.secondsToYears( startTime + EatingAndExerciseUnits.yearsToSeconds( rangeYears ) ) );
        weightGraph.setDomain( EatingAndExerciseUnits.secondsToYears( startTime ),
                               EatingAndExerciseUnits.secondsToYears( startTime + EatingAndExerciseUnits.yearsToSeconds( rangeYears ) ) );
    }

    private void resetChartArea() {
        clearAndResetDomains();
    }

    private void syncMassVar() {
        massVar.clear();
        ITemporalVariable itv = model.getHuman().getMassVariable();
        for ( int i = 0; i < itv.getSampleCount(); i++ ) {
            massVar.addValue( model.getUnits().modelToViewMass( itv.getData( i ).getValue() ), EatingAndExerciseUnits.secondsToYears( itv.getData( i ).getTime() ) );
        }
    }

    private void updateVars() {
        updateMassVar();
        updateCalIntakeVar();
        updateCalBurnVar();
    }

    private void updateCalBurnVar() {
        double calBurn = model.getHuman().getCaloricBurnVariable().getValue();
        calBurnVar.setValue( calBurn );
        calBurnVar.addValue( calBurn, getAgeYears() );
    }

    private void updateCalIntakeVar() {
        double calIntake = model.getHuman().getCaloricIntakeVariable().getValue();
        calIntakeVar.setValue( calIntake );
        calIntakeVar.addValue( calIntake, getAgeYears() );
    }

    private void updateMassVar() {
        massVar.setValue( getMassDisplayValue() );
        massVar.addValue( getMassDisplayValue(), getAgeYears() );
    }

    private double getMassDisplayValue() {
        return model.getUnits().modelToViewMass( model.getHuman().getMass() );
    }

    private double getAgeYears() {
        return EatingAndExerciseUnits.secondsToYears( model.getHuman().getAge() );
    }

    public void relayout( double width, double height ) {
        weightChart.setAvailableBounds( width, height / 2 );
        calorieChart.setAvailableBounds( width, height / 2 );
        weightChart.setOffset( 0, height - weightChart.getFullBounds().getHeight() - calorieChart.getFullBounds().getHeight() );
        calorieChart.setOffset( 0, weightChart.getFullBounds().getMaxY() );

        //have to relayout after both are resized
        //todo: internalize this
        weightChart.relayoutControlGraph();
        calorieChart.relayoutControlGraph();
    }

    public void resetAll() {
        clearData();
        updateGraphDomains( DEFAULT_RANGE_YEARS );
        resetChartVerticalRanges();
    }

    private void clearData() {
        massVar.clear();
        //todo: remove the need for this workaround
        model.getHuman().clearMassData();
        calBurnVar.clear();
        calIntakeVar.clear();

        notifyChartDataCleared();
    }

    public static interface Listener {
        public void chartDataCleared();
    }

    public void notifyChartDataCleared() {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (Listener) listeners.get( i ) ).chartDataCleared();
        }
    }

    public void addListener( Listener listener ) {
        listeners.add( listener );
    }

    public void removeListener( Listener listener ) {
        listeners.remove( listener );
    }

    private class EatingAndExerciseControlGraph extends ControlGraph {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private HTMLImageButtonNode gradientButtonNode;
        private PNode axisLabel;

        public EatingAndExerciseControlGraph( AimxcelPCanvas canvas, ControlGraphSeries series, String title, int minY, int maxY, TimeSeriesModel timeSeriesModel ) {
            super( canvas, series, title, minY, maxY, timeSeriesModel );
            gradientButtonNode = new HTMLImageButtonNode( EatingAndExerciseResources.getString( "time.reset" ), new AimxcelFont( Font.BOLD, 12 ), Color.green );
            gradientButtonNode.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    resetChartArea();
                }
            } );
            addChild( gradientButtonNode );

            axisLabel = new PNode();
            PText text = new EatingAndExercisePText( EatingAndExerciseResources.getString( "units.time.yrs" ) );
            axisLabel.addChild( text );
            axisLabel.addChild( new AimxcelPPath( new Arrow( new Point2D.Double( text.getFullBounds().getMaxX(), text.getFullBounds().getCenterY() ),
                                                          new MutableVector2D( 20, 0 ), 6, 6, 2, 0.5, true ).getShape(), Color.black ) );
            addChild( axisLabel );

            NumberAxis numberAxis = (NumberAxis) getJFreeChartNode().getChart().getXYPlot().getDomainAxis();

            numberAxis.setTickUnit( new NumberTickUnit( 2.0 * DEFAULT_RANGE_YEARS / 12.0 ) );
            numberAxis.setNumberFormatOverride( new YearMonthFormat() );

            relayout();
        }

        public void relayout() {
            super.relayout();
            if ( gradientButtonNode != null ) {
                int buttonInsetX = 2;
                gradientButtonNode.setOffset( getJFreeChartNode().getDataArea().getMaxX() - gradientButtonNode.getFullBounds().getWidth()
                                              - buttonInsetX + getJFreeChartNode().getOffset().getX(),
                                              getJFreeChartNode().getDataArea().getMaxY() - gradientButtonNode.getFullBounds().getHeight() );
                axisLabel.setOffset( getJFreeChartNode().getDataArea().getCenterX() - axisLabel.getFullBounds().getWidth()
                                     - buttonInsetX + getJFreeChartNode().getOffset().getX(),
                                     getJFreeChartNode().getDataArea().getMaxY() - axisLabel.getFullBounds().getHeight() );
            }
        }

        protected void zoomHorizontal( double v ) {
            double min = getLowerBound();
            double max = getJFreeChartNode().getChart().getXYPlot().getDomainAxis().getUpperBound();
            double currentRange = max - min;
            double newRange = Math.min( DEFAULT_RANGE_YEARS, currentRange * v );

            setDomain( min, min + newRange );
            forceUpdateAll();
        }

        private double getLowerBound() {
            return getJFreeChartNode().getChart().getXYPlot().getDomainAxis().getLowerBound();
        }

        public void setDomain( double minDomainValue, double maxDomainValue ) {
            super.setDomain( minDomainValue, maxDomainValue );
            if ( getZoomControl() != null ) {
                double currentRange = maxDomainValue - minDomainValue;
                double MACHINE_EPSILON = 1E-6;
                getZoomControl().setHorizontalZoomOutEnabled( currentRange <= DEFAULT_RANGE_YEARS - MACHINE_EPSILON );
            }
            syncMassVar();//todo: remove the need for this workaround
        }

        protected void zoomVertical( double zoomValue ) {
            Range verticalRange = getVerticalRange( zoomValue );
            setVerticalRange( 0, verticalRange.getUpperBound() );
            notifyZoomChanged();
            forceUpdateAll();
        }
    }

}
