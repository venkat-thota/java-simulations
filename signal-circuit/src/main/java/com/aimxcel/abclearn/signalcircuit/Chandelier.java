
package com.aimxcel.abclearn.signalcircuit;

import java.awt.*;

import com.aimxcel.abclearn.signalcircuit.paint.Painter;

public class Chandelier implements SignalListener, Painter {
    Painter on;
    Painter off;
    boolean isOn;
    double foreThreshold;

    public Chandelier( Painter on, Painter off, boolean isOn, double foreThreshold ) {
        this.foreThreshold = foreThreshold;
        this.on = on;
        this.off = off;
        this.isOn = isOn;
    }

    public void signalMoved( double fore, double back ) {
        //System.out.println("fore="+fore);
        this.isOn = fore > foreThreshold;
    }

    public void paint( Graphics2D g ) {
        if( isOn ) {
            on.paint( g );
        }
        else {
            off.paint( g );
        }
    }
}
