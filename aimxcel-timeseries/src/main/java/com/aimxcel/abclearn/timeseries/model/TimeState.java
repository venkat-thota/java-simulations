
package com.aimxcel.abclearn.timeseries.model;



public class TimeState {
    private Object value;
    private double time;

    public TimeState( Object value, double time ) {
        this.value = value;
        this.time = time;
    }

    public Object getValue() {
        return value;
    }

    public double getTime() {
        return time;
    }

    public String toString() {
        return "time=" + time + ", value=" + value;
    }

}
