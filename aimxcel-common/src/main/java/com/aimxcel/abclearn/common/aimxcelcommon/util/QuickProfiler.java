package com.aimxcel.abclearn.common.aimxcelcommon.util;

/**
 * Utility class for timing activity.
 */

public class QuickProfiler {
    private long startTime;
    private String name;

    public QuickProfiler() {
        this( null );
    }

    public QuickProfiler( String name ) {
        this.name = name;
        this.startTime = System.currentTimeMillis();
    }

    public long getTime() {
        long now = System.currentTimeMillis();
        return now - startTime;
    }

    public void println() {
        System.out.println( toString() );
    }

    public String toString() {
        return ( name != null ? ( name + ": " ) : "" ) + getTime() + " (ms)";
    }
}