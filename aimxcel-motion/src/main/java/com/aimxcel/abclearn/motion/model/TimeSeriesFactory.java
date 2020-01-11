
package com.aimxcel.abclearn.motion.model;


public interface TimeSeriesFactory {
    DefaultTimeSeries createTimeSeries();

    public static class Default implements TimeSeriesFactory {
        public DefaultTimeSeries createTimeSeries() {
            return new DefaultTimeSeries();
        }
    }
}
