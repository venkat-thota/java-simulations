
package com.aimxcel.abclearn.motion.model;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.timeseries.model.RecordableModel;
import com.aimxcel.abclearn.timeseries.model.TimeSeriesModel;

/**
 * Created by: Sam
 * Nov 28, 2007 at 8:16:46 AM
 */
public class MotionTimeSeriesModel extends TimeSeriesModel {
    public MotionTimeSeriesModel( RecordableModel recordableModel, ConstantDtClock clock ) {
        super( recordableModel, clock );
    }

    //workaround for buggy state/time sequence: time is obtained from record mode before switching to playback mode
    public void rewind() {
        setPlaybackMode();
        super.rewind();
    }
}
