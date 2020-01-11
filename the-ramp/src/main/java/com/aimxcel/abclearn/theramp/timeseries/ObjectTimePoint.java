
package com.aimxcel.abclearn.theramp.timeseries;



public class ObjectTimePoint {
    private Object value;
    private double time;

    public ObjectTimePoint( Object value, double time ) {
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
