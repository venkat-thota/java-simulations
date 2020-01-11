
package com.aimxcel.abclearn.motion.model;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.timeseries.model.RecordableModel;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;


public class MotionTimeSeriesModel extends TimeSeriesModel {
    public MotionTimeSeriesModel( RecordableModel recordableModel, ConstantDtClock clock ) {
        super( recordableModel, clock );
    }

    public void rewind() {
        setPlaybackMode();
        super.rewind();
    }
}
