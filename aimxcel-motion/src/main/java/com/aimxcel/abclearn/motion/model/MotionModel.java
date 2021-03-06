
package com.aimxcel.abclearn.motion.model;

import java.util.ArrayList;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.timeseries.model.RecordableModel;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;
import com.aimxcel.abclearn.timeseries.model.TimeState;


public class MotionModel {
    private TimeSeriesModel timeSeriesModel;
    private ITemporalVariable timeVariable;
    private ArrayList<ITemporalVariable> temporalVariables = new ArrayList<ITemporalVariable>();
    private ConstantDtClock clock;

    public MotionModel( ConstantDtClock clock, TimeSeriesFactory timeSeriesFactory ) {
        timeVariable = new DefaultTemporalVariable( timeSeriesFactory );
        this.clock = clock;
        RecordableModel recordableModel = new RecordableModel() {
            public void stepInTime( double simulationTimeChange ) {
                MotionModel.this.stepInTime( simulationTimeChange );
            }

            public Object getState() {
                return new Double( timeVariable.getValue() );
            }

            public void setState( Object o ) {
                //the setState paradigm is used to allow attachment of listeners to model substructure
                //states are copied without listeners
                setPlaybackTime( ( (Double) o ).doubleValue() );
            }

            public void resetTime() {
                timeVariable.setValue( 0.0 );
            }

            public void clear() {
                MotionModel.this.clear();
            }
        };
        timeSeriesModel = createTimeSeriesModel( recordableModel, clock );
        timeSeriesModel.addListener( new TimeSeriesModel.Adapter() {
            public void modeChanged() {
                if ( timeSeriesModel.isRecordMode() ) {
                    TimeState timeState = timeSeriesModel.getSeries().getLastPoint();
                    if ( timeState != null ) {
                        Object o = timeState.getValue();
                        setPlaybackTime( ( (Double) o ).doubleValue() );
                    }
                }
            }
        } );
        timeSeriesModel.setRecordMode();
        clock.addClockListener( new ClockAdapter() {
            public void simulationTimeChanged( ClockEvent clockEvent ) {
                timeSeriesModel.stepMode( clockEvent.getSimulationTimeChange() );
            }
        } );
        addTemporalVariable( timeVariable );
    }


    protected TimeSeriesModel createTimeSeriesModel( RecordableModel recordableModel, ConstantDtClock clock ) {
        return new MotionTimeSeriesModel( recordableModel, clock );
    }

    /**
     * Sets the specified time for all registered temporal variables.
     *
     * @param time
     */
    protected void setPlaybackTime( double time ) {
        for ( int i = 0; i < temporalVariables.size(); i++ ) {
            ( (ITemporalVariable) temporalVariables.get( i ) ).setPlaybackTime( time );
        }
    }

    public void addTemporalVariables( ITemporalVariable[] temporalVariables ) {
        for ( int i = 0; i < temporalVariables.length; i++ ) {
            addTemporalVariable( temporalVariables[i] );
        }
    }

    public void addTemporalVariable( ITemporalVariable temporalVariable ) {
        temporalVariables.add( temporalVariable );
    }

    public void stepInTime( double dt ) {
        double newTime = timeVariable.getValue() + dt;
        timeVariable.addValue( newTime, newTime );
    }

    /**
     * Clears history for all registered temporal variables.
     */
    public void clear() {
        timeVariable.setValue( 0.0 );
        for ( int i = 0; i < temporalVariables.size(); i++ ) {
            ( (ITemporalVariable) temporalVariables.get( i ) ).clear();
        }
        timeSeriesModel.clear();
    }

    /**
     * Adds the current value to the history for the specified variable with the current time.
     *
     * @param variable var
     */
    protected void defaultUpdate( ITemporalVariable variable ) {
        variable.addValue( variable.getValue(), getTime() );
    }

    protected void defaultUpdate( ITemporalVariable[] v ) {
        for ( int i = 0; i < v.length; i++ ) {
            defaultUpdate( v[i] );
        }
    }

    public double getTime() {
        return timeVariable.getValue();
    }

    public TimeSeriesModel getTimeSeriesModel() {
        return timeSeriesModel;
    }

    public void resetAll() {
        clear();
    }

    public ITemporalVariable getTimeVariable() {
        return timeVariable;
    }

    public void setMaxAllowedRecordTime( double maxAllowedRecordTime ) {
        getTimeSeriesModel().setMaxAllowedRecordTime( maxAllowedRecordTime );
    }

    public ConstantDtClock getClock() {
        return clock;
    }
}
