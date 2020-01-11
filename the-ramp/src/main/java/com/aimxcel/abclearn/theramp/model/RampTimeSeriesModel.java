
package com.aimxcel.abclearn.theramp.model;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.theramp.RampModule;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.timeseries.ObjectTimePoint;
import com.aimxcel.abclearn.theramp.timeseries.ObjectTimeSeries;
import com.aimxcel.abclearn.theramp.timeseries.TimeSeriesModel;


public class RampTimeSeriesModel extends TimeSeriesModel {
    private RampModule rampModule;
    private ObjectTimeSeries series = new ObjectTimeSeries();
    private boolean recordedLastTime = false;

    public RampTimeSeriesModel( RampModule rampModule ) {
        super( RampModule.MAX_TIME );
        this.rampModule = rampModule;
    }

    public void repaintBackground() {
        rampModule.repaintBackground();
    }

    public void updateModel( ClockEvent clockEvent ) {
        rampModule.updateModel( clockEvent.getSimulationTimeChange() );

        if ( getRecordTime() <= RampModule.MAX_TIME && !recordedLastTime ) {
            RampPhysicalModel state = rampModule.getRampPhysicalModel().getState();
            series.addPoint( state, getRecordTime() );
            rampModule.updatePlots( state, getRecordTime() );
            if ( getRecordTime() >= RampModule.MAX_TIME ) {
                recordedLastTime = true;
            }
        }
        else {
            rampModule.updateReadouts();
        }
    }

    public void setReplayTime( double requestedTime ) {
        super.setReplayTime( requestedTime );

        ObjectTimePoint value = series.getValueForTime( requestedTime );
        if ( value != null ) {
            RampPhysicalModel v = (RampPhysicalModel) value.getValue();
            if ( v != null ) {
                rampModule.getRampPhysicalModel().setState( v );
            }
        }
        rampModule.updateReadouts();
    }

    public void reset() {
        super.reset();
        series.reset();
        rampModule.getRampPlotSet().reset();
        recordedLastTime = false;
        rampModule.clearHeatSansFiredog();
    }



    protected boolean confirmReset() {
        int answer = JOptionPane.showConfirmDialog( getSimulationPanel(), TheRampStrings.getString( "confirm-clear-graphs" ), TheRampStrings.getString( "confirm.clear" ), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE );
        return answer == JOptionPane.OK_OPTION;
    }

    public JComponent getSimulationPanel() {
        return rampModule.getSimulationPanel();
    }

    public RampModule getRampModule() {
        return rampModule;
    }

    public void clockTicked( ClockEvent event ) {
        super.clockTicked( event );
        if ( isPaused() ) {
            rampModule.updateReadouts();
        }
    }


}
