
package com.aimxcel.abclearn.signalcircuit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.electron.wire1d.WireSegment;
import com.aimxcel.abclearn.signalcircuit.phys2d.DoublePoint;

import java.util.Vector;

public class SegmentAngle implements ChangeListener {
    JSlider js;
    WireSegment s;
    double length;
    DoublePoint start;
    Vector v = new Vector();

    public SegmentAngle( JSlider js, WireSegment s ) {
        this.length = s.length();
        this.js = js;
        this.s = s;
        this.start = s.getStart();
    }

    public void addAngleListener( AngleListener al ) {
        v.add( al );
    }

    public void stateChanged( ChangeEvent ce ) {
        double value = js.getValue() / 180.0 * Math.PI;
        double dx = length * Math.cos( value );
        double dy = length * Math.sin( value );
        DoublePoint end = start.subtract( new DoublePoint( dx, dy ) );
        s.setFinish( end );
        for( int i = 0; i < v.size(); i++ ) {
            ( (AngleListener)v.get( i ) ).angleChanged( value );
        }
    }
}
