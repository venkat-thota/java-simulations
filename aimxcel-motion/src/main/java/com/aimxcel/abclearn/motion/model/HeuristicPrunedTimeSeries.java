
package com.aimxcel.abclearn.motion.model;


public class HeuristicPrunedTimeSeries extends DefaultTimeSeries {
    private double maxTime;

    public HeuristicPrunedTimeSeries( double maxTime ) {
        this.maxTime = maxTime;
    }

    public void addValue( double v, double time ) {
        super.addValue( v, time );
        //todo: how safe is this heuristic?
        if ( time >= maxTime * 4 ) {
            super.removeValue( getSampleCount() / 2 );
        }
    }

    public static class Factory implements TimeSeriesFactory {
        private double maxTime;

        public Factory( double maxTime ) {
            this.maxTime = maxTime;
        }

        public DefaultTimeSeries createTimeSeries() {
            return new HeuristicPrunedTimeSeries( maxTime );
        }
    }
}
